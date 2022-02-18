package com.yegetables.dao;

import com.yegetables.pojo.Content;
import com.yegetables.pojo.Field;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface FieldMapper {
    ArrayList<Field> getAllFields();

    Field getField(@Param("cid") Long cid, @Param("name") String name);

    void addField(Field field);

    void deleteField(@Param("cid") Long cid, @Param("name") String name);

    void updateField(Field field);

    default void deleteField(Field field) {
        deleteField(field.getContent(), field.getName());
    }


    default void deleteField(Content content, String name) {
        deleteField(content.getCid(), name);
    }


    default Field getField(Content content, String name) {
        if (content == null) return null;
        return getField(content.getCid(), name);
    }
}


