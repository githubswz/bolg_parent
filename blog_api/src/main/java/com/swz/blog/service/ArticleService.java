package com.swz.blog.service;

import com.swz.blog.vo.ArticleVo;
import com.swz.blog.vo.Result;
import com.swz.blog.vo.param.PageParams;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 10:55
 * @Description: TODO:
 */

public interface ArticleService {
    List<ArticleVo> listArticlesPage (PageParams pageParams);

    List<ArticleVo> selectHot (Integer limit);

    List<ArticleVo> selectNew (int limit);

    Result listArchives ();

    Result findArticleById (Long id);

    Result publish (ArticleVo articleVo);

    Result findByCategoryId (Long id);
}
