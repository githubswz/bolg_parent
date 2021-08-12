package com.swz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swz.blog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 13:28
 * @Description: TODO:
 */
@Mapper
@Repository
public interface CommentDao extends BaseMapper<Comment> {

}
