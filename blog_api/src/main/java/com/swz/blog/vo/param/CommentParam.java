package com.swz.blog.vo.param;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CommentParam {
    /**
     * 分布式id中太长了，传递到前端会有精度的损失，需要将long转化为string类型
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    private String content;

    /**
     * 父评论的id
     */
    private Long parent;

    /**
     * 父评论用户的id
     */
    private Long toUserId;
}
