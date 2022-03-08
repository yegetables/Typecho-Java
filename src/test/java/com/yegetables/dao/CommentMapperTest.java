package com.yegetables.dao;

import com.yegetables.pojo.Comment;
import com.yegetables.pojo.CommentStatus;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import com.yegetables.utils.TimeTools;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        var list = commentMapper.getAllCommentsByContentId(RandomTools.getRandom(contentMapper.getAllContents()).cid());
        list.forEach(System.out::println);
    }

    @Test
    void getComment() {
        var list = commentMapper.getComment(RandomTools.getRandom(commentMapper.getAllComments()).coid());
        System.out.println(list);
    }

    Comment createComment() {
        Comment comment = new Comment();
        comment.content(RandomTools.getRandom(contentMapper.getAllContents()));
        comment.authorName(RandomTools.getRandomName());
        comment.url(RandomTools.getRandomUrl());
        comment.text(RandomTools.getRandomText(10));
        comment.created(TimeTools.NowTime());
        comment.status(CommentStatus.approved);
        comment.owner(comment.content().author());
        comment.parent(RandomTools.getRandom(commentMapper.getAllComments()));
        assertEquals(1, commentMapper.addComment(comment));
        return comment;
    }

    @Test
    void addComment() {
        Comment comment = createComment();
        Comment comment1 = commentMapper.getComment(comment.coid());
        //        comment1.toString();
        assertEquals(comment1, comment);
    }

    @Test
    void updateComment() {
        Comment comment = createComment();
        comment.text(RandomTools.getRandomText(10));
        assertEquals(1, commentMapper.updateComment(comment));
        Comment comment1 = commentMapper.getComment(comment.coid());
        //        comment1.toString();
        assertEquals(comment1, comment);

    }

    @Test
    void deleteComment() {
        Comment comment = createComment();
        assertEquals(1, commentMapper.deleteComment(comment.coid()));
        Comment comment1 = commentMapper.getComment(comment.coid());
        assertNull(comment1);
    }


}