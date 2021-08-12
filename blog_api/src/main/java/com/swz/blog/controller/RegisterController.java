package com.swz.blog.controller;

import com.swz.blog.service.LoginService;
import com.swz.blog.vo.Result;
import com.swz.blog.vo.param.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 苏文致
 * @date Date : 2021年07月22日 14:53
 * @Description: TODO:
 */
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register (@RequestBody LoginParam register){
        return loginService.register(register);

    }
}
