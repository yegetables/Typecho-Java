package com.yegetables.utils;

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
        //        if (map.containsKey(key))
        //        {
        //            String value = String.valueOf(map.get(key));
        //            return toOkString(value);
        //            //            if (!StringUtils.isBlank(value) && !StringUtils.equals("null", value)) return value;
        //        }
        //        return "";
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
            return 0L;
        }
    }


    public static class User {
        public static boolean isGroupName(String newGroup) {
            newGroup = toOkString(newGroup);
            if ("administrator".equals(newGroup) || "visitor".equals(newGroup)) return true;
            return false;
        }

        public static boolean isPassword(String password) {
            password = toOkString(password);
            if (isInLength(password, PropertiesConfig.getPasswordMinLength(), PropertiesConfig.getPasswordMaxLength()))
                return true;
            return false;
        }

        public static boolean isCode(String code) {
            code = toOkString(code);
            if (code.length() == PropertiesConfig.getAuthCodeLength()) return true;
            return false;
        }

        public static boolean isUserName(String username) {
            username = toOkString(username);
            if (isInLength(username, PropertiesConfig.getNameMinLength(), PropertiesConfig.getNameMaxLength()))
                return true;
            return false;
        }

        static String emailRegex = PropertiesConfig.getEmailRegex();

        public static boolean isEmail(String email) {
            email = toOkString(email);
            if (!isInLength(email, PropertiesConfig.getEmailMinLength(), PropertiesConfig.getEmailMaxLength()))
                return false;
            /*
             * " \w"：匹配字母、数字、下划线。等价于'[A-Za-z0-9_]'。
             * "|"  : 或的意思，就是二选一
             * "*" : 出现0次或者多次
             * "+" : 出现1次或者多次
             * "{n,m}" : 至少出现n个，最多出现m个
             * "$" : 以前面的字符结束
             */
            String REGEX = emailRegex;
            Pattern p = Pattern.compile(REGEX);
            Matcher matcher = p.matcher(email);
            return matcher.matches();
        }

    }


}
