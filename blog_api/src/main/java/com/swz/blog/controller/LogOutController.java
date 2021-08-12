package com.swz.blog.controller;

import com.swz.blog.service.LoginService;
import com.swz.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 苏文致
 * @date Date : 2021年07月22日 14:06
 * @Description: TODO:
 */
@RestController
@RequestMapping("/logout")
public class LogOutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result logout (@RequestHeader("Authorization") String tooken){
        Result result = loginService.logOut(tooken);
        return result;
    }


}
