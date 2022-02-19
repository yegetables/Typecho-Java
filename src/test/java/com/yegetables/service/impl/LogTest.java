package com.yegetables.service.impl;

import com.yegetables.utils.BaseJunit5Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LogTest extends BaseJunit5Test {
    @Autowired
    private UserServiceImpl userService;


    @Test
    void test() {
        userService.test();
    }
}
