package com.yegetables.pojo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Field {

    @EqualsAndHashCode.Include
    private Content content;
    @EqualsAndHashCode.Include

    private String name;

    private String type = "str";

    private String str_value;

    private Integer int_value = 0;

    private Float float_value = 0f;

}
