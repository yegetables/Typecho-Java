package com.yegetables.pojo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(fluent = true)@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Option implements Serializable {
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





    public Long getUser() {
        return user;
    }

    public Option setUser(Long user) {
        this.user = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public Option setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Option setValue(String value) {
        this.value = value;
        return this;
    }

}
