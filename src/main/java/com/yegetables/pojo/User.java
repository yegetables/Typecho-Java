package com.yegetables.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
public class User {
    /**
     * 用户id  	user表主键 主键,非负,自增
     */
    private Long uid;
    /**
     * 用户名 唯一
     */
    private String name;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户邮箱 唯一
     */
    private String mail;
    /**
     * 用户主页
     */
    private String url;
    /**
     * 显示用户名
     */
    private String screenName;
    /**
     * 用户注册时间
     */
    @EqualsAndHashCode.Exclude
    private Timestamp created;
    /**
     * 用户最近活动时间
     */
    @EqualsAndHashCode.Exclude
    private Timestamp activated;
    /**
     * 用户上次登录最后活跃时间
     */
    @EqualsAndHashCode.Exclude
    private Timestamp logged;
    /**
     * 用户组
     */
    private String group = "visitor";
    /**
     * 用户登录验证码
     */
    private String authCode;


}
