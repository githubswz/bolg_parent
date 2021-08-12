package com.swz.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 苏文致
 * @date Date : 2021年07月22日 15:56
 * @Description: TODO:
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping
    public String test (){
        return "success";
    }

}
