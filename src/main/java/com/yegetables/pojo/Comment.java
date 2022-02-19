package com.yegetables.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Comment {
    /**
     * 评论id
     */
    @EqualsAndHashCode.Include
    private Long coid;
    /**
     * 文章id
     */
    private Content content;
    /**
     * 评论时间
     */
    @EqualsAndHashCode.Exclude
    private Timestamp created;
    /**
     * 评论者 名
     */
    private String authorName;
    /**
     * 评论者id
     */
    private Long authorId = 0L;
    /**
     * 文章作者id
     */
    private User owner;
    /**
     * 评论者邮箱
     */
    private String email;
    /**
     * 评论者网址
     */
    private String url;
    /**
     * 评论者ip
     */
    private String ip;
    /**
     * 评论者客户端
     */
    private String agent;
    /**
     * 评论内容
     */
    private String text;
    /**
     * 评论类型
     */
    private String type = "comment";
    /**
     * 评论状态
     */
    private String status = "approved";
    /**
     * 父级评论
     */
    private Comment parent;


}
