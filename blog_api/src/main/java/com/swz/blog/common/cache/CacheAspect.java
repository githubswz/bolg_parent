package com.swz.blog.common.cache;

import com.alibaba.fastjson.JSON;
import com.swz.blog.common.CacheAnnotation;
import com.swz.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * aop定义缓存功能
 */
@Component
@Aspect
@Slf4j
public class CacheAspect {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Pointcut("@annotation(com.swz.blog.common.CacheAnnotation)")
    public void cachePointCut (){
    }

    /**
     * 通知功能：根据前端传递的url来进行缓存查询
     */
    @Around("cachePointCut()")
    public Object around (ProceedingJoinPoint pjp){
        try {
            Signature signature = pjp.getSignature();
            //1.拿到方法参数中的数据(存放缓存时可以根据参数存放)
            //类名
            String className = pjp.getTarget().getClass().getSimpleName();
            //方法名
            String methodName = signature.getName();//标签名称
            //参数的类型
            Class[] parameterTypes = new Class[pjp.getArgs().length];
            //参数
            Object[] args = pjp.getArgs();
            String params = "";
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                } else {
                    parameterTypes[i] = null;
                }
            }

            if (StringUtils.isNotEmpty(params)) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            //获取注解的信息
            Method method = signature.getDeclaringType().getMethod(methodName, parameterTypes);
            CacheAnnotation annotation = method.getAnnotation(CacheAnnotation.class);
            //过期的时间
            long expire = annotation.expire();
            //缓存的名称
            String name = annotation.name();
            //先从redis获取的key
            String redisKey = name + "::" + className + "::" + methodName + "::" + params;
            String s = (String) redisTemplate.opsForValue().get(redisKey);
            //2.有就从缓存返回数据
            if (StringUtils.isNoneBlank(s)) {
            Result result = JSON.parseObject(s, Result.class);
                log.info("走了缓存{}",result);
                return result;
            }
            //3.执行代码，并且将结果存放到redis
            Result res = (Result) pjp.proceed();
            String processResult = JSON.toJSONString(res);
            //4.存放数据
            redisTemplate.opsForValue().set(redisKey, processResult);
            log.info("存放了数据K: {},V: {}",redisKey,processResult);
            return res;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.info("controller中的方法执行有误");
        }
        return Result.fail(-9999, "代码有误");
    }

}
