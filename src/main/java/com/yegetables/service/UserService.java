package com.yegetables.service;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.pojo.User;

public interface UserService {

    ApiResult<String> sendEmailAuthorCode(String email);

    ApiResult<User> register(String username, String password, String mail, String code);

    ApiResult<User> login(String username, String password);

    ApiResult<User> changeUserInfo(User user);

    User getUser(User user);

}
