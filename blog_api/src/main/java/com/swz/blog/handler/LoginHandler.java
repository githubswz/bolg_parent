package com.swz.blog.handler;
import com.alibaba.fastjson.JSONObject;
import com.swz.blog.pojo.SysUser;
import com.swz.blog.utils.UserThreadLocal;
import com.swz.blog.vo.ErrorCode;
import com.swz.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通过拦截器实现拦截未登录的请求
 * 未登录的不能访问
 */
@Component
@Slf4j
public class LoginHandler implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //springboot中请求可以访问controller中的资源，也可以访问resource目录下的static中的资源
        //判断是不是访问的controller
        if (!(handler instanceof HandlerMethod))
            return true;
        //拿到token
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");
        //token有误
        if (StringUtils.isEmpty(token)|token.equals("undefined")){
            //需要通过respose来响应json数据
            Result fail = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSONObject.toJSONString(fail));
            return false;
        }
        String s = redisTemplate.opsForValue().get("TOKEN_" + token) + "";
        SysUser sysUser = JSONObject.parseObject(s, SysUser.class);
        //没有用户
        if (ObjectUtils.isEmpty(sysUser)){
            //需要通过respose来响应json数据
            Result fail = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSONObject.toJSONString(fail));
            return false;
        }
        /**************因为此处已经验证过了，所以后台controller需要直接拿到用户信息该怎么做？*************/
        UserThreadLocal.set(sysUser);
        //******在后太controller中就可以通过   UserThreadLocal.get()  就可以获取用户线程了
        return true;
    }

    @Override
    public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
        /****在请求结束的时候去销毁本地线程***/
        UserThreadLocal.delete();
    }
}
