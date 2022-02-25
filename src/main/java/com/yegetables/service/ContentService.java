package com.yegetables.service;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.pojo.Content;

public interface ContentService {
    ApiResult<Content> addContent(Content content);
}
