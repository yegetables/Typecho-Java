package com.yegetables.utils;

import com.yegetables.pojo.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
public class StringTools {


    public static String mapGetStringKey(String key, Map map) {
        return toOkString(map.get(key));
    }

    public static String toOkString(String value) {
        if (!StringUtils.isBlank(value) && !StringUtils.equals("null", value)) return value;
        return "";
    }

    public static String toOkString(Object value) {
        if (value != null) return toOkString(String.valueOf(value));
        return "";
    }


    public static boolean isInLength(String value, int min, int max) {
        if (value == null) return false;
        int length = value.length();
        return length > min && length < max;
    }


    public static Long mapGetLongKey(String key, Map map) {
        String str = mapGetStringKey(key, map);
        try
        {
            return Long.parseLong(str);
        } catch (Exception e)
        {
            log.warn("mapGetLongKey error, key: {}, value: {}, error: {}", key, str, e.getMessage());
            return -1L;
        }
    }


    public static class User {
        public static UserGroup getGroupName(String newGroup) {
            newGroup = toOkString(newGroup);
            for (UserGroup group : UserGroup.values())
            {
                if (group.name().equals(newGroup)) return group;
            }
            return null;
        }

        public static boolean isGroupName(String newGroup) {
            return getGroupName(newGroup) != null;
        }

        public static boolean isPassword(String password) {
            password = toOkString(password);
            if (isInLength(password, PropertiesConfig.User.getPasswordMinLength(), PropertiesConfig.User.getPasswordMaxLength()))
                return true;
            return false;
        }

        public static boolean isCode(String code) {
            code = toOkString(code);
            if (code.length() == PropertiesConfig.User.getAuthCodeLength()) return true;
            return false;
        }

        public static boolean isUserName(String username) {
            username = toOkString(username);
            if (isInLength(username, PropertiesConfig.User.getNameMinLength(), PropertiesConfig.User.getNameMaxLength()))
                return true;
            return false;
        }

        public static boolean isEmail(String email) {
            email = toOkString(email);
            if (!isInLength(email, PropertiesConfig.User.getEmailMinLength(), PropertiesConfig.User.getEmailMaxLength()))
                return false;
            /*
             * " \w"：匹配字母、数字、下划线。等价于'[A-Za-z0-9_]'。
             * "|"  : 或的意思，就是二选一
             * "*" : 出现0次或者多次
             * "+" : 出现1次或者多次
             * "{n,m}" : 至少出现n个，最多出现m个
             * "$" : 以前面的字符结束
             */
            String REGEX = PropertiesConfig.getEmailRegex();
            Pattern p = Pattern.compile(REGEX);
            Matcher matcher = p.matcher(email);
            return matcher.matches();
        }

        public static boolean isUrl(String url) {
            url = toOkString(url);
            if (isInLength(url, PropertiesConfig.User.getUrlMinLength(), PropertiesConfig.User.getUrlMaxLength()))
                return true;
            return false;
        }

        public static boolean isScreenName(String screenName) {
            screenName = toOkString(screenName);
            if (isInLength(screenName, PropertiesConfig.User.getScreenNameMinLength(), PropertiesConfig.User.getScreenNameMaxLength()))
                return true;
            return false;
        }

    }

    public static class Meta {
        public static boolean isType(String type) {
            return getType(type) != null;
        }

        public static MetaType getType(String type) {
            type = toOkString(type);
            for (MetaType temp : MetaType.values())
            {
                if (temp.name().equals(type)) return temp;
            }
            return null;
        }

        public static boolean isName(String name) {
            name = toOkString(name);
            if (isInLength(name, PropertiesConfig.Meta.getNameMinLength(), PropertiesConfig.Meta.getNameMaxLength()))
                return true;
            return false;
        }

        public static boolean isSlug(String slug) {
            slug = toOkString(slug);
            if (isInLength(slug, PropertiesConfig.Meta.getSlugMinLength(), PropertiesConfig.Meta.getSlugMaxLength()))
                return true;
            return false;
        }

        public static boolean isDescription(String description) {
            description = toOkString(description);
            if (isInLength(description, PropertiesConfig.Meta.getDescriptionMinLength(), PropertiesConfig.Meta.getDescriptionMaxLength()))
                return true;
            return false;
        }


    }

    public static class Content {
        public static boolean isTitle(String title) {
            title = toOkString(title);
            if (isInLength(title, PropertiesConfig.Content.getTitleMinLength(), PropertiesConfig.Content.getTitleMaxLength()))
                return true;
            return false;
        }

        public static boolean isSlug(String slug) {
            slug = toOkString(slug);
            if (isInLength(slug, PropertiesConfig.Content.getSlugMinLength(), PropertiesConfig.Content.getSlugMaxLength()))
                return true;
            return false;
        }

        public static boolean isPassword(String password) {
            password = toOkString(password);
            if (isInLength(password, PropertiesConfig.Content.getPasswordMinLength(), PropertiesConfig.Content.getPasswordMaxLength()))
                return true;
            return false;
        }

        public static boolean isTemplate(String template) {
            template = toOkString(template);
            if (isInLength(template, PropertiesConfig.Content.getTemplateMinLength(), PropertiesConfig.Content.getTemplateMaxLength()))
                return true;
            return false;
        }

        public static ContentStatus getStatus(String value) {
            value = toOkString(value);
            for (ContentStatus temp : ContentStatus.values())
            {
                if (temp.getValue().equals(value)) return temp;
            }
            return null;
        }

        public static boolean isType(String type) {
            return getType(type) != null;
        }

        public static ContentType getType(String type) {
            type = toOkString(type);
            for (ContentType temp : ContentType.values())
            {
                if (temp.name().equals(type)) return temp;
            }
            return null;
        }

        public static boolean isStatus(String status) {
            return getStatus(status) != null;
        }

    }

    public static class Comment {

        public static boolean isAuthorName(String authorName) {
            authorName = toOkString(authorName);
            if (isInLength(authorName, PropertiesConfig.Comment.getAuthorNameMinLength(), PropertiesConfig.Comment.getAuthorNameMaxLength()))
                return true;
            return false;
        }

        public static boolean isEmail(String email) {
            return User.isEmail(email);
        }

        public static boolean isIp(String ip) {
            ip = toOkString(ip);
            if (isInLength(ip, PropertiesConfig.Comment.getIpMinLength(), PropertiesConfig.Comment.getIpMaxLength()))
                return true;
            return false;
        }

        public static boolean isAgent(String agent) {
            agent = toOkString(agent);
            if (isInLength(agent, PropertiesConfig.Comment.getAgentMinLength(), PropertiesConfig.Comment.getAgentMaxLength()))
                return true;
            return false;
        }

        public static boolean isUrl(String url) {
            url = toOkString(url);
            if (isInLength(url, PropertiesConfig.Comment.getUrlMinLength(), PropertiesConfig.Comment.getUrlMaxLength()))
                return true;
            return false;
        }

        public static CommentType getType(String type) {
            type = toOkString(type);
            for (CommentType temp : CommentType.values())
            {
                if (temp.name().equals(type)) return temp;
            }
            return null;
        }

        public static boolean isType(String type) {
            return getType(type) != null;
        }

        public static CommentStatus getStatus(String status) {
            status = toOkString(status);
            for (CommentStatus temp : CommentStatus.values())
            {
                if (temp.name().equals(status)) return temp;
            }
            return null;
        }

        public static boolean isStatus(String status) {
            return getStatus(status) != null;
        }

    }


}
