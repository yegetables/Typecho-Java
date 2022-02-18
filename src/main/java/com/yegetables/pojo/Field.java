package com.yegetables.pojo;

import lombok.Data;

@Data
public class Field {

    private Content content;

    private String name;

    private String type = "str";

    private String str_value;

    private Integer int_value = 0;

    private Float float_value = 0f;

}
