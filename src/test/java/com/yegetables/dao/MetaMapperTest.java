package com.yegetables.dao;

import com.yegetables.pojo.Meta;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetaMapperTest extends BaseJunit5Test {

    @Autowired
    MetaMapper metaMapper;

    @Test
    void getAllMetas() {
        var list = metaMapper.getAllMetas();
        list.forEach(System.out::println);
    }

    @Test
    void getMetasByType() {
        var list = metaMapper.getMetasByType("category");
        list.forEach(System.out::println);
    }

    @Test
    void getMeta() {
        var meta = metaMapper.getMeta(RandomTools.getRandom(metaMapper.getAllMetas()).getMid());
        System.out.println(meta);
    }

    Meta createMeta() {
        var meta = new Meta();
        meta.setName(RandomTools.getRandomName());
        meta.setSlug(RandomTools.getRandomName());
        meta.setDescription(RandomTools.getRandomText(10));
        meta.setParent(RandomTools.getRandom(metaMapper.getAllMetas()));
        meta.setType("test");
        metaMapper.addMeta(meta);
        return meta;
    }

    @Test
    void insertMeta() {
        var meta = createMeta();
        var newMeta = metaMapper.getMeta(meta.getMid());
        System.out.println("newMeta = " + newMeta);
        System.out.println("meta = " + meta);
        assertEquals(meta, newMeta);
    }

    @Test
    void updateMeta() {
        var newMeta = createMeta();
        newMeta.setName(RandomTools.getRandomName());
        metaMapper.updateMeta(newMeta);
        var updateMeta = metaMapper.getMeta(newMeta.getMid());
        assertEquals(newMeta, updateMeta);
        metaMapper.deleteMeta(updateMeta);

    }

    @Test
    void deleteMeta() {
        var meta = createMeta();
        metaMapper.deleteMeta(meta.getMid());
        var deleteMeta = metaMapper.getMeta(meta.getMid());
        assertEquals(null, deleteMeta);
    }
}