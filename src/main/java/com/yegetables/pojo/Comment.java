package com.yegetables.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Comment implements Serializable {

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
    private CommentType type = CommentType.comment;
    /**
     * 评论状态
     */
    private CommentStatus status = CommentStatus.approved;
    /**
     * 父级评论
     */
    private Comment parent;


    public Long getCoid() {
        return coid;
    }

    public Comment setCoid(Long coid) {
        this.coid = coid;
        return this;
    }

    public Content getContent() {
        return content;
    }

    public Comment setContent(Content content) {
        this.content = content;
        return this;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Comment setCreated(Timestamp created) {
        this.created = created;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Comment setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Comment setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Comment setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Comment setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Comment setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Comment setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getAgent() {
        return agent;
    }

    public CommentType getType() {
        return type;
    }

    public void setType(CommentType type) {
        this.type = type;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public Comment setAgent(String agent) {
        this.agent = agent;
        return this;
    }

    public String getText() {
        return text;
    }

    public Comment setText(String text) {
        this.text = text;
        return this;
    }


    public Comment getParent() {
        return parent;
    }

    public Comment setParent(Comment parent) {
        this.parent = parent;
        return this;
    }

}
