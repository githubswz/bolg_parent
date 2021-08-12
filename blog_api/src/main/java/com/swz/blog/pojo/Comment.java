package com.swz.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Long level;

    public Comment (String content, Long createDate, Long articleId, Long authorId, Long parentId, Long toUid, Long level){
        this.content = content;
        this.createDate = createDate;
        this.articleId = articleId;
        this.authorId = authorId;
        this.parentId = parentId;
        this.toUid = toUid;
        this.level = level;
    }

    private static final long serialVersionUID = 1L;
}