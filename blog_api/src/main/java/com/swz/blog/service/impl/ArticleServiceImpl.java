package com.swz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swz.blog.dao.ArticleDao;
import com.swz.blog.dao.BodyDao;
import com.swz.blog.dao.CategoryDao;
import com.swz.blog.pojo.Article;
import com.swz.blog.pojo.ArticleBody;
import com.swz.blog.pojo.SysUser;
import com.swz.blog.service.*;
import com.swz.blog.utils.UserThreadLocal;
import com.swz.blog.vo.*;
import com.swz.blog.vo.param.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 10:56
 * @Description: TODO:
 */

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BodyDao bodyDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<ArticleVo> listArticlesPage (PageParams pageParams){
        //确定页码和条数
        Page<Article> articlePage = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        List<Article> records = articleDao.findPage(articlePage, pageParams.getCategoryId(), pageParams.getYear(), pageParams.getMonth(), pageParams.getTagId(), pageParams.getSort());
        //拷贝list
        List<ArticleVo> articleVos = copyList(records, true, true);
        return articleVos;
    }

    /**
     * 查询最热的文章
     */
    @Override
    public List<ArticleVo> selectHot (Integer limit){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_counts")
                .select("id", "title")
                .last("limit 0," + limit);
        List<Article> articleList = articleDao.selectList(queryWrapper);
        List<ArticleVo> articleVos = copyList(articleList, false, false);
        return articleVos;
    }

    @Override
    public List<ArticleVo> selectNew (int limit){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title")
                .orderByDesc("create_date")
                .last("limit 0," + limit);
        List<Article> articleList = articleDao.selectList(queryWrapper);
        List<ArticleVo> articleVos = copyList(articleList, false, false);
        return articleVos;
    }

    @Override
    public Result listArchives (){
        List<Map<String, Object>> archivesInfo = articleDao.selectDate();
        return Result.success(archivesInfo);
    }

    @Autowired
    private ThreadService threadService;

    /**
     * 查询文章内容，以及更新阅读的数量
     */
    @Override
    public Result findArticleById (Long id){
        Article article = articleDao.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true);
        /**
         *  将文章的阅读数量加 1
         *  由于添加和查询存在在事务中，不能让添加干扰查询，故需要才用多线程来独立的进行更新操作
         */
        //log.info("========================================>>>>>>开启多线程添加");
        threadService.update(article, articleDao);
        //log.info("========================================>>>>>>主线程完毕...");
        return Result.success(articleVo);
    }

    /**
     * 上传文章
     */
    @Override
    public Result publish (ArticleVo articleVo){
        Article article = new Article();
        //将vo--->pojo
        BeanUtils.copyProperties(articleVo, article);
        article.setCreateDate(System.currentTimeMillis());
        SysUser sysUser = UserThreadLocal.get();
        article.setAuthorId(sysUser.getId());

        Long categoryId = articleVo.getCategory().getId();
        article.setCategoryId(categoryId);
        articleDao.insert(article);
        //添加body
        ArticleBodyVo bodyVo = articleVo.getBody();
        ArticleBody articleBody = new ArticleBody();
        BeanUtils.copyProperties(bodyVo, articleBody);
        Long articleId = article.getId();
        articleBody.setArticleId(articleId);
        bodyDao.insert(articleBody);
        article.setBodyId(articleBody.getId());
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.eq("id", article.getId());
        articleDao.update(article, wrapper);
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId() + "");
        return Result.success(map);
    }

    /**
     * 根据分类的id查询所有的文章
     */
    @Override
    public Result findByCategoryId (Long id){
        if (id == null)
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", id);
        List<Article> articleList = articleDao.selectList(queryWrapper);

        List<ArticleVo> articleVos = copyList(articleList, true, true);
        return Result.success(articleVos);
    }

    private List<ArticleVo> copyList (List<Article> articles, boolean hasUser, boolean hasTar){
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            ArticleVo articleVo = copy(article, hasUser, hasTar);
            articleVos.add(articleVo);
        }
        return articleVos;
    }

    private ArticleVo copy (Article article, boolean hasUser, boolean hasTar){
        if (ObjectUtils.isEmpty(article))
            return null;
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        if (hasUser) {
            //通过文章的id去查询作者信息
            UserVo sysUser = sysUserService.selectById(article.getAuthorId());
            //用户查询
            String nickname = sysUser.getNickname();
            if (nickname != null && nickname.length() > 0) {
                articleVo.setAuthor(nickname);
            } else {
                articleVo.setAuthor("没有用户的信息");
            }
        }
        if (hasTar) {
            //tag查询
            List<TagVo> tags = tagService.selectTagsByArticleId(article.getId());
            articleVo.setTags(tags);
        }
        return articleVo;
    }

    private List<ArticleVo> copyList (List<Article> articles, boolean hasUser, boolean hasTar, boolean hasBody){
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            ArticleVo articleVo = copy(article, hasUser, hasTar, hasBody);
            articleVos.add(articleVo);
        }
        return articleVos;
    }

    @Autowired
    private CategoryService categoryService;

    private ArticleVo copy (Article article, boolean hasUser, boolean hasTar, boolean isBody){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        Long categoryId = article.getCategoryId();
        CategoryVo categoryVo = categoryService.findCategoryById(categoryId);
        articleVo.setCategory(categoryVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        if (hasUser) {
            //通过文章的id去查询作者信息
            UserVo sysUser = sysUserService.selectById(article.getAuthorId());

            //用户查询
            String nickname = sysUser.getNickname();
            if (nickname != null && nickname.length() > 0) {
                articleVo.setAuthor(nickname);
            } else {
                articleVo.setAuthor("没有用户的信息");
            }
        }
        if (hasTar) {
            //tag查询
            List<TagVo> tags = tagService.selectTagsByArticleId(article.getId());
            articleVo.setTags(tags);
        }
        //需要内容
        if (isBody) {
            ArticleBodyVo articleBody = findArticleBody(article.getId());
            articleVo.setBody(articleBody);
        }

        return articleVo;
    }


    @Autowired
    private ArticleBodyService articleBodyService;

    private ArticleBodyVo findArticleBody (Long id){

        ArticleBody articleBody = articleBodyService.findArticleBodyByArticleId(id);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        BeanUtils.copyProperties(articleBody, articleBodyVo);
        return articleBodyVo;
    }


}
