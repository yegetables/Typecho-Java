package com.yegetables.dao;

import com.yegetables.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface UserMapper {

    ArrayList<User> getAllUsers();

    default User getUserById(Long uid) {
        return getUser(uid, null, null);
    }

    default User getUserByName(String name) {
        return getUser(null, name, null);
    }

    default User getUserByMail(String mail) {
        return getUser(null, null, mail);
    }

    default User getUser(User user) {
        return getUser(user.uid(), user.name(), user.mail());
    }

    default Integer deleteUserByUid(Long uid) {
        return deleteUser(uid, null, null);
    }

    default Integer deleteUserByName(String name) {
        return deleteUser(null, name, null);
    }

    default Integer deleteUserByMail(String mail) {
        return deleteUser(null, null, mail);
    }

    default Integer deleteUser(User user) {
        if (user != null) return deleteUser(user.uid(), user.name(), user.mail());
        else return 0;
    }

    Integer addUser(User user);

    Integer updateUser(User user);

    User getUser(@Param("uid") Long uid, @Param("name") String name, @Param("mail") String mail);

    Integer deleteUser(@Param("uid") Long uid, @Param("name") String name, @Param("mail") String mail);
}