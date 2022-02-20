package com.yegetables.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanApplicationContextTools {
    public static ApplicationContext getApplicationContext() {
        return new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
    }
}
