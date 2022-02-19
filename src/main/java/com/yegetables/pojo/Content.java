package com.yegetables.pojo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(fluent = true)@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Content {
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
}
