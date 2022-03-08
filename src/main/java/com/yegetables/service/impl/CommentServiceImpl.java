package com.yegetables.service.impl;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.Comment;
import com.yegetables.pojo.Content;
import com.yegetables.service.CommentService;
import com.yegetables.service.ContentService;
import com.yegetables.utils.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CommentServiceImpl extends BaseServiceImpl implements CommentService {
    @Autowired
    ContentService contentService;

    @Override
    public ApiResult<Comment> addComment(Comment comment) {

        //parent
        if (comment.parent() == null)
        {
            comment.parent(new Comment().coid(0L));
        }
        comment.parent(getComment(comment.parent()));
        //comtent
        comment.content(contentService.getContent(comment.content()));
        if (comment.content() == null) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("评论文章不存在");
        if (comment.content().author() == null) comment.content().toString();
        //owner
        if (comment.content().author() == null)
            return new ApiResult<Comment>().code(ApiResultStatus.Error).message("评论文章作者不存在");
        comment.owner(comment.content().author());
        try
        {
            commentMapper.addComment(comment);
            comment = getComment(comment);
            redisTemplate.opsForValue().set(getToken(comment.coid()), comment, 6, TimeUnit.HOURS);
        } catch (Exception e)
        {
            return new ApiResult<Comment>().code(ApiResultStatus.Error).message("添加评论失败");
        }
        return new ApiResult<Comment>().code(ApiResultStatus.Success).message("添加评论成功").data(comment);
    }

    @Override
    public Comment getComment(Comment comment) {
        if (comment == null || comment.coid() == null) return null;
        Comment comment1 = (Comment) redisTemplate.opsForValue().get(getToken(comment.coid()));
        if (comment1 != null) return comment1;
        try
        {
            comment1 = commentMapper.getComment(comment.coid());
            if (comment1 != null)
            {
                redisTemplate.opsForValue().set(getToken(comment.coid()), comment1, 6, TimeUnit.HOURS);
                return comment1;
            }
            else
            {
                return null;
            }
        } catch (Exception e)
        {
            return null;
        }
    }


    @Override
    public ApiResult<Comment> deleteComment(Comment comment) {
        comment = getComment(comment);
        if (comment == null) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("评论不存在");
        if (comment.coid() == null) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("评论不存在");
        // if (comment == null) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("评论不存在");
        try
        {
            redisTemplate.delete(getToken(comment.coid()));
            commentMapper.deleteComment(comment);
            redisTemplate.delete(getToken(comment.coid()));
        } catch (Exception e)
        {
            return new ApiResult<Comment>().code(ApiResultStatus.Error).message("删除评论失败");
        }
        return new ApiResult<Comment>().code(ApiResultStatus.Success).message("删除评论成功");
    }

    @Override
    public ApiResult<List<Comment>> getCommentsByContent(Content content) {
        List<Comment> list;
        if (content == null || (content.cid() == null && content.slug() == null))
            return new ApiResult<List<Comment>>().code(ApiResultStatus.Error).message("cid或者 slug不能为空");
        list = commentMapper.getAllCommentsByContent(content);
        //        redisTemplate.opsForList().leftPushAll(getToken(content.cid()), list);TODO
        return new ApiResult<List<Comment>>().code(ApiResultStatus.Success).message("获取评论成功").data(list);
    }

    private String getToken(Long coid) {
        String token = PropertiesConfig.getApplicationName() + ":comment:";
        token += "coid:" + coid;
        token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
        return token;
    }
}
