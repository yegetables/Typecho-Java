package com.yegetables.dao;

import com.yegetables.pojo.Meta;

import java.util.ArrayList;

public interface MetaMapper {
    ArrayList<Meta> getAllMetas();

    ArrayList<Meta> getMetasByType(String type);

    Meta getMeta(Long mid);

    Integer addMeta(Meta meta);


    Integer updateMeta(Meta meta);

    Integer deleteMeta(Long mid);

    default Integer deleteMeta(Meta meta) {
        return deleteMeta(meta.mid());
    }
}
