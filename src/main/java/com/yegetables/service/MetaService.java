package com.yegetables.service;

import com.yegetables.pojo.Meta;
import com.yegetables.pojo.MetaType;

import java.util.List;

public interface MetaService {
    List<Meta> allCategory();

    List<Meta> allTag();

    List<Meta> allType(MetaType type);

    void updateRedisMetaCacheTag();

    void updateRedisMetaCacheCategory();

    void updateRedisMetaCache();

    void updateRedisMetaCache(MetaType type);
}
