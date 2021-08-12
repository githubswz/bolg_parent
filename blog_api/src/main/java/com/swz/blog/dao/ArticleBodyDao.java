package com.swz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swz.blog.pojo.ArticleBody;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 10:57
 * @Description: TODO:
 */
@Mapper
@Repository
public interface ArticleBodyDao extends BaseMapper<ArticleBody> {
}
