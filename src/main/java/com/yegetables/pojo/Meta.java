package com.yegetables.pojo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Meta {
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
    private String type;
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
}
