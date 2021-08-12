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
 * @date Date : 2021年07月22日 12:44
 * @Description: TODO:
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/currentUser")
    public Result currentUser (@RequestHeader("Authorization") String token){

        Result result = loginService.currentUser(token);
        return result;
    }


}
