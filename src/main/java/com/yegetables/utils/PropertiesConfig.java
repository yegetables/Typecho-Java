package com.yegetables.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PropertiesConfig {
    static int nameMaxLength;
    static int nameMinLength;
    static int emailMaxLength;
    static int emailMinLength;
    static int passwordMaxLength;
    static int passwordMinLength;
    static int authCodeLength;
    static int urlMaxLength;
    static int urlMinLength;
    static int screenNameMaxLength;
    static int screenNameMinLength;
    static int groupNameMaxLength;
    static int groupNameMinLength;
    static String ApplicationName;
    static String emailRegex = "^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";

    public static String getApplicationName() {
        return ApplicationName;
    }

    public static int getUrlMaxLength() {
        return urlMaxLength;
    }

    @Value("${user.Url.MaxLength}")
    public void setUrlMaxLength(int urlMaxLength) {
        PropertiesConfig.urlMaxLength = urlMaxLength;
    }

    public static int getUrlMinLength() {
        return urlMinLength;
    }

    @Value("${user.Url.MinLength}")
    public void setUrlMinLength(int urlMinLength) {
        PropertiesConfig.urlMinLength = urlMinLength;
    }

    public static int getScreenNameMaxLength() {
        return screenNameMaxLength;
    }

    @Value("${user.ScreenName.MaxLength}")
    public void setScreenNameMaxLength(int screenNameMaxLength) {
        PropertiesConfig.screenNameMaxLength = screenNameMaxLength;
    }

    public static int getScreenNameMinLength() {
        return screenNameMinLength;
    }

    @Value("${user.ScreenName.MinLength}")
    public void setScreenNameMinLength(int screenNameMinLength) {
        PropertiesConfig.screenNameMinLength = screenNameMinLength;
    }

    public static int getGroupNameMaxLength() {
        return groupNameMaxLength;
    }

    @Value("${user.Group.MaxLength}")
    public void setGroupNameMaxLength(int groupNameMaxLength) {
        PropertiesConfig.groupNameMaxLength = groupNameMaxLength;
    }

    public static int getGroupNameMinLength() {
        return groupNameMinLength;
    }

    @Value("${user.Group.MinLength}")
    public void setGroupNameMinLength(int groupNameMinLength) {
        PropertiesConfig.groupNameMinLength = groupNameMinLength;
    }

    @Value("${application.name}")
    public PropertiesConfig setApplicationName(String applicationName) {
        ApplicationName = applicationName;
        return this;
    }


    public static String getEmailRegex() {
        return emailRegex;
    }


    //    @Value("${user.Email.RegExp.regexp}")
    public PropertiesConfig setEmailRegex(String emailRegex) {
        emailRegex = emailRegex;
        return this;

    }

    public static int getNameMaxLength() {
        return nameMaxLength;
    }

    @Value("${user.Name.MaxLength}")
    public PropertiesConfig setNameMaxLength(int nameMaxLength) {
        PropertiesConfig.nameMaxLength = nameMaxLength;
        return this;
    }

    public static int getNameMinLength() {
        return nameMinLength;
    }

    @Value("${user.Name.MinLength}")
    public PropertiesConfig setNameMinLength(int nameMinLength) {
        PropertiesConfig.nameMinLength = nameMinLength;
        return this;
    }

    public static int getEmailMaxLength() {
        return emailMaxLength;
    }

    @Value("${user.Email.MaxLength}")
    public PropertiesConfig setEmailMaxLength(int emailMaxLength) {
        PropertiesConfig.emailMaxLength = emailMaxLength;
        return this;
    }

    public static int getEmailMinLength() {
        return emailMinLength;
    }

    @Value("${user.Email.MinLength}")
    public PropertiesConfig setEmailMinLength(int emailMinLength) {
        PropertiesConfig.emailMinLength = emailMinLength;
        return this;
    }

    public static int getPasswordMaxLength() {
        return passwordMaxLength;
    }

    @Value("${user.Password.MaxLength}")
    public PropertiesConfig setPasswordMaxLength(int passwordMaxLength) {
        PropertiesConfig.passwordMaxLength = passwordMaxLength;
        return this;
    }

    public static int getPasswordMinLength() {
        return passwordMinLength;
    }

    @Value("${user.Password.MinLength}")
    public PropertiesConfig setPasswordMinLength(int passwordMinLength) {
        PropertiesConfig.passwordMinLength = passwordMinLength;
        return this;
    }

    public static int getAuthCodeLength() {
        return authCodeLength;
    }

    @Value("${user.AuthCode.Length}")
    public PropertiesConfig setAuthCodeLength(int authCodeLength) {
        PropertiesConfig.authCodeLength = authCodeLength;
        return this;
    }

}
