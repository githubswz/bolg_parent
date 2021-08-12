package com.swz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swz.blog.dao.CommentDao;
import com.swz.blog.pojo.Comment;
import com.swz.blog.service.CommentService;
import com.swz.blog.service.SysUserService;
import com.swz.blog.utils.UserThreadLocal;
import com.swz.blog.vo.CommentVo;
import com.swz.blog.vo.Result;
import com.swz.blog.vo.UserVo;
import com.swz.blog.vo.param.CommentParam;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月23日 13:23
 * @Description: TODO:
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    SysUserService sysUserService;

    /**
     * 1.查询所有的评论
     * 2.转为vo
     */
    @Override
    public Result getComment (Long articleId){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        List<Comment> comments = commentDao.selectList(wrapper);
        List<CommentVo> commentVos = copyList(comments);
        return Result.success(commentVos);
    }

    /**
     * 添加评论
     *
     * @param commentParam
     * @return
     */
    @Override
    public Result comment (CommentParam commentParam){
        Long parent = commentParam.getParent();
        if (ObjectUtils.isEmpty(parent)) parent = 0L; //表示没有父评论，当前在第一层
        Long toUserId = commentParam.getToUserId();
        toUserId = toUserId == null ? 0 : toUserId;
        String content = commentParam.getContent();
        Long articleId = commentParam.getArticleId();
        Long createdate = System.currentTimeMillis();
        Long autherId = UserThreadLocal.get().getId();//拿到当前用户的id
        Comment parentComment = commentDao.selectById(parent);//拿到父评论
        Long level = parentComment == null ? 1 : parentComment.getLevel() + 1;
        Comment comment = new Comment(content, createdate, articleId, autherId, parent, toUserId, level);
        commentDao.insert(comment);
        return Result.success(null);
    }

    //变量count用来记录变里的层数
    private int count = 1;

    private List<CommentVo> copyList (List<Comment> comments){
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getLevel() == count) {
                CommentVo commentVo = copy(comment);
                commentVoList.add(commentVo);
            }
        }
        return commentVoList;
    }


    private CommentVo copy (Comment comment){
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        //查询评论的用户
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.selectById(authorId);
        commentVo.setAuthor(userVo);
        //查询创建的时间
        Long createDate = comment.getCreateDate();
        Date date = new Date(createDate);
        commentVo.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        //处理回复的对象
        if (comment.getLevel() != 1 && comment.getToUid() != 0) {
            UserVo toUserVo = sysUserService.selectById(comment.getToUid());
            commentVo.setToUser(toUserVo);
        }
        //处理评论的评论
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", comment.getId());
        //拿到所有的子评论
        List<Comment> comments = commentDao.selectList(wrapper);
        if (comment != null) {
            count++;
            List<CommentVo> commentVos = copyList(comments);
            commentVo.setChildrens(commentVos);
            count--;
        }
        return commentVo;
    }
}
