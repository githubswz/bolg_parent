package com.swz.blog.controller;

import com.swz.blog.service.CategoryService;
import com.swz.blog.vo.CategoryVo;
import com.swz.blog.vo.ErrorCode;
import com.swz.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 19:52
 * @Description: TODO:
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有的类别
     * @return
     */
    @GetMapping
    public Result listCategory() {
        return categoryService.findAll();
    }

    /**
     *  查询所有的文章分类
     * @return
     */
    @GetMapping("/detail")
    public Result findAllCategory(){
        List<CategoryVo> allCategory = categoryService.findAllCategory();
        return Result.success(allCategory);
    }

    /**
     * 拿到具体的分类信息
     */
    // category/detail/{id}
    @GetMapping("/detail/{id}")
    public Result findCategoryById(@PathVariable("id")Long id){
        CategoryVo categoryVo = categoryService.findCategoryById(id);
        if (categoryVo!=null)
        return Result.success(categoryVo);
        return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
    }


}
