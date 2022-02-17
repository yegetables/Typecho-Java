package com.yegetables.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
public class Content {
    /**
     * 文章id
     */
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
    private User author = null;
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
    private Content parent = null;
}
