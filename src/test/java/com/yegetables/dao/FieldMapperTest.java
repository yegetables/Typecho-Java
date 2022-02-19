package com.yegetables.dao;

import com.yegetables.pojo.Field;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FieldMapperTest extends BaseJunit5Test {

    @Autowired
    private FieldMapper fieldMapper;
    @Autowired
    private ContentMapper contentMapper;

    Field createField() {
        Field field = new Field();
        field.content(RandomTools.getRandom(contentMapper.getAllContents()));
        field.name(RandomTools.getRandomName());
        field.str_value(RandomTools.getRandomText(4));
        assertEquals(1, fieldMapper.addField(field));
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
        var newField = fieldMapper.getField(field.content(), field.name());
        System.out.println(newField);
    }

    @Test
        //    @RepeatedTest(value = 8, name = "测试插入")
    void addField() {
        var old = createField();
        var newField = fieldMapper.getField(old.content(), old.name());
        //        newField.toString();
        assertEquals(newField, old);
    }

    @Test
    void deleteField() {
        var old = createField();
        assertEquals(1, fieldMapper.deleteField(old));
        var now = fieldMapper.getField(old.content(), old.name());
        assertNull(now);
    }

    @Test
    void updateField() {
        var old = createField();
        old.str_value(RandomTools.getRandomText(8));
        assertEquals(1, fieldMapper.updateField(old));
        var now = fieldMapper.getField(old.content(), old.name());
        //        now.toString();
        assertEquals(now, old);
    }

}
