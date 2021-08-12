package com.swz.blog.controller;

import com.swz.blog.service.LoginService;
import com.swz.blog.vo.ErrorCode;
import com.swz.blog.vo.Result;
import com.swz.blog.vo.param.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 苏文致
 * @date Date : 2021年07月22日 11:12
 * @Description: TODO:
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){

        String token = loginService.login(loginParam);
        if (token==null){
          return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        return Result.success(token);
    }





}
