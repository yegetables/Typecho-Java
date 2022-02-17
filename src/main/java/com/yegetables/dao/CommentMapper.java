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

    void addComment(Comment comment);

    void updateComment(Comment comment);

    void deleteComment(Long commentId);

    default ArrayList<Comment> getAllCommentsByContent(Content content) {return getAllCommentsByContentId(content.getCid());}

    default void deleteComment(Comment comment) {
        deleteComment(comment.getCoid());
    }
}



