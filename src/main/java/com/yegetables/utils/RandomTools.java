package com.yegetables.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class RandomTools {

    public static String getRandomEmail() {
        return RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(3, 10)) + "@" + RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(3, 10)) + ".com";
    }

    public static String getRandomName() {
        return RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(3, 10));
    }

    public static String getRandomPassword() {
        return new PHPass().HashPassword(RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(3, 10)));
    }

    public static String getRandomUrl() {
        return "https://" + RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(3, 10)) + ".com";
    }

    public static String getRandomText(int i) {
        return RandomStringUtils.randomAlphanumeric(i);
    }

    public static String getRandomAuthorCode(int i) {
        //生成验证码,排除易错字符,忽略大小写
        return RandomStringUtils.random(i, "abcdefghkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789");
    }

    public static <T> T getRandom(List<T> list) {
        int size = list.size();
        return list.get(RandomUtils.nextInt(0, size));
    }
}

