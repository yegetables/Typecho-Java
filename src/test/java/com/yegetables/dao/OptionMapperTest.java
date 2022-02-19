package com.yegetables.dao;

import com.yegetables.pojo.Option;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Log4j2
class OptionMapperTest extends BaseJunit5Test {

    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    void addOption() {
        Option option = createOption();
        var newOption = optionMapper.getOption(option.name(), option.user());
        //        newOption;
        assertEquals(option, newOption);
    }

    Option createOption() {
        Option option = new Option();
        option.name(RandomTools.getRandomName());
        option.value(RandomTools.getRandomText(5));
        option.user(RandomTools.getRandom(userMapper.getAllUsers()).uid());
        assertEquals(1, optionMapper.addOption(option));
        return option;
    }

    @Test
    void updateOption() {
        Option option = createOption();
        option.value(RandomTools.getRandomText(6));
        assertEquals(1, optionMapper.updateOption(option));
        Option newOption = optionMapper.getOption(option.name(), option.user());
        //        newOption.toString();
        assertEquals(newOption, option);
    }


    @Test
    void deleteOptionByName() {
        Option option = createOption();
        assertEquals(1, optionMapper.deleteOption(option));
        Option newOption = optionMapper.getOption(option.name(), option.user());
        assertNull(newOption);
    }

    @Test
    void getOptionByName() {
        log.info(optionMapper.getOptionByName("charset"));
    }

    @Test
    void getAllOptions() {
        var list = optionMapper.getAllOptions();
        list.forEach(log::info);
    }
}