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

    default void deleteUserByUid(Long uid) {
        deleteUser(uid, null, null);
    }

    default void deleteUserByName(String name) {
        deleteUser(null, name, null);
    }

    default void deleteUserByMail(String mail) {
        deleteUser(null, null, mail);
    }

    default void deleteUser(User user) {
        if (user != null) deleteUser(user.getUid(), user.getName(), user.getMail());
    }

    void addUser(User user);

    void updateUser(User user);

    User getUser(@Param("uid") Long uid, @Param("name") String name, @Param("mail") String mail);

    void deleteUser(@Param("uid") Long uid, @Param("name") String name, @Param("mail") String mail);
}