package com.yegetables.pojo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Option {
    /**
     * 选项所属用户，默认0全局变量
     */
    @EqualsAndHashCode.Include
    private Long user = 0L;
    /**
     * 选项名 非空
     */

    @EqualsAndHashCode.Include
    private String name;
    /**
     * 选项值
     */
    private String value;
}
