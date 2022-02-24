package com.yegetables.dao;

import com.yegetables.pojo.Content;
import com.yegetables.pojo.ContentStatus;
import com.yegetables.pojo.ContentType;
import com.yegetables.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ContentMapper {
    ArrayList<Content> getAllContents();

    Content getContent(Long cid);

    ArrayList<Content> getContentsByAuthorId(Long authorId);

    ArrayList<Content> getContentsByType(ContentType type);

    ArrayList<Content> getContentsByStatus(ContentStatus status);
    //    ArrayList<Content> getContentsByParent(Content parent);

    Integer addContent(Content content);

    //不能删除更新外键（author）
    Integer updateContent(Content content);

    //不能删除更新外键（author）
    Integer deleteContent(Long cid);


    default Integer deleteContent(Content content) {
        return deleteContent(content.cid());
    }


    default ArrayList<Content> getContentsByAuthor(Content content) {
        return getContentsByAuthor(content.author());
    }

    default ArrayList<Content> getContentsByAuthor(User author) {
        return getContentsByAuthorId(author.uid());
    }
}
