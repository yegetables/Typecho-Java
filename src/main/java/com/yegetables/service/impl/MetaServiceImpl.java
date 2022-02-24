package com.yegetables.service.impl;

import com.yegetables.pojo.Meta;
import com.yegetables.service.MetaService;
import com.yegetables.utils.PropertiesConfig;
import com.yegetables.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaServiceImpl extends BaseServiceImpl implements MetaService {
    @Autowired
    protected RedisTemplate<String, Meta> redisTemplate;

    public List<Meta> allCategory() {
        return allType("category");
    }

    public List<Meta> allTag() {
        return allType("tag");
    }

    @Override
    public List<Meta> allType(String type) {
        List<Meta> result = null;
        type = StringTools.toOkString(type);
        String key = getTypeKey(type);
        Long size = redisTemplate.opsForList().size(key);
        if (size != null && size > 0)
        {
            result = redisTemplate.opsForList().range(key, 0, size);
        }
        else
        {
            result = metaMapper.getMetasByType(type);
            if (result != null && result.size() > 0) redisTemplate.opsForList().leftPushAll(key, result);
        }
        return result;
    }


    private String getTypeKey(String type) {
        return PropertiesConfig.getApplicationName() + ":meta:" + type;
    }

    public void updateRedisMetaCache(String type) {
        List<Meta> result = null;
        String key = getTypeKey(type);
        redisTemplate.delete(key);
        result = metaMapper.getMetasByType(type);
        if (result != null && result.size() > 0) redisTemplate.opsForList().leftPushAll(key, result);
    }

    public void updateRedisMetaCache() {
        updateRedisMetaCache("category");
        updateRedisMetaCache("tag");
        updateRedisMetaCache("test");
    }

    public void updateRedisMetaCacheCategory() {
        updateRedisMetaCache("category");
    }

    public void updateRedisMetaCacheTag() {
        updateRedisMetaCache("tag");
    }
}
