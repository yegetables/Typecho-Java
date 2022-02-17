package com.yegetables.dao;

import com.yegetables.pojo.Meta;

import java.util.ArrayList;

public interface MetaMapper {
    ArrayList<Meta> getAllMetas();

    ArrayList<Meta> getMetasByType(String type);

    Meta getMeta(Long mid);

    void addMeta(Meta meta);

    void updateMeta(Meta meta);

    void deleteMeta(Long mid);

    default void deleteMeta(Meta meta) {
        deleteMeta(meta.getMid());
    }
}
