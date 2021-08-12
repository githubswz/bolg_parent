package com.swz.blog.service;

import com.swz.blog.vo.Result;
import com.swz.blog.vo.TagVo;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 13:13
 * @Description: TODO:
 */
public interface TagService {
    List<TagVo> selectTagsByArticleId (Long id);

    List<TagVo> selectHotTags (int limit);

    Result findAllTags ();

    Result getTagById (Long id);
}
