package com.swz.blog.controller;

import com.swz.blog.service.TagService;
import com.swz.blog.vo.Result;
import com.swz.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 14:36
 * @Description: TODO:
 */
@RestController
@RequestMapping("/tags")
public class HotTagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Result hotTags (){
        int limit = 6; //设置最热的数量
        List<TagVo> tagVos = tagService.selectHotTags(limit);
        return Result.success(tagVos);
    }

    /**
     * 显示所有的标签
     *
     * @return
     */
    @GetMapping
    public Result listTags (){
        return tagService.findAllTags();
    }

    @GetMapping("/detail")
    public Result getAllTags(){
        return tagService.findAllTags();
    }

    /**
     * 根据id来查询标签信息
     * @return
     */
    @GetMapping("/detail/{id}")
    public Result getTagById(@PathVariable("id") Long id){
        return tagService.getTagById(id);
    }

}
