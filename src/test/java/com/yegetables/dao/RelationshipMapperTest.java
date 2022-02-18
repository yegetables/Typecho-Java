package com.yegetables.dao;

import com.yegetables.pojo.Relationship;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RelationshipMapperTest extends BaseJunit5Test {
    @Autowired
    RelationshipMapper relationshipMapper;
    @Autowired
    ContentMapper contentMapper;
    @Autowired
    MetaMapper metaMapper;

    Relationship createRelationship() {
        Relationship relationship = new Relationship();
        relationship.setContent(RandomTools.getRandom(contentMapper.getAllContents()));
        relationship.setMeta(RandomTools.getRandom(metaMapper.getAllMetas()));
        relationshipMapper.addRelationship(relationship);
        return relationship;
    }

    @Test
    void getAllRelationships() {
        var list = relationshipMapper.getAllRelationships();
        System.out.println(list);
    }

    @Test
    void getMetasById() {
        var list = relationshipMapper.getMetasByCid(RandomTools.getRandom(relationshipMapper.getAllRelationships()).getContent().getCid());
        System.out.println(list);
    }

    @Test
    void getContentsById() {
        var list = relationshipMapper.getContentsByMid(RandomTools.getRandom(relationshipMapper.getAllRelationships()).getMeta().getMid());
        System.out.println(list);
    }

    //            @RepeatedTest(value = 8, name = "测试插入用户")
    @Test
    void addRelationship() {
        var old = createRelationship();
        var n = relationshipMapper.getRelationship(old.getContent().getCid(), old.getMeta().getMid());
//        System.out.println(n);
//        System.out.println(old);
        assertEquals(old, n);
    }

    @Test
    void deleteRelationship() {
        var old = createRelationship();
        relationshipMapper.deleteRelationship(old.getContent().getCid(), old.getMeta().getMid());
        var n = relationshipMapper.getRelationship(old.getContent().getCid(), old.getMeta().getMid());
        assertEquals(null, n);
    }
}