package com.yegetables.service.impl;

import com.yegetables.pojo.User;
import com.yegetables.service.ApiResult;
import com.yegetables.service.ApiResultStatus;
import com.yegetables.service.UserService;
import com.yegetables.utils.MailTools;
import com.yegetables.utils.PropertiesConfig;
import com.yegetables.utils.RandomTools;
import com.yegetables.utils.TimeTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private final Map<String, String> mailAuthCodeMap = new HashMap<>();
    @Autowired
    MailTools mailTools;


    @Override
    public ApiResult<String> sendEmailAuthorCode(String email) {
        if (getUser(new User().mail(email)) != null)
            return new ApiResult<String>().code(ApiResultStatus.Error).message("该邮箱已经注册");
        String text = RandomTools.getRandomAuthorCode(PropertiesConfig.getAuthCodeLength());
        try
        {
            mailTools.sendSimpleMail(email, "验证码", "忽略大小写\n" + text + "\n5分钟内有效");
            //TODO: 存储验证码Redis
            //~~~替代,使用数据库users表的authCode字段~~~
            //使用map存储邮箱和验证码
            mailAuthCodeMap.put(email, text);
            return new ApiResult<String>().message("验证码已发送");
        } catch (Exception e)
        {
            String errorMessage = "邮件发送失败";
            log.error(errorMessage, e);
            return new ApiResult<String>().code(ApiResultStatus.Error).message(errorMessage);
        }

    }

    @Override
    public ApiResult<User> register(String username, String password, String mail, String code) {
        if (!mailAuthCodeMap.containsKey(mail))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("请先发送验证码");
        if (!StringUtils.equalsIgnoreCase(mailAuthCodeMap.get(mail), code))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("验证码错误,请重试");
        if (getUser(new User().name(username)) != null)
            return new ApiResult<User>().code(ApiResultStatus.Error).message("该用户名已经注册");
        if (getUser(new User().mail(mail)) != null)
            return new ApiResult<User>().code(ApiResultStatus.Error).message("该邮箱已经注册");
        mailAuthCodeMap.remove(mail);
        User newUser = new User().mail(mail).name(username).password(password).created(TimeTools.NowTime()).activated(TimeTools.NowTime());
        try
        {
            Integer sum = userMapper.addUser(newUser);
            if (sum != 1) throw new Exception("添加用户失败");
            else newUser = userMapper.getUserById(newUser.uid()); //获得数据库默认值  非必要
            return new ApiResult<User>().code(ApiResultStatus.Success).message("注册成功").data(newUser);
        } catch (Exception e)
        {
            //TODO: 删除验证码Redis||数据库插入失败因为邮箱/用户名重复等原因
            String errorMessage = "注册失败";
            log.warn(errorMessage, e);
            return new ApiResult<User>().code(ApiResultStatus.Error).message("注册失败,发生未知错误").data(newUser);
        }
    }

    @Override
    public ApiResult<User> login(String username, String password) {
        User user = userMapper.getUserByName(username);
        if (user == null) return new ApiResult<User>().code(ApiResultStatus.Error).message("用户名不存在");
        if (!StringUtils.equals(user.password(), password))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("密码错误");
        try
        {
            userMapper.updateUser(user.logged(TimeTools.NowTime()).activated(TimeTools.NowTime()));
            return new ApiResult<User>().code(ApiResultStatus.Success).message("登录成功").data(user);
        } catch (Exception e)
        {
            String errorMessage = "登录失败";
            log.error(errorMessage, e);
            return new ApiResult<User>().code(ApiResultStatus.Error).message(errorMessage).data(user);
        }
    }

    @Override
    public ApiResult<User> changeUserInfo(User user) {
        //查重
        try
        {

            User oldUser = userMapper.getUserById(user.uid());
            if (oldUser == null) return new ApiResult<User>().code(ApiResultStatus.Error).message("用户不存在");
            if (user.name() != null && userMapper.getUserByName(user.name()) != null)
                return new ApiResult<User>().code(ApiResultStatus.Error).message("用户名已存在");
            if (user.mail() != null && userMapper.getUserByMail(user.mail()) != null)
                return new ApiResult<User>().code(ApiResultStatus.Error).message("邮箱已存在");
        } catch (Exception e)
        {
            String errorMessage = "查找用户失败";
            log.error(errorMessage, e);
            return new ApiResult<User>().code(ApiResultStatus.Error).message(errorMessage);
        }
        try
        {
            Integer sum = userMapper.updateUser(user);
            if (sum != 1) throw new Exception("更新用户信息失败");
            User newUser = userMapper.getUserById(user.uid());
            return new ApiResult<User>().code(ApiResultStatus.Success).message("更新成功").data(newUser);
        } catch (Exception e)
        {
            String errorMessage = "更新用户信息失败";
            log.error(errorMessage, e);
            return new ApiResult<User>().code(ApiResultStatus.Error).message(errorMessage);
        }
    }

    @Override
    public User getUser(User user) {
        return userMapper.getUser(user);
    }


    void test() {
        log.warn("hello");
    }

}
