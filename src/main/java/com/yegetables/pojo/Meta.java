package com.yegetables.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Meta implements Serializable {
    /**
     * 项目主键
     */
    @EqualsAndHashCode.Include
    private Long mid;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目标题
     */
    private String slug;
    /**
     * 项目类型
     */
    private MetaType type;
    /**
     * 项目描述
     */
    private String description;
    /**
     * 项目所属内容个数
     */
    private Long count = 0L;
    /**
     * 项目排序
     */
    private Long order = 0L;
    /**
     * 父项目
     */
    private Meta parent;


    public Long getMid() {
        return mid;
    }

    public Meta setMid(Long mid) {
        this.mid = mid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Meta setName(String name) {
        this.name = name;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public Meta setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public MetaType getType() {
        return type;
    }

    public Meta setType(MetaType type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Meta setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public Meta setCount(Long count) {
        this.count = count;
        return this;
    }

    public Long getOrder() {
        return order;
    }

    public Meta setOrder(Long order) {
        this.order = order;
        return this;
    }

    public Meta getParent() {
        return parent;
    }

    public Meta setParent(Meta parent) {
        this.parent = parent;
        return this;
    }


}
