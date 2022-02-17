package com.yegetables.dao;

import com.yegetables.pojo.Comment;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import com.yegetables.utils.TimeTools;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
class CommentMapperTest extends BaseJunit5Test {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ContentMapper contentMapper;


    @Test
    void getAllComments() {
        var list = commentMapper.getAllComments();
        list.forEach(System.out::println);
    }

    @Test
    void getAllCommentsByContentId() {
        var list = commentMapper.getAllCommentsByContentId(RandomTools.getRandom(contentMapper.getAllContents()).getCid());
        list.forEach(System.out::println);
    }

    @Test
    void getComment() {
        var list = commentMapper.getComment(RandomTools.getRandom(commentMapper.getAllComments()).getCoid());
        System.out.println(list);
    }

    Comment createComment() {
        Comment comment = new Comment();
        comment.setContent(RandomTools.getRandom(contentMapper.getAllContents()));
        comment.setAuthorName(RandomTools.getRandomName());
        comment.setUrl(RandomTools.getRandomUrl());
        comment.setText(RandomTools.getRandomText(10));
        comment.setCreated(TimeTools.NowTime());
        comment.setOwner(comment.getContent().getAuthor());
        comment.setParent(RandomTools.getRandom(commentMapper.getAllComments()));
        commentMapper.addComment(comment);
        return comment;
    }

    @Test
    void addComment() {
        Comment comment = createComment();
        Comment comment1 = commentMapper.getComment(comment.getCoid());
        assertEquals(comment, comment1);
    }

    @Test
    void updateComment() {
        Comment comment = createComment();
        comment.setText(RandomTools.getRandomText(10));
        commentMapper.updateComment(comment);
        Comment comment1 = commentMapper.getComment(comment.getCoid());
        assertEquals(comment, comment1);
    }

    @Test
    void deleteComment() {
        Comment comment = createComment();
        commentMapper.deleteComment(comment.getCoid());
        Comment comment1 = commentMapper.getComment(comment.getCoid());
        assertEquals(null, comment1);
    }


}