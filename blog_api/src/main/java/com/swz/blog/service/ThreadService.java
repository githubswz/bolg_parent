package com.swz.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.swz.blog.dao.ArticleDao;
import com.swz.blog.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 11:38
 * @Description: TODO:
 */
@Component
public class ThreadService {

    @Async("taskExecutor") //从线程池中拿到线程，通过多线程的方式去执行该操作
    public void update (Article article, ArticleDao articleDao){
        article.setViewCounts(article.getViewCounts() + 1);
        UpdateWrapper<Article> wrapper = new UpdateWrapper<>();
        wrapper.set("view_counts", article.getViewCounts())
                .eq("id", article.getId())
                .eq("view_counts", article.getViewCounts() - 1);
        articleDao.update(article, wrapper);
        try {
            //睡眠5秒 证明不会影响主线程的使用
            Thread.sleep(5000);
            //System.out.println("========================>>>>>>多线程添加结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
