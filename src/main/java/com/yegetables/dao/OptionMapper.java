package com.yegetables.dao;

import com.yegetables.pojo.Option;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OptionMapper {
    ArrayList<Option> getAllOptions();

    Integer addOption(Option option);

    Integer updateOption(Option option);


    Integer deleteOption(@Param("name") String name, @Param("user") Long user);

    Option getOption(@Param("name") String name, @Param("user") Long user);


    default Integer deleteOptionByName(String name) {
        return deleteOption(name, 0L);
    }

    default Integer deleteOption(Option option) {
        return deleteOption(option.name(), option.user());
    }

    default Option getOptionByName(String name) {
        return getOption(name, 0L);
    }

}
