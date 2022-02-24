package com.yegetables.service;

import com.yegetables.pojo.Meta;

import java.util.List;

public interface MetaService {
    List<Meta> allCategory();

    List<Meta> allTag();

    List<Meta> allType(String type);

    void updateRedisMetaCacheTag();

    void updateRedisMetaCacheCategory();

    void updateRedisMetaCache();

    void updateRedisMetaCache(String type);
}
