package com.swz.blog.controller;

import com.swz.blog.utils.QiniuUtils;
import com.swz.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 七牛云上传md文件的图片信息
 */

@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {

    @PostMapping
    public Result upload (@RequestParam("image") MultipartFile image){
        String originalFilename = image.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String houzui = originalFilename.substring(i - 1);
        //上传的文件名称
        String fileName = UUID.randomUUID().toString() + houzui;
        byte[] bytes = null;
        try {
            bytes = image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("===================文件上传有误================");
        }
        //文件上传
        QiniuUtils.upload2Qiniu(bytes,fileName);

        //上传成功....
        //将文件图片信息保存到redis的一个集合中
        //制定定时任务
        return Result.success("http://qwqjodrkx.hn-bkt.clouddn.com/"+fileName);
    }

}
