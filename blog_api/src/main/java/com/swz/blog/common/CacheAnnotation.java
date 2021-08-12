package com.swz.blog.common;

import java.lang.annotation.*;

/**
 * 缓存注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheAnnotation {
    /**
     * 指定缓存的时间
     */
    long expire () default 1 * 60; //默认为 1 分钟

    /**
     * 指定缓存的名称
     */
    String name () default "";
}
