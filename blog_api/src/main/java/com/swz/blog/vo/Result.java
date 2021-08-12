package com.swz.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 11:13
 * @Description: TODO:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean success;

    private Integer code;

    private String msg;

    private Object data;

    public static Result success (Object data){
        return new Result(true, 200, "success", data);
    }

    public static Result fail(Integer code,String msg){
        return new Result(false,code,msg,null);
    }


}