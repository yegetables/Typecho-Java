package com.yegetables.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:config.properties")
public class MailConfig {
    //    <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
    //        <property name="host" value="${mail.host}"/>
    //        <property name="username" value="${mail.username}"/>
    //        <property name="password" value="${mail.password}"/>
    //        <property name="defaultEncoding" value="${mail.defaultEncoding}"/>
    //        <property name="javaMailProperties">
    //            <props>
    //                <prop key="mail.smtp.auth">true</prop>
    //                <prop key="mail.smtp.ssl.enable">true</prop>
    //                <prop key="mail.smtp.port">${mail.port}</prop>
    //            </props>
    //        </property>
    //    </bean>
    //    <bean id="mailMessage" class="org.springframework.mail.SimpleMailMessage">
    //        <property name="from" value="${mail.username}"/>
    //        <property name="subject" value="${mail.subject}"/>
    //    </bean>
    @Value("${mail.host}")
    private String host;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.defaultEncoding}")
    private String defaultEncoding;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.port}")
    private String port;
    @Value("${mail.subject}")
    private String subject;

    @Bean(name = "mailSender")
    public JavaMailSenderImpl getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding(defaultEncoding);
        mailSender.setJavaMailProperties(getMailProperties());
        return mailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.port", port);
        return properties;
    }

    @Bean(name = "mailMessage")
    public SimpleMailMessage getMailMessage() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setSubject(subject);
        return mailMessage;
    }
}
