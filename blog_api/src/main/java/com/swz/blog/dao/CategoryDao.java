package com.swz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swz.blog.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 20:04
 * @Description: TODO:
 */
@Repository
@Mapper
public interface CategoryDao extends BaseMapper<Category> {
}
