package com.yegetables.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
//@Accessors(chain = true)
@Accessors(fluent = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Relationship {
    @EqualsAndHashCode.Include
    private Content content;
    @EqualsAndHashCode.Include
    private Meta meta;
}
