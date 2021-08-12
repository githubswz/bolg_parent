package com.swz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.swz.blog.dao.CategoryDao;
import com.swz.blog.pojo.Category;
import com.swz.blog.service.CategoryService;
import com.swz.blog.vo.CategoryVo;
import com.swz.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 19:54
 * @Description: TODO:
 */

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public CategoryVo copy (Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    public List<CategoryVo> copyList (List<Category> categoryList){
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

    @Override
    public Result findAll (){
        List<Category> categories = categoryDao.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(categories));
    }

    /**
     * 查询所有的文章分类
     */
    @Override
    public List<CategoryVo> findAllCategory (){
        List<Category> categories = categoryDao.selectList(null);
        List<CategoryVo> categoryVos = copyList(categories);
        return categoryVos;
    }

    @Override
    public CategoryVo findCategoryById (Long id){
        if (id == null)
            return null;
        Category category = categoryDao.selectById(id);
        CategoryVo categoryVo = copy(category);
        return categoryVo;
    }
}
