package com.yegetables.dao;

import com.yegetables.pojo.Option;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Log4j2
class OptionMapperTest extends BaseJunit5Test {

    @Autowired
    private OptionMapper optionMapper;

    @Test
    void addOption() {
        Option option = createOption();
        var newOption = optionMapper.getOption(option.getName(), option.getUser());
        assertEquals(option, newOption);
    }

    Option createOption() {
        Option option = new Option();
        option.setName(RandomTools.getRandomName());
        option.setValue(RandomTools.getRandomText(5));
        option.setUser(RandomUtils.nextLong() % 2);
        optionMapper.addOption(option);
        return option;
    }

    @Test
    void updateOption() {
        Option option = createOption();
        option.setValue(RandomTools.getRandomText(6));
        optionMapper.updateOption(option);
        Option newOption = optionMapper.getOption(option.getName(), option.getUser());
        assertEquals(option, newOption);
    }


    @Test
    void deleteOptionByName() {
        Option option = createOption();
        optionMapper.deleteOption(option);
        Option newOption = optionMapper.getOption(option.getName(), option.getUser());
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