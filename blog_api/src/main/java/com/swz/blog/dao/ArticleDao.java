package com.swz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swz.blog.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 10:51
 * @Description: TODO:
 */
@Mapper
@Repository
public interface ArticleDao extends BaseMapper<Article> {
    List<Map<String,Object>> selectDate ();

    List<Article> findPage (Page<Article> articlePage,Long categoryId,String year,String month,Long tagId,String sort);
}
