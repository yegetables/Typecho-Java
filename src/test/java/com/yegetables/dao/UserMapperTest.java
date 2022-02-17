package com.yegetables.dao;

import com.yegetables.pojo.User;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import com.yegetables.utils.TimeTools;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Log4j2
class UserMapperTest extends BaseJunit5Test {

    @Autowired
    private UserMapper userMapper;

    @Test
    void getAllUsers() {
        log.info("getAllUsers");
        var list = userMapper.getAllUsers();
        list.forEach(log::info);
    }

    @Test
    void getUser() {
        Long id = 1L;
        String mail = "yegetables@gmail.com";
        String name = "admin";
        String errorName = "123";
        log.info("getUserById=" + id);
        log.info(userMapper.getUserById(id));
        log.info("getUserByMail=" + mail);
        log.info(userMapper.getUserByMail(mail));
        log.info("getUserByName=" + name);
        log.info(userMapper.getUserByName(name));
        //不检测空指针不会异常 ,打印内容 null
        log.info("getUserByName=" + errorName + " 不存在用户");
        log.info(userMapper.getUserByName(errorName));
    }

    //            @RepeatedTest(value = 8, name = "测试插入用户")
    User createUser() {
        User user = new User();
        user.setName(RandomTools.getRandomName());
        user.setPassword(RandomTools.getRandomPassword());
        user.setMail(RandomTools.getRandomEmail());
        user.setUrl(RandomTools.getRandomUrl());
        user.setCreated(TimeTools.NowTime());
        userMapper.addUser(user);
        return user;
    }

    @Test
    void addUser() {
        var user = createUser();
        var newUser = userMapper.getUserByName(user.getName());
        assertEquals(user, newUser);
    }

    @Test
    void deleteUser() {
        User newUser = null;
        User getted = null;
        newUser = userMapper.getUserById(createUser().getUid());
        userMapper.deleteUserByUid(newUser.getUid());
        getted = userMapper.getUserById(newUser.getUid());
        assertNull(getted, "delete UserById ");

        //
        newUser = userMapper.getUserByName(createUser().getName());
        userMapper.deleteUserByName(newUser.getName());
        getted = userMapper.getUserByName(newUser.getName());
        assertNull(getted, "delete UserByName ");

        //
        newUser = userMapper.getUserByMail(createUser().getMail());
        userMapper.deleteUserByMail(newUser.getMail());
        getted = userMapper.getUserByMail(newUser.getMail());
        assertNull(getted, "delete UserByMail ");


    }

    @Test
    void updateUserById() {
        var old = createUser();
        old.setName(RandomTools.getRandomName());
        old.setUrl(RandomTools.getRandomUrl());
        userMapper.updateUser(old);
        var lastUpdate = userMapper.getUserById(old.getUid());
        //                assertEquals(old, lastUpdate);
        userMapper.deleteUser(lastUpdate);
        assertNull(userMapper.getUserById(lastUpdate.getUid()));
    }


}