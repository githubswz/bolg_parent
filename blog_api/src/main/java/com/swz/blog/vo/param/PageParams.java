package com.swz.blog.vo.param;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 11:18
 * @Description: TODO:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    /**
     * 当前页面
     */
    private Integer page;
    /**
     * 页面返回条数
     */
    private Integer pageSize;
    /**
     * 分类的查询条件
     */
    private Long categoryId;

    /**
     * 标签的查询条件
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tagId;

    private String year;
    private String month;
    private String sort;


}
