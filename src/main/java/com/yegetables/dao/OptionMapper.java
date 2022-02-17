package com.yegetables.dao;

import com.yegetables.pojo.Option;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OptionMapper {
    ArrayList<Option> getAllOptions();

    void addOption(Option option);

    void updateOption(Option option);

    void deleteOption(@Param("name") String name, @Param("user") Long user);

    Option getOption(@Param("name") String name, @Param("user") Long user);


    default void deleteOptionByName(String name) {
        deleteOption(name, 0L);
    }

    default void deleteOption(Option option) {
        deleteOption(option.getName(), option.getUser());
    }

    default Option getOptionByName(String name) {
        return getOption(name, 0L);
    }

}
