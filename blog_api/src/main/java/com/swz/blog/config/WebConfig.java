package com.swz.blog.config;

import com.swz.blog.handler.LoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : 苏文致
 * @date Date : 2021年07月21日 10:06
 * @Description: TODO:
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginHandler loginHandler;

    //处理跨域请求
    @Override
    public void addCorsMappings (CorsRegistry registry){
        //跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
        //本地测试 端口不一致 也算跨域
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }

    @Override
    public void addInterceptors (InterceptorRegistry registry){
        registry.addInterceptor(loginHandler)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
    }

}
