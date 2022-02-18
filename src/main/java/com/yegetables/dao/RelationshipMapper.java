package com.yegetables.dao;

import com.yegetables.pojo.Content;
import com.yegetables.pojo.Meta;
import com.yegetables.pojo.Relationship;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface RelationshipMapper {
    ArrayList<Relationship> getAllRelationships();

    Relationship getRelationship(@Param("cid") Long cid, @Param("mid") Long mid);

    ArrayList<Meta> getMetasByCid(Long cid);

    ArrayList<Content> getContentsByMid(Long mid);

    void addRelationship(@Param("cid") Long cid, @Param("mid") Long mid);

    void deleteRelationship(@Param("cid") Long cid, @Param("mid") Long mid);
    //void updateRelationship(Long cid, Long mid);

    default void deleteRelationship(Content content, Meta meta) {
        deleteRelationship(content.getCid(), meta.getMid());
    }

    default void deleteRelationship(Relationship relationship) {
        deleteRelationship(relationship.getContent(), relationship.getMeta());
    }

    default void addRelationship(Content content, Meta meta) {
        addRelationship(content.getCid(), meta.getMid());
    }

    default void addRelationship(Relationship relationship) {
        addRelationship(relationship.getContent(), relationship.getMeta());
    }

    default ArrayList<Content> getContents(Meta meta) {
        return getContentsByMid(meta.getMid());
    }

    default ArrayList<Meta> getMetas(Content content) {
        return getMetasByCid(content.getCid());
    }

}
