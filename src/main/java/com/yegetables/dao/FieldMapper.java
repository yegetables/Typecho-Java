package com.yegetables.dao;

import com.yegetables.pojo.Content;
import com.yegetables.pojo.Field;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface FieldMapper {
    ArrayList<Field> getAllFields();

    Field getField(@Param("cid") Long cid, @Param("name") String name);

    Integer addField(Field field);


    Integer deleteField(@Param("cid") Long cid, @Param("name") String name);

    Integer updateField(Field field);

    default Integer deleteField(Field field) {
        return deleteField(field.content(), field.name());
    }


    default Integer deleteField(Content content, String name) {
        return deleteField(content.cid(), name);
    }


    default Field getField(Content content, String name) {
        if (content == null) return null;
        return getField(content.cid(), name);
    }
}


