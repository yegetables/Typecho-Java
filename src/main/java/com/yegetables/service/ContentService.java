package com.yegetables.service;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.pojo.Content;

public interface ContentService {
    ApiResult<Content> addContent(Content content);

    Content getContent(Long cid);

    ApiResult<Content> deleteContent(Content content);

    ApiResult<Content> updateContent(Content content);
}
