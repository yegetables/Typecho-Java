package com.yegetables.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PropertiesConfig {

    public static class User {
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

        public static int getUrlMaxLength() {
            return urlMaxLength;
        }

        @Value("${user.Url.MaxLength}")
        public void setUrlMaxLength(int urlMaxLength) {
            User.urlMaxLength = urlMaxLength;
        }

        public static int getUrlMinLength() {
            return urlMinLength;
        }

        @Value("${user.Url.MinLength}")
        public void setUrlMinLength(int urlMinLength) {
            User.urlMinLength = urlMinLength;
        }

        public static int getScreenNameMaxLength() {
            return screenNameMaxLength;
        }

        @Value("${user.ScreenName.MaxLength}")
        public void setScreenNameMaxLength(int screenNameMaxLength) {
            User.screenNameMaxLength = screenNameMaxLength;
        }

        public static int getScreenNameMinLength() {
            return screenNameMinLength;
        }

        @Value("${user.ScreenName.MinLength}")
        public void setScreenNameMinLength(int screenNameMinLength) {
            User.screenNameMinLength = screenNameMinLength;
        }

        public static int getGroupNameMaxLength() {
            return groupNameMaxLength;
        }

        @Value("${user.Group.MaxLength}")
        public void setGroupNameMaxLength(int groupNameMaxLength) {
            User.groupNameMaxLength = groupNameMaxLength;
        }

        public static int getGroupNameMinLength() {
            return groupNameMinLength;
        }

        @Value("${user.Group.MinLength}")
        public void setGroupNameMinLength(int groupNameMinLength) {
            User.groupNameMinLength = groupNameMinLength;
        }


        public static int getNameMaxLength() {
            return nameMaxLength;
        }

        @Value("${user.Name.MaxLength}")
        public void setNameMaxLength(int nameMaxLength) {
            User.nameMaxLength = nameMaxLength;
        }

        public static int getNameMinLength() {
            return nameMinLength;
        }

        @Value("${user.Name.MinLength}")
        public void setNameMinLength(int nameMinLength) {

            User.nameMinLength = nameMinLength;
        }

        public static int getEmailMaxLength() {
            return emailMaxLength;
        }

        @Value("${user.Email.MaxLength}")
        public void setEmailMaxLength(int emailMaxLength) {
            User.emailMaxLength = emailMaxLength;
        }

        public static int getEmailMinLength() {
            return emailMinLength;
        }

        @Value("${user.Email.MinLength}")
        public void setEmailMinLength(int emailMinLength) {
            User.emailMinLength = emailMinLength;
        }

        public static int getPasswordMaxLength() {
            return passwordMaxLength;
        }

        @Value("${user.Password.MaxLength}")
        public void setPasswordMaxLength(int passwordMaxLength) {
            User.passwordMaxLength = passwordMaxLength;
        }

        public static int getPasswordMinLength() {
            return passwordMinLength;
        }

        @Value("${user.Password.MinLength}")
        public void setPasswordMinLength(int passwordMinLength) {
            User.passwordMinLength = passwordMinLength;

        }

        public static int getAuthCodeLength() {
            return authCodeLength;
        }

        @Value("${user.AuthCode.Length}")
        public void setAuthCodeLength(int authCodeLength) {
            User.authCodeLength = authCodeLength;
        }
    }


    static String ApplicationName;
    static String emailRegex = "^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";

    public static String getApplicationName() {
        return ApplicationName;
    }


    @Value("${application.name}")
    public void setApplicationName(String applicationName) {
        PropertiesConfig.ApplicationName = applicationName;
    }


    public static String getEmailRegex() {
        return emailRegex;
    }


    //    @Value("${user.Email.RegExp.regexp}")
    public void setEmailRegex(String emailRegex) {
        PropertiesConfig.emailRegex = emailRegex;
    }

}
