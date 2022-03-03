package com.yegetables.service;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.pojo.Content;
import com.yegetables.pojo.User;

import java.util.ArrayList;

public interface ContentService {
    ApiResult<Content> addContent(Content content);

    Content getContent(Content content);

    ApiResult<Content> deleteContent(Content content);

    ApiResult<Content> updateContent(Content content);

    ArrayList<Content> getContentByAuthor(User author);
}
