package com.yegetables.service.impl;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.Content;
import com.yegetables.pojo.User;
import com.yegetables.service.ContentService;
import com.yegetables.utils.PropertiesConfig;
import com.yegetables.utils.TimeTools;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

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
    if (content == null)
      return new ApiResult<Content>().code(ApiResultStatus.Error).message("参数不能为空");
    if (content.parent() != null) {
      Content parent = getContent(content.parent());
      content.parent(parent);
    }
    if (content.slug() != null) {
      Content content1 = contentMapper.getContentBySlug(content.slug());
      if (content1 != null)
        return new ApiResult<Content>().code(ApiResultStatus.Error).message("slug已存在");
    }
    try {
      UserServiceImpl.removeSecrets(content.author());
      contentMapper.addContent(content);
      content = getContent(content);
    } catch (Exception e) {
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
      if (content == null)
        return null;
      String token = null;
      Content newContent = null;
      if (content.cid() != null) {
        token = getToken(content.cid());
        var result = getContentByToken(token);
        if (result.code() == ApiResultStatus.Success)
          return result.data();
        newContent = contentMapper.getContent(content.cid());
      }
      if (content.slug() != null) {
        token = getToken(content.slug());
        var result = getContentByToken(token);
        if (result.code() == ApiResultStatus.Success)
          return result.data();
        newContent = contentMapper.getContentBySlug(content.slug());
      }
      if (token == null)
        return null;
      if (newContent == null)
        return null;
      token = getToken(newContent.cid());
      redisTemplate.opsForValue().set(token, newContent, 6, TimeUnit.HOURS);
      token = getToken(newContent.slug());
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

    @Override
    public ArrayList<Content> getContentByAuthor(User author) {
      return null;
    }

    ApiResult<Content> getContentByToken(String token) {
        if (token == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("参数不能为空");
        Content content = (Content) redisTemplate.opsForValue().get(token);
        if (content != null)
            return new ApiResult<Content>().code(ApiResultStatus.Success).message("获取成功").data(content);
        return new ApiResult<Content>().code(ApiResultStatus.Error).message("获取失败,token不存在");
    }

    private String getToken(Long cid) {
      //        if (cid < 0) return "";
      String token = PropertiesConfig.getApplicationName() + ":content:";
      token += "cid:" + cid;
      token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
      return token;
    }

    private String getToken(String slug) {
      //        if (cid < 0) return "";
      String token = PropertiesConfig.getApplicationName() + ":content:";
      token += "slug:" + slug;
      token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
      return token;
    }
}
