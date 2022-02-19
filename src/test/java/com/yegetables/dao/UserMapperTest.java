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
        user.name(RandomTools.getRandomName());
        user.password(RandomTools.getRandomPassword());
        user.mail(RandomTools.getRandomEmail());
        user.url(RandomTools.getRandomUrl());
        user.created(TimeTools.NowTime());
        assertEquals(1, userMapper.addUser(user));
        return user;
    }

    @Test
    void addUser() {
        var user = createUser();
        var newUser = userMapper.getUserByName(user.name());
        //        newUser.toString();
       assertEquals(user, newUser);
    }

    @Test
    void deleteUser() {
        User newUser = null;
        User getted = null;
        newUser = userMapper.getUserById(createUser().uid());
        assertEquals(1, userMapper.deleteUserByUid(newUser.uid()));
        getted = userMapper.getUserById(newUser.uid());
        assertNull(getted, "delete UserById ");

        //
        newUser = userMapper.getUserByName(createUser().name());
        assertEquals(1, userMapper.deleteUserByName(newUser.name()));
        getted = userMapper.getUserByName(newUser.name());
        assertNull(getted, "delete UserByName ");

        //
        newUser = userMapper.getUserByMail(createUser().mail());
        assertEquals(1, userMapper.deleteUserByMail(newUser.mail()));
        getted = userMapper.getUserByMail(newUser.mail());
        assertNull(getted, "delete UserByMail ");


    }

    @Test
    void updateUserById() {
        var old = createUser();
        old.name(RandomTools.getRandomName());
        old.url(RandomTools.getRandomUrl());
        assertEquals(1, userMapper.updateUser(old));
        var lastUpdate = userMapper.getUserById(old.uid());
        //        lastUpdate.toString();
       assertEquals(old, lastUpdate);
        assertEquals(1, userMapper.deleteUser(lastUpdate));
        assertNull(userMapper.getUserById(lastUpdate.uid()));
    }


}