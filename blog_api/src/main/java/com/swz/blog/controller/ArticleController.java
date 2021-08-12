package com.swz.blog.controller;

import com.swz.blog.common.CacheAnnotation;
import com.swz.blog.common.LogAnnotation;
import com.swz.blog.service.ArticleService;
import com.swz.blog.vo.ArticleVo;
import com.swz.blog.vo.Result;
import com.swz.blog.vo.param.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 10:51
 * @Description: TODO:
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @CacheAnnotation(name = "getArticleController") //缓存apo
    public Result articles (@RequestBody PageParams pageParams){
        //ArticleVo 页面接收的数据
        List<ArticleVo> articles = articleService.listArticlesPage(pageParams);
        return Result.success(articles);
    }

    @PostMapping("/hot")
    public Result hot (){
        int limit = 6;
        List<ArticleVo> voList = articleService.selectHot(limit);
        return Result.success(voList);
    }

    @PostMapping("/new")
    public Result newArticle (){
        int limit = 6;
        List<ArticleVo> articleVos = articleService.selectNew(limit);
        return Result.success(articleVos);
    }

    /**
     * 返回文章归档的信息
     *
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives (){
        return articleService.listArchives();
    }


    @PostMapping("/view/{id}")
    public Result showView (@PathVariable("id") Long id){
        Result result = articleService.findArticleById(id);
        return result;
    }

    @PostMapping("/publish")
    @LogAnnotation(module = "文章控制类", operator = "添加文章")
    public Result publish (@RequestBody ArticleVo articleVo){
        return articleService.publish(articleVo);
    }

}
