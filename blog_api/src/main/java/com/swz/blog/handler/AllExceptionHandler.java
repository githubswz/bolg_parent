package com.swz.blog.handler;

import com.swz.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 15:23
 * @Description: TODO:
 */
//对所有的controller注解进行拦截，进行异常处理
@ControllerAdvice
public class AllExceptionHandler {

    @ResponseBody //以json响应会数据
    @ExceptionHandler(Exception.class)
    public Result allExceptionHandler(Exception e){
        e.printStackTrace();
        return Result.fail(-999,"系统出错");
    }

}
