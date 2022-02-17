package com.yegetables.pojo;

import lombok.Data;

@Data
public class Option {
    /**
     * 选项所属用户，默认0全局变量
     */
    private Long user = 0L;
    /**
     * 选项名 非空
     */

    private String name;
    /**
     * 选项值
     */
    private String value;
}
