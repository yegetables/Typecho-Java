package com.yegetables.service.impl;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.Content;
import com.yegetables.service.ContentService;
import com.yegetables.utils.PropertiesConfig;
import com.yegetables.utils.TimeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
public class ContentServiceImpl extends BaseServiceImpl implements ContentService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public ApiResult<Content> addContent(Content content) {
        //slug 唯一,parent填充
        if (content == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("参数不能为空");
        if (content.parent() != null)
        {
            Content parent = getContent(content.parent().cid());
            content.parent(parent);
        }
        if (content.slug() != null)
        {
            Content content1 = contentMapper.getContentBySlug(content.slug());
            if (content1 != null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("slug已存在");
        }
        try
        {
            UserServiceImpl.removeSecrets(content.author());
            contentMapper.addContent(content);
            content = getContent(content.cid());
        } catch (Exception e)
        {
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("添加失败");
        }
        return new ApiResult<Content>().code(ApiResultStatus.Success).message("添加成功").data(content);
    }


    public Content getContent(Long cid) {
        String token = getToken(cid);
        var result = getContentByToken(token);
        if (result.code() == ApiResultStatus.Success) return result.data();
        var newUser = contentMapper.getContent(cid);
        if (newUser == null) return null;
        redisTemplate.opsForValue().set(token, newUser, 6, TimeUnit.HOURS);
        return newUser;
    }

    @Override
    @Transactional
    public ApiResult<Content> deleteContent(Content content) {
        if (content == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("参数不能为空");
        String token = getToken(content.cid());
        try
        {
            redisTemplate.delete(token);
            contentMapper.deleteContent(content);
        } catch (Exception e)
        {
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("删除失败");
        }
        return new ApiResult<Content>().code(ApiResultStatus.Success).message("删除成功");

    }

    @Override
    @Transactional
    public ApiResult<Content> updateContent(Content content) {
        if (content == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("参数不能为空");
        String token = getToken(content.cid());
        var result = getContentByToken(token);
        if (result.code() != ApiResultStatus.Success)
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("更新失败");
        if (content.slug() != null && !content.slug().equals(result.data().slug()))
        {
            Content content1 = contentMapper.getContentBySlug(content.slug());
            if (content1 != null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("slug已存在");
        }
        try
        {
            content.modified(TimeTools.NowTime());
            redisTemplate.opsForValue().set(token, content, 6, TimeUnit.HOURS);
            contentMapper.updateContent(content);
        } catch (Exception e)
        {
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("更新失败");
        }
        return new ApiResult<Content>().code(ApiResultStatus.Success).message("更新成功");
    }

    ApiResult<Content> getContentByToken(String token) {
        if (token == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("参数不能为空");
        Content content = (Content) redisTemplate.opsForValue().get(token);
        if (content != null)
            return new ApiResult<Content>().code(ApiResultStatus.Success).message("获取成功").data(content);
        return new ApiResult<Content>().code(ApiResultStatus.Error).message("获取失败,token不存在");
    }

    private String getToken(Long cid) {
        if (cid < 0) return "";
        String token = PropertiesConfig.getApplicationName() + ":content:";
        token += "cid:" + cid;
        token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
        return token;

    }
}
