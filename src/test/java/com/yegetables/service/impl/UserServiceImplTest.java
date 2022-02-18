package com.yegetables.service.impl;

import com.yegetables.utils.BaseJunit5Test;
import org.junit.jupiter.api.Test;

class UserServiceImplTest extends BaseJunit5Test {


    @Test
    void testLog() {
        var test = new UserServiceImpl();
        test.testLog();
    }
}