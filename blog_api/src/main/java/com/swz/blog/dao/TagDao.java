package com.swz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swz.blog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 13:05
 * @Description: TODO:
 */
@Mapper
@Repository
public interface TagDao extends BaseMapper<Tag> {

    List<Tag> selectTagsByArticleId (Long articleId);

    List<Tag> selectHotTags (int limit);
}
