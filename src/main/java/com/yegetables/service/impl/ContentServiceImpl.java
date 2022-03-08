package com.yegetables.service.impl;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.Content;
import com.yegetables.pojo.User;
import com.yegetables.service.ContentService;
import com.yegetables.utils.PropertiesConfig;
import com.yegetables.utils.TimeTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class ContentServiceImpl extends BaseServiceImpl implements ContentService {
    /**
     * 添加文章
     *
     * @param content 文章
     * @return 添加结果
     */
    @Override
    @Transactional
    public ApiResult<Content> addContent(Content content) {
        // slug 唯一,parent填充
        if (content == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("参数不能为空");
        if (content.parent() != null)
        {
            Content parent = getContent(content.parent());
            content.parent(parent);
        }
        if (content.slug() != null)
        {
            var result = getContentByToken(getTokenBySlug(content.slug()));
            if (result.code() == ApiResultStatus.Success)
                return new ApiResult<Content>().code(ApiResultStatus.Error).message("slug已存在");
            Content content1 = contentMapper.getContentBySlug(content.slug());
            if (content1 != null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("slug已存在");
        }
        try
        {
            contentMapper.addContent(content);
            content = getContent(content);
            UserServiceImpl.removeSecrets(content.author());
        } catch (Exception e)
        {
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("添加失败");
        }
        return new ApiResult<Content>().code(ApiResultStatus.Success).message("添加成功").data(content);
    }

    /**
     * 根据cid或者slug获取文章
     *
     * @param content 文章 cid或者slug
     * @return 文章
     */
    public Content getContent(Content content) {
        if (content == null) return null;
        String token = null;
        Content newContent = null;
        if (content.cid() != null)
        {
            token = getTokenByCid(content.cid());
            var result = getContentByToken(token);
            if (result.code() == ApiResultStatus.Success) return result.data();
            newContent = contentMapper.getContent(content.cid());
        }
        if (content.slug() != null)
        {
            token = getTokenBySlug(content.slug());
            var result = getContentByToken(token);
            if (result.code() == ApiResultStatus.Success) return result.data();
            newContent = contentMapper.getContentBySlug(content.slug());
        }

        if (token == null || newContent == null) return null;
        UserServiceImpl.removeSecrets(newContent.author());
        newContent.author(null);
        token = getTokenByCid(newContent.cid());
        redisTemplate.opsForValue().set(token, newContent, 6, TimeUnit.HOURS);
        token = getTokenBySlug(newContent.slug());
        redisTemplate.opsForValue().set(token, newContent, 6, TimeUnit.HOURS);
        return newContent;
    }

    /**
     * 根据token获取文章
     *
     * @param content 文章
     * @return 文章
     */
    @Override
    @Transactional
    public ApiResult<Content> deleteContent(Content content) {
        if (content == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("参数不能为空");

        ArrayList<String> contentList = new ArrayList<>();
        if (content.cid() != null) contentList.add(getTokenByCid(content.cid()));
        if (content.slug() != null) contentList.add(getTokenBySlug(content.slug()));

        try
        {
            //双删
            contentList.forEach(token -> redisTemplate.delete(token));
            contentMapper.deleteContent(content);
            contentList.forEach(token -> redisTemplate.delete(token));
        } catch (Exception e)
        {
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("删除失败");
        }
        return new ApiResult<Content>().code(ApiResultStatus.Success).message("删除成功");

    }

    /**
     * 根据token获取文章
     *
     * @param content 文章
     * @return 文章
     */
    @Override
    @Transactional
    public ApiResult<Content> updateContent(Content content) {
        if (content == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("参数不能为空");
        String token;
        if (content.cid() != null) token = getTokenByCid(content.cid());
        else token = getTokenBySlug(content.slug());

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
            contentMapper.updateContent(content);
            if (content.cid() != null)
            {
                token = getTokenByCid(content.cid());
                redisTemplate.opsForValue().set(token, content, 6, TimeUnit.HOURS);
            }
            if (content.slug() != null)
            {
                token = getTokenBySlug(content.slug());
                redisTemplate.opsForValue().set(token, content, 6, TimeUnit.HOURS);
            }
        } catch (Exception e)
        {
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("更新失败");
        }
        return new ApiResult<Content>().code(ApiResultStatus.Success).message("更新成功").data(content);
    }

    @Override
    public ArrayList<Content> getContentByAuthor(User author) {
        if (author == null) return null;
        var list = contentMapper.getContentsByAuthor(author);
        list.forEach(content -> {
            UserServiceImpl.removeSecrets(content.author());
            content.author().authCode(null);
            String token;
            token = getTokenByCid(content.cid());
            redisTemplate.opsForValue().set(token, content, 6, TimeUnit.HOURS);
            token = getTokenBySlug(content.slug());
            redisTemplate.opsForValue().set(token, content, 6, TimeUnit.HOURS);
        });
        return list;
    }

    ApiResult<Content> getContentByToken(String token) {
        if (token == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("参数不能为空");
        Content content = (Content) redisTemplate.opsForValue().get(token);
        if (content != null)
            return new ApiResult<Content>().code(ApiResultStatus.Success).message("获取成功").data(content);
        return new ApiResult<Content>().code(ApiResultStatus.Error).message("获取失败,token不存在");
    }

    private String getTokenByCid(Long cid) {
        //        if (cid < 0) return "";
        String token = PropertiesConfig.getApplicationName() + ":content:";
        token += "cid:" + cid;
        token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
        return token;
    }

    private String getTokenBySlug(String slug) {
        //        if (cid < 0) return "";
        String token = PropertiesConfig.getApplicationName() + ":content:";
        token += "slug:" + slug;
        token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
        return token;
    }
}
