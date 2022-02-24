package com.yegetables.dao;

import com.yegetables.pojo.Meta;
import com.yegetables.pojo.MetaType;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        var list = metaMapper.getMetasByType(MetaType.category);
        list.forEach(System.out::println);
    }

    @Test
    void getMeta() {
        var meta = metaMapper.getMeta(RandomTools.getRandom(metaMapper.getAllMetas()).mid());
        System.out.println(meta);
    }

    Meta createMeta() {
        var meta = new Meta();
        meta.name(RandomTools.getRandomName());
        meta.slug(RandomTools.getRandomName());
        meta.description(RandomTools.getRandomText(10));
        meta.parent(RandomTools.getRandom(metaMapper.getAllMetas()));
        meta.type(MetaType.test);
        assertEquals(1, metaMapper.addMeta(meta));
        return meta;
    }

    @Test
    void addMeta() {
        var meta = createMeta();
        var newMeta = metaMapper.getMeta(meta.mid());
        //        newMeta.toString();
        assertEquals(newMeta, meta);
    }

    @Test
    void updateMeta() {
        var newMeta = createMeta();
        newMeta.name(RandomTools.getRandomName());
        newMeta.type(MetaType.tag);
        assertEquals(1, metaMapper.updateMeta(newMeta));
        var updateMeta = metaMapper.getMeta(newMeta.mid());
        //        updateMeta.toString();
        assertEquals(updateMeta, newMeta);
        assertEquals(1, metaMapper.deleteMeta(updateMeta));
    }

    @Test
    void deleteMeta() {
        var meta = createMeta();
        assertEquals(1, metaMapper.deleteMeta(meta.mid()));
        var deleteMeta = metaMapper.getMeta(meta.mid());
        assertNull(deleteMeta);
    }
}