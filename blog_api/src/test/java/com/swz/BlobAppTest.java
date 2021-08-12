package com.swz;

import com.swz.blog.controller.LoginController;
import com.swz.blog.dao.ArticleDao;
import com.swz.blog.service.ArticleService;
import com.swz.blog.vo.Result;
import com.swz.blog.vo.param.LoginParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月22日 12:25
 * @Description: TODO:
 */
@SpringBootTest
public class BlobAppTest {
    @Autowired
    private LoginController loginController;

    @Test
    public void testLogin (){
        LoginParam loginParam = new LoginParam();
        loginParam.setAccount("admin");
        loginParam.setPassword("admin");
        Result login = loginController.login(loginParam);
        System.out.println(login);
    }

    @Test
    public void test (){

        ThreadLocal<String> threadLocal1 = new ThreadLocal<>();
        threadLocal1.set("hello");
        ThreadLocal<String> threadLocal2 = new ThreadLocal<>();
        threadLocal1.set("world...");
        ThreadLocal<String> threadLocal3 = new ThreadLocal<>();
        threadLocal1.set("!!!!");
        String s1 = threadLocal1.get();
        String s2 = threadLocal2.get();
        String s3 = threadLocal3.get();
        System.out.println("==========================================>>>>>");

    }

    @Autowired
    private ArticleDao articleDao;

    @Test
    public void test3 (){
        List<Map<String, Object>> list = articleDao.selectDate();
        list.forEach(System.out::println);
    }

    @Autowired
    ArticleService articleService;

    @Test
    public void test4 (){
        Result articleById = articleService.findArticleById(1405916999732707330L);
        System.out.println(articleById);
    }

    @Test
    public void test5 (){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        //for (String s : list) {
        //    if (s.equals("a")) {
        //        list.remove("a");
        //    }
        //}
        String a = null;
        for (int i = 0; i < list.size(); i++) {
            if ("a".equals((a = list.get(i)))) {
                list.remove(a);
            }
        }
        list.forEach(System.out::println);
    }

}
