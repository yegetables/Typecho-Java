package com.yegetables.service.impl;

import com.yegetables.pojo.Meta;
import com.yegetables.pojo.MetaType;
import com.yegetables.service.MetaService;
import com.yegetables.utils.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaServiceImpl extends BaseServiceImpl implements MetaService {
    @Autowired
    protected RedisTemplate<String, Meta> redisTemplate;

    public List<Meta> allCategory() {
        return allType(MetaType.category);
    }

    public List<Meta> allTag() {
        return allType(MetaType.tag);
    }

    @Override
    public List<Meta> allType(MetaType type) {
        List<Meta> result = null;
        //        type = StringTools.toOkString(type);
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


    private String getTypeKey(MetaType type) {
        return PropertiesConfig.getApplicationName() + ":meta:" + type.name();
    }

    public void updateRedisMetaCache(MetaType type) {
        List<Meta> result = null;
        String key = getTypeKey(type);
        redisTemplate.delete(key);
        result = metaMapper.getMetasByType(type);
        if (result != null && result.size() > 0) redisTemplate.opsForList().leftPushAll(key, result);
    }

    public void updateRedisMetaCache() {
        for (MetaType type : MetaType.values())
        {
            updateRedisMetaCache(type);
        }
    }

    public void updateRedisMetaCacheCategory() {
        updateRedisMetaCache(MetaType.category);
    }

    public void updateRedisMetaCacheTag() {
        updateRedisMetaCache(MetaType.tag);
    }
}
