package com.yegetables.pojo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(fluent = true)@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Field implements Serializable {

    @EqualsAndHashCode.Include
    private Content content;


    @EqualsAndHashCode.Include

    private String name;

    private String type = "str";

    private String str_value;

    private Integer int_value = 0;

    private Float float_value = 0f;




    public String getName() {
        return name;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Field setType(String type) {
        this.type = type;
        return this;
    }

    public String getStr_value() {
        return str_value;
    }

    public Field setStr_value(String str_value) {
        this.str_value = str_value;
        return this;
    }

    public Integer getInt_value() {
        return int_value;
    }

    public Field setInt_value(Integer int_value) {
        this.int_value = int_value;
        return this;
    }

    public Float getFloat_value() {
        return float_value;
    }

    public Field setFloat_value(Float float_value) {
        this.float_value = float_value;
        return this;
    }

}
