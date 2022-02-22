package com.yegetables.pojo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Accessors(fluent = true)@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Content implements Serializable {


    /**
     * 文章id
     */
    @EqualsAndHashCode.Include
    private Long cid;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章概述
     */
    private String slug;
    /**
     * 创建时间
     */
    @EqualsAndHashCode.Exclude
    private Timestamp created;
    /**
     * 更新时间
     */
    @EqualsAndHashCode.Exclude
    private Timestamp modified;
    /**
     * 文章内容
     */
    private String text;
    /**
     * 优先级
     */
    private Long order = 0L;
    /**
     * 作者id  ---> authorId
     */
    //    @ToString.Exclude
    private User author ;
    /**
     * 内容使用的模板
     */
    private String template;
    /**
     * 文章类型
     */
    private String type = "post";
    /**
     * 文章状态
     */
    private String status = "publish";
    /**
     * 文章密码 可以为空
     */
    private String password;
    /**
     * 评论数
     */
    private Long commentsNum = 0L;
    /**
     * 允许评论
     */
    private Boolean allowComment = false;
    /**
     * 允许ping
     */
    private Boolean allowPing = false;
    /**
     * 允许聚合
     */
    private Boolean allowFeed = false;
    /**
     * 父级评论
     */
    //    @ToString.Exclude
    private Content parent ;


    public Boolean getAllowFeed() {
        return allowFeed;
    }

    public Content setAllowFeed(Boolean allowFeed) {
        this.allowFeed = allowFeed;
        return this;
    }

    public Long getCid() {
        return cid;
    }

    public Content setCid(Long cid) {
        this.cid = cid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Content setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public Content setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Content setCreated(Timestamp created) {
        this.created = created;
        return this;
    }

    public Timestamp getModified() {
        return modified;
    }

    public Content setModified(Timestamp modified) {
        this.modified = modified;
        return this;
    }

    public String getText() {
        return text;
    }

    public Content setText(String text) {
        this.text = text;
        return this;
    }

    public Long getOrder() {
        return order;
    }

    public Content setOrder(Long order) {
        this.order = order;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public Content setAuthor(User author) {
        this.author = author;
        return this;
    }

    public String getTemplate() {
        return template;
    }

    public Content setTemplate(String template) {
        this.template = template;
        return this;
    }

    public String getType() {
        return type;
    }

    public Content setType(String type) {
        this.type = type;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Content setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Content setPassword(String password) {
        this.password = password;
        return this;
    }

    public Long getCommentsNum() {
        return commentsNum;
    }

    public Content setCommentsNum(Long commentsNum) {
        this.commentsNum = commentsNum;
        return this;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public Content setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
        return this;
    }

    public Boolean getAllowPing() {
        return allowPing;
    }

    public Content setAllowPing(Boolean allowPing) {
        this.allowPing = allowPing;
        return this;
    }

    public Content getParent() {
        return parent;
    }

    public Content setParent(Content parent) {
        this.parent = parent;
        return this;
    }
}
