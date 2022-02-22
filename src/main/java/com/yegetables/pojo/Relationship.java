package com.yegetables.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
//@Accessors(chain = true)
@Accessors(fluent = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Relationship implements Serializable {
    @EqualsAndHashCode.Include
    private Content content;
    @EqualsAndHashCode.Include
    private Meta meta;

    public Content getContent() {
        return content;
    }

    public Relationship setContent(Content content) {
        this.content = content;
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public Relationship setMeta(Meta meta) {
        this.meta = meta;
        return this;
    }
}
