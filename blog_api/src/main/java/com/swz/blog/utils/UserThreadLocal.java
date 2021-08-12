package com.swz.blog.utils;

import com.swz.blog.pojo.SysUser;

/**
 * @author : 苏文致
 * @date Date : 2021年07月22日 16:29
 * @Description: TODO:
 */

public class UserThreadLocal {

   private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

   public static SysUser get(){
       return LOCAL.get();
   }

   public static void set(SysUser sysUser){
       LOCAL.set(sysUser);
   }

   public static void delete(){
       LOCAL.remove();
   }

}
