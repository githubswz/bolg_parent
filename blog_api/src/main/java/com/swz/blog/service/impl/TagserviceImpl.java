package com.swz.blog.service.impl;

import com.swz.blog.dao.TagDao;
import com.swz.blog.pojo.Tag;
import com.swz.blog.service.TagService;
import com.swz.blog.vo.ErrorCode;
import com.swz.blog.vo.Result;
import com.swz.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 13:13
 * @Description: TODO:
 */
@Service
public class TagserviceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    /**
     * 根据文章id查询标签的信息
     * @param articleId
     * @return
     */
    @Override
    public List<TagVo> selectTagsByArticleId (Long articleId){
        List<Tag> tagList = tagDao.selectTagsByArticleId(articleId);
        List<TagVo> tagVos = copyList(tagList);
        return tagVos;
    }

    /**
     * 查询最热标签
     * @param limit
     * @return
     */
    @Override
    public List<TagVo> selectHotTags (int limit){

        List<Tag> tags = tagDao.selectHotTags(limit);

        List<TagVo> tagVos = copyList(tags);
        return tagVos;
    }

    /**
     * 查询所有的标签
     * @return
     */
    @Override
    public Result findAllTags (){
        List<Tag> tagDaos = tagDao.selectList(null);
        List<TagVo> tagVos = copyList(tagDaos);
        return Result.success(tagVos);
    }

    @Override
    public Result getTagById (Long id){
        if (id==null)
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        Tag tag = tagDao.selectById(id);
        return Result.success(copy(tag));
    }

    private List<TagVo> copyList (List<Tag> tagList){
        List<TagVo> tagVos = new ArrayList<>();
        for (Tag tag : tagList) {
            TagVo tagVo = copy(tag);
            tagVos.add(tagVo);
        }
        return tagVos;
    }

    private TagVo copy (Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }


}
