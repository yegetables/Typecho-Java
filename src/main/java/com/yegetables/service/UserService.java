package com.yegetables.service;

import com.yegetables.pojo.User;

public interface UserService {
    //生成+发送+保存到mailMap
    ApiResult<String> sendEmailAuthorCode(String email);

    ApiResult<User> register(String username, String password, String mail, String code);

    ApiResult<User> login(String username, String password);
    //    void changePassword(String oldPassword, String newPassword);
    //
    //    void changeUsername(String oldUsername, String newUsername);
    //
    //    void changeEmail(String oldEmail, String newEmail);
    //
    //    void changePhone(String oldPhone, String newPhone);
    //
    //    void changeAddress(String oldAddress, String newAddress);
    //
    //    void changeAvatar(String oldAvatar, String newAvatar);
    //
    //    void changeGender(String oldGender, String newGender);
    //
    //    void changeBirthday(String oldBirthday, String newBirthday);
    //
    //    void changeRole(String oldRole, String newRole);
    //
    //    void changeStatus(String oldStatus, String newStatus);


}
