package com.swz.blog.common.aop;


import com.alibaba.fastjson.JSON;
import com.swz.blog.common.LogAnnotation;
import com.swz.blog.utils.HttpContextUtils;
import com.swz.blog.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
/**
 * 定义日志切面
 */
public class LogAspect {

    /**
     * 标记定义切点的位置：@annotation(com.swz.blog.common.LogAnnotation)注解所在的位置
     */
    @Pointcut("@annotation(com.swz.blog.common.LogAnnotation)")
    public void logPointCut(){ //定义切点
    }

    /**
     * 定义切面（环绕通知）
     * ProceedingJoinPoint point 切点方法
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        //前置通知
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();//执行方法
        //后置通知
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        recordLog(point, time);
        return result;
    }


    private void recordLog (ProceedingJoinPoint point, long time){
        //拿到切点的签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        //拿到方法
        Method method = signature.getMethod();
        //拿到方法的注解
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=====================log start================================");
        //打印注解的值属性对应的值
        log.info("module:{}",logAnnotation.module());
        log.info("operation:{}",logAnnotation.operator());
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}",className + "." + methodName + "()");

        //请求的参数
        Object[] args = point.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}",params);

        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(request));

        log.info("excute time : {} ms",time);
        log.info("=====================log end================================");
    }

}
