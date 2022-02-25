package com.yegetables.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PropertiesConfig {
    @Service
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
    @Service
    public static class Meta {
        static int nameMaxLength = 150;
        static int nameMinLength = 0;
        static int slugMaxLength = 150;
        static int slugMinLength = -1;
        static int descriptionMaxLength = 150;
        static int descriptionMinLength = -1;


        public static int getNameMaxLength() {
            return nameMaxLength;
        }

        public void setNameMaxLength(int nameMaxLength) {
            Meta.nameMaxLength = nameMaxLength;
        }

        public static int getNameMinLength() {
            return nameMinLength;
        }

        public void setNameMinLength(int nameMinLength) {
            Meta.nameMinLength = nameMinLength;
        }

        public static int getSlugMaxLength() {
            return slugMaxLength;
        }

        public void setSlugMaxLength(int slugMaxLength) {
            Meta.slugMaxLength = slugMaxLength;
        }

        public static int getSlugMinLength() {
            return slugMinLength;
        }

        public void setSlugMinLength(int slugMinLength) {
            Meta.slugMinLength = slugMinLength;
        }

        public static int getDescriptionMaxLength() {
            return descriptionMaxLength;
        }

        public void setDescriptionMaxLength(int descriptionMaxLength) {
            Meta.descriptionMaxLength = descriptionMaxLength;
        }

        public static int getDescriptionMinLength() {
            return descriptionMinLength;
        }

        public void setDescriptionMinLength(int descriptionMinLength) {
            Meta.descriptionMinLength = descriptionMinLength;
        }
    }
    @Service
    public static class Content {
        static int titleMaxLength = 150;
        static int titleMinLength = 0;
        static int slugMaxLength = 150;
        static int slugMinLength = -1;
        static int passwordMaxLength = 32;
        static int passwordMinLength = -1;
        static int templateMaxLength = 32;
        static int templateMinLength = -1;

        public static int getTitleMaxLength() {
            return titleMaxLength;
        }

        public void setTitleMaxLength(int titleMaxLength) {
            Content.titleMaxLength = titleMaxLength;
        }

        public static int getTitleMinLength() {
            return titleMinLength;
        }

        public void setTitleMinLength(int titleMinLength) {
            Content.titleMinLength = titleMinLength;
        }

        public static int getSlugMaxLength() {
            return slugMaxLength;
        }

        public void setSlugMaxLength(int slugMaxLength) {
            Content.slugMaxLength = slugMaxLength;
        }

        public static int getSlugMinLength() {
            return slugMinLength;
        }

        public void setSlugMinLength(int slugMinLength) {
            Content.slugMinLength = slugMinLength;
        }

        public static int getPasswordMaxLength() {
            return passwordMaxLength;
        }

        public void setPasswordMaxLength(int passwordMaxLength) {
            Content.passwordMaxLength = passwordMaxLength;
        }

        public static int getPasswordMinLength() {
            return passwordMinLength;
        }

        public void setPasswordMinLength(int passwordMinLength) {
            Content.passwordMinLength = passwordMinLength;
        }

        public static int getTemplateMaxLength() {
            return templateMaxLength;
        }

        public void setTemplateMaxLength(int templateMaxLength) {
            Content.templateMaxLength = templateMaxLength;
        }

        public static int getTemplateMinLength() {
            return templateMinLength;
        }

        public void setTemplateMinLength(int templateMinLength) {
            Content.templateMinLength = templateMinLength;
        }
    }
    @Service
    public static class Comment {
        static int authorNameMaxLength = 150;
        static int authorNameMinLength = 0;
        static int emailMaxLength = 150;
        static int emailMinLength = 0;
        static int urlMaxLength = 150;
        static int urlMinLength = -1;
        static int ipMaxLength = 64;
        static int ipMinLength = -1;
        static int agentMaxLength = 511;
        static int agentMinLength = -1;

        public static int getAuthorNameMaxLength() {
            return authorNameMaxLength;
        }

        public void setAuthorNameMaxLength(int authorNameMaxLength) {
            Comment.authorNameMaxLength = authorNameMaxLength;
        }

        public static int getAuthorNameMinLength() {
            return authorNameMinLength;
        }

        public void setAuthorNameMinLength(int authorNameMinLength) {
            Comment.authorNameMinLength = authorNameMinLength;
        }

        public static int getEmailMaxLength() {
            return emailMaxLength;
        }

        public void setEmailMaxLength(int emailMaxLength) {
            Comment.emailMaxLength = emailMaxLength;
        }

        public static int getEmailMinLength() {
            return emailMinLength;
        }

        public void setEmailMinLength(int emailMinLength) {
            Comment.emailMinLength = emailMinLength;
        }

        public static int getUrlMaxLength() {
            return urlMaxLength;
        }

        public void setUrlMaxLength(int urlMaxLength) {
            Comment.urlMaxLength = urlMaxLength;
        }

        public static int getUrlMinLength() {
            return urlMinLength;
        }

        public void setUrlMinLength(int urlMinLength) {
            Comment.urlMinLength = urlMinLength;
        }

        public static int getIpMaxLength() {
            return ipMaxLength;
        }

        public void setIpMaxLength(int ipMaxLength) {
            Comment.ipMaxLength = ipMaxLength;
        }

        public static int getIpMinLength() {
            return ipMinLength;
        }

        public void setIpMinLength(int ipMinLength) {
            Comment.ipMinLength = ipMinLength;
        }

        public static int getAgentMaxLength() {
            return agentMaxLength;
        }

        public void setAgentMaxLength(int agentMaxLength) {
            Comment.agentMaxLength = agentMaxLength;
        }

        public static int getAgentMinLength() {
            return agentMinLength;
        }

        public void setAgentMinLength(int agentMinLength) {
            Comment.agentMinLength = agentMinLength;
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
