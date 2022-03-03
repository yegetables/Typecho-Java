package com.yegetables.service.impl;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.Meta;
import com.yegetables.pojo.MetaType;
import com.yegetables.service.MetaService;
import com.yegetables.utils.PropertiesConfig;
import com.yegetables.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        String key = getTypeKey(type);
        Long size = redisTemplate.opsForList().size(key);
        if (size != null && size > 0)
        {
            result = redisTemplate.opsForList().range(key, 0, size);
        }
        else
        {
            result = metaMapper.getMetasByType(type);
            if (result != null && result.size() > 0)
            {
                redisTemplate.opsForList().leftPushAll(key, result);
                result.forEach(meta -> {
                    String slugKey = getSlugKey(meta.getSlug());
                    redisTemplate.opsForValue().set(slugKey, meta, 6, TimeUnit.HOURS);
                });
            }
        }
        return result;
    }

    public Meta getMetaBySlug(String slug) {
        if (slug == null) return null;
        String key = getSlugKey(slug);
        Meta meta = (Meta) redisTemplate.opsForValue().get(key);
        if (meta == null)
        {
            meta = metaMapper.getMetaBySlug(slug);
            if (meta != null)
            {
                redisTemplate.opsForValue().set(key, meta, 6, TimeUnit.HOURS);
                return meta;
            }
            return null;
        }
        else return meta;
    }

    private String getTypeKey(MetaType type) {
        return PropertiesConfig.getApplicationName() + ":meta:" + type.name();
    }

    private String getSlugKey(String slug) {
        if (slug == null) return null;
        slug = StringTools.toOkString(slug);
        return PropertiesConfig.getApplicationName() + ":meta:slug:" + slug;
    }

    public void updateRedisMetaCache(MetaType type) {
        List<Meta> result = null;
        String key = getTypeKey(type);
        result = metaMapper.getMetasByType(type);
        if (result != null && result.size() > 0)
        {
            redisTemplate.opsForList().leftPushAll(key, result);
            result.forEach(meta -> {
                String slugKey = getSlugKey(meta.getSlug());
                redisTemplate.opsForValue().set(slugKey, meta, 6, TimeUnit.HOURS);
            });
        }
        else redisTemplate.delete(key);

    }

    @Override
    @Transactional
    public ApiResult<Meta> addMeta(Meta meta) {
        if (getMetaBySlug(meta.getSlug()) != null)
            return new ApiResult<Meta>().code(ApiResultStatus.Error).message("别名已存在");
        try
        {
            metaMapper.addMeta(meta);
            meta = getMetaBySlug(meta.getSlug());
            if (meta == null) return new ApiResult<Meta>().code(ApiResultStatus.Error).message("添加失败");
            redisTemplate.opsForList().leftPush(getTypeKey(meta.getType()), meta);
            redisTemplate.opsForValue().set(getSlugKey(meta.getSlug()), meta);
        } catch (Exception e)
        {
            return new ApiResult<Meta>().code(ApiResultStatus.Error).message("添加失败");
        }
        return new ApiResult<Meta>().message("成功").data(meta);
    }

    @Override
    @Transactional
    public ApiResult<Meta> deleteMetaBySlug(String slug) {
        var meta = getMetaBySlug(slug);
        if (meta == null) return new ApiResult<Meta>().code(ApiResultStatus.Error).message("别名不存在");
        try
        {
            metaMapper.deleteMeta(meta.getMid());
            updateRedisMetaCache(meta.type());
            redisTemplate.delete(getSlugKey(meta.getSlug()));
        } catch (Exception e)
        {
            return new ApiResult<Meta>().code(ApiResultStatus.Error).message("删除失败");
        }
        return new ApiResult<Meta>().message("成功").data(meta);
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
