package com.swz.blog.service;

import com.swz.blog.vo.Result;
import com.swz.blog.vo.param.CommentParam;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 13:23
 * @Description: TODO:
 */

public interface CommentService {

    Result getComment (Long articleId);

    Result comment (CommentParam commentParam);
}
