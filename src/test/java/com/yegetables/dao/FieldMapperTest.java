package com.yegetables.dao;

import com.yegetables.pojo.Field;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldMapperTest extends BaseJunit5Test {

    @Autowired
    private FieldMapper fieldMapper;
    @Autowired
    private ContentMapper contentMapper;

    Field createField() {
        Field field = new Field();
        field.setContent(RandomTools.getRandom(contentMapper.getAllContents()));
        field.setName(RandomTools.getRandomName());
        field.setStr_value(RandomTools.getRandomText(4));
        fieldMapper.addField(field);
        return field;
    }

    @Test
    void getAllFields() {
        var list = fieldMapper.getAllFields();
        list.forEach(System.out::println);
    }

    @Test
    void getField() {
        var field = RandomTools.getRandom(fieldMapper.getAllFields());
        var newField = fieldMapper.getField(field.getContent(), field.getName());
        System.out.println(newField);
    }

    @Test
        //    @RepeatedTest(value = 8, name = "测试插入")
    void addField() {
        var old = createField();
        var newField = fieldMapper.getField(old.getContent(), old.getName());
        assertEquals(old, newField);
    }

    @Test
    void deleteField() {
        var old = createField();
        fieldMapper.deleteField(old);
        var now = fieldMapper.getField(old.getContent(), old.getName());
        assertEquals(null, now);
    }

    @Test
    void updateField() {
        var old = createField();
        old.setStr_value(RandomTools.getRandomText(8));
        fieldMapper.updateField(old);
        var now = fieldMapper.getField(old.getContent(), old.getName());
        assertEquals(old, now);
    }

}
