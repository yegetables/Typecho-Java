package com.yegetables.dao;

import com.yegetables.pojo.Content;
import com.yegetables.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ContentMapper {
    ArrayList<Content> getAllContents();

    Content getContent(Long cid);

    ArrayList<Content> getContentsByAuthorId(Long authorId);

    ArrayList<Content> getContentsByType(String type);

    ArrayList<Content> getContentsByStatus(String status);
    //    ArrayList<Content> getContentsByParent(Content parent);

    void addContent(Content content);

    //不能删除更新外键（author）
    void updateContent(Content content);

    //不能删除更新外键（author）
    void deleteContent(Long cid);


    default void deleteContent(Content content) {
        deleteContent(content.getCid());
    }


    default ArrayList<Content> getContentsByAuthor(Content content) {
        return getContentsByAuthor(content.getAuthor());
    }

    default ArrayList<Content> getContentsByAuthor(User author) {
        return getContentsByAuthorId(author.getUid());
    }
}
