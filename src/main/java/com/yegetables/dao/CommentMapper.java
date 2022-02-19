package com.yegetables.dao;

import com.yegetables.pojo.Comment;
import com.yegetables.pojo.Content;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentMapper {
    ArrayList<Comment> getAllComments();

    ArrayList<Comment> getAllCommentsByContentId(Long commentId);

    Comment getComment(Long coid);

    Integer addComment(Comment comment);

    Integer updateComment(Comment comment);

    Integer deleteComment(Long commentId);

    default ArrayList<Comment> getAllCommentsByContent(Content content) {return getAllCommentsByContentId(content.cid());}

    default Integer deleteComment(Comment comment) {
        return deleteComment(comment.coid());
    }
}



