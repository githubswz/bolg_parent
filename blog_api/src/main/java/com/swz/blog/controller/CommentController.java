package com.swz.blog.controller;

import com.swz.blog.service.CommentService;
import com.swz.blog.vo.Result;
import com.swz.blog.vo.param.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 13:21
 * @Description: TODO:
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 查询所有的评论
     * @param articleId
     * @return
     */
    @GetMapping("/article/{id}")
    public Result getComment (@PathVariable("id") Long articleId){
        return commentService.getComment(articleId);
    }

    /**
     * 创建评论
     * @param commentParam
     * @return
     */
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentService.comment(commentParam);
    }

}
