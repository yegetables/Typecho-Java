package com.yegetables.pojo;

import lombok.Getter;

import java.util.Locale;

@Getter
public enum ContentStatus {
    publish, hidden, PRIVATE, waiting;
    //page----publish,hidden
    // 如果草稿page,则新建 page_draft hidden,原来的不变

    //post -->
    private String value;

    ContentStatus() {
        this.value = name().toLowerCase(Locale.ENGLISH);
    }

    public String getValue() {
        return value;
    }
}
