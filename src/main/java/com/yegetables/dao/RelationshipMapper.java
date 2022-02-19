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

    Integer addRelationship(@Param("cid") Long cid, @Param("mid") Long mid);

    Integer deleteRelationship(@Param("cid") Long cid, @Param("mid") Long mid);
    //void updateRelationship(Long cid, Long mid);

    default Integer deleteRelationship(Content content, Meta meta) {
        return deleteRelationship(content.cid(), meta.mid());
    }

    default Integer deleteRelationship(Relationship relationship) {
        return deleteRelationship(relationship.content(), relationship.meta());
    }

    default Integer addRelationship(Content content, Meta meta) {
        return addRelationship(content.cid(), meta.mid());
    }

    default Integer addRelationship(Relationship relationship) {
        return addRelationship(relationship.content(), relationship.meta());
    }

    default ArrayList<Content> getContents(Meta meta) {
        return getContentsByMid(meta.mid());
    }

    default ArrayList<Meta> getMetas(Content content) {
        return getMetasByCid(content.cid());
    }

}
