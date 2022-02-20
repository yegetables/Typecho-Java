package com.yegetables.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Service
//@Log4j2
public class StringTools {
    public static boolean isEmail(String email) {
        /*
         * " \w"：匹配字母、数字、下划线。等价于'[A-Za-z0-9_]'。
         * "|"  : 或的意思，就是二选一
         * "*" : 出现0次或者多次
         * "+" : 出现1次或者多次
         * "{n,m}" : 至少出现n个，最多出现m个
         * "$" : 以前面的字符结束
         */
        String REGEX = "^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";
        Pattern p = Pattern.compile(REGEX);
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }

    public static String mapGetStringKey(String key, Map map) {
        if (map.containsKey(key))
        {
            String value = String.valueOf(map.get(key));
            if (!StringUtils.isBlank(value) && !StringUtils.equals("null", value)) return value;
        }
        return "";
    }


}
