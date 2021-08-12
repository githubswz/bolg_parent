package com.swz.blog.service;

import com.swz.blog.vo.CategoryVo;
import com.swz.blog.vo.Result;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 19:54
 * @Description: TODO:
 */
public interface CategoryService {
    Result findAll ();

    List<CategoryVo> findAllCategory ();

    CategoryVo findCategoryById (Long id);
}
