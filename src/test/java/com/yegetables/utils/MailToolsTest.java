package com.yegetables.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MailToolsTest extends BaseJunit5Test {

    @Autowired
    MailTools mailTools;
    String to = "test@163.com";
    String subject = "testMail";
    String text = RandomTools.getRandomText(8);

    @Test
    void sendSimpleMail1() {
        mailTools.sendSimpleMail(to, text);
    }

    @Test
    void sendSimpleMail2() {
        mailTools.sendSimpleMail(to, subject, text);
    }

}