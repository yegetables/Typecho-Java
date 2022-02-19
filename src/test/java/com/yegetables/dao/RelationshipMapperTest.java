package com.yegetables.dao;

import com.yegetables.pojo.Relationship;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RelationshipMapperTest extends BaseJunit5Test {
    @Autowired
    RelationshipMapper relationshipMapper;
    @Autowired
    ContentMapper contentMapper;
    @Autowired
    MetaMapper metaMapper;

    Relationship createRelationship() {
        Relationship relationship = new Relationship();
        relationship.content(RandomTools.getRandom(contentMapper.getAllContents()));
        relationship.meta(RandomTools.getRandom(metaMapper.getAllMetas()));
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
        var list = relationshipMapper.getMetasByCid(RandomTools.getRandom(relationshipMapper.getAllRelationships()).content().cid());
        System.out.println(list);
    }

    @Test
    void getContentsById() {
        //        var list1 = relationshipMapper.getAllRelationships();
        //        assertNotNull(list1);
        //        var list2 = RandomTools.getRandom((list1));
        //        assertNotNull(list2);
        //        var list3 = list2.meta();
        //        assertNotNull(list3);
        //        var list4 = list3.mid();
        //        assertNotNull(list4);
        //        System.out.println("list4:" + list4);
        var list = relationshipMapper.getContentsByMid(RandomTools.getRandom(relationshipMapper.getAllRelationships()).meta().mid());
        System.out.println(list);
    }

    //            @RepeatedTest(value = 8, name = "测试插入用户")
    @Test
    void addRelationship() {
        var old = createRelationship();
        var n = relationshipMapper.getRelationship(old.content().cid(), old.meta().mid());
        //        n.toString();
        assertEquals(n, old);
    }

    @Test
    void deleteRelationship() {
        var old = createRelationship();
        assertEquals(1, relationshipMapper.deleteRelationship(old.content().cid(), old.meta().mid()));
        var n = relationshipMapper.getRelationship(old.content().cid(), old.meta().mid());
        assertNull(n);
    }
}