package com.swz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swz.blog.dao.ArticleBodyDao;
import com.swz.blog.pojo.ArticleBody;
import com.swz.blog.service.ArticleBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 10:50
 * @Description: TODO:
 */
@Service
@Transactional
public class ArticleBodyServiceImpl implements ArticleBodyService {
    @Autowired
    private ArticleBodyDao articleBodyDao;

    @Override
    public ArticleBody findArticleBodyByArticleId (Long id){
        QueryWrapper<ArticleBody> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", id);
        return articleBodyDao.selectOne(wrapper);
    }
}
