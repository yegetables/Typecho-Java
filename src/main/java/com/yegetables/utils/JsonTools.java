package com.yegetables.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.extern.log4j.Log4j2;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class JsonTools {

    @Autowired
    Gson gson;


    public static boolean isBadJson(String json) {
        return !isGoodJson(json);
    }

    public static boolean isGoodJson(String json) {
        if (StringUtils.isBlank(json))
        {
            return false;
        }
        try
        {
            return JsonParser.parseString(json) != null;
        } catch (JsonSyntaxException e)
        {
            return false;
        }
    }

    public static boolean isJson(String json) {
        return isGoodJson(json);
    }

    public Map JsonToMap(String json) {
        if (isBadJson(json))
        {
            return new HashMap();
        }
        try
        {
            Map map = gson.fromJson(json, Map.class);

            return map;
        } catch (Exception e)
        {
            log.error("json转换为map失败：" + json);
        }
        return new HashMap();
    }
}
