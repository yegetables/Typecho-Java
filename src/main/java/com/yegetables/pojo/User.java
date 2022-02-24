package com.yegetables.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class User implements Serializable {
    /**
     * 用户id  	user表主键 主键,非负,自增
     */
    @EqualsAndHashCode.Include
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
    private UserGroup group = UserGroup.visitor;
    /**
     * 用户登录验证码,相当于token
     */
    private String authCode;

    public Long getUid() {
        return uid;
    }

    public User setUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public User setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public User setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getScreenName() {
        return screenName;
    }

    public User setScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    public Timestamp getCreated() {
        return created;
    }

    public User setCreated(Timestamp created) {
        this.created = created;
        return this;
    }

    public Timestamp getActivated() {
        return activated;
    }

    public User setActivated(Timestamp activated) {
        this.activated = activated;
        return this;
    }

    public Timestamp getLogged() {
        return logged;
    }

    public User setLogged(Timestamp logged) {
        this.logged = logged;
        return this;
    }

    public UserGroup getGroup() {
        return group;
    }

    public User setGroup(UserGroup group) {
        this.group = group;
        return this;
    }

    public String getAuthCode() {
        return authCode;
    }

    public User setAuthCode(String authCode) {
        this.authCode = authCode;
        return this;
    }
}
