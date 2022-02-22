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
    static String emailRegex = "^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";

    public static String getEmailRegex() {
        return emailRegex;
    }

    //    @Value("${user.Email.RegExp.regexp}")
    public void setEmailRegex(String emailRegex) {
        StringTools.emailRegex = emailRegex;
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
