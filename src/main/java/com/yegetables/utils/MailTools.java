package com.yegetables.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailTools {

    private final JavaMailSender mailSender;
    private final SimpleMailMessage mailMessage;

    public MailTools(@Autowired JavaMailSender mailSender, @Autowired SimpleMailMessage mailMessage) {
        this.mailSender = mailSender;
        this.mailMessage = mailMessage;
    }

    public void sendSimpleMail(String to, String subject, String content) {
        mailMessage.setSubject(subject);
        sendSimpleMail(to, content);
    }

    public void sendSimpleMail(String to, String content) {
        mailMessage.setTo(to);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }

}
