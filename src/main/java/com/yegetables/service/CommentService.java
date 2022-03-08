package com.yegetables.service;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.pojo.Comment;
import com.yegetables.pojo.Content;

import java.util.List;

public interface CommentService {
    ApiResult<Comment> addComment(Comment comment);

    Comment getComment(Comment comment);

    ApiResult<Comment> deleteComment(Comment comment);

    ApiResult<List<Comment>> getCommentsByContent(Content content);

    //    ApiResult<Comment> updateComment(Comment Comment);

}
