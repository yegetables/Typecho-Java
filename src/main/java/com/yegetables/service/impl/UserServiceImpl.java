package com.yegetables.service.impl;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.User;
import com.yegetables.service.UserService;
import com.yegetables.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    // private final Map<String, String> mailAuthCodeMap = new HashMap<>();
    @Autowired
    MailTools mailTools;
    @Autowired
    PHPass phpass;

    @Override
    public ApiResult<String> sendEmailAuthorCode(String email) {
        if (getUser(new User().mail(email)) != null)
            return new ApiResult<String>().code(ApiResultStatus.Error).message("该邮箱已经注册");
        String text = RandomTools.getRandomAuthorCode(PropertiesConfig.getAuthCodeLength());
        String key = getEmailKey(email);
        //key --- rightcode  存入redis ,5分钟有效
        try
        {
            mailTools.sendSimpleMail(email, "验证码", "忽略大小写\n" + text + "\n5分钟内有效");
            redisTemplate.opsForValue().set(key, text, 5, TimeUnit.MINUTES);
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
        String key = getEmailKey(mail);
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) return new ApiResult<User>().code(ApiResultStatus.Error).message("请先发送验证码");
        if (!StringUtils.equalsIgnoreCase(String.valueOf(value), code))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("验证码错误,请重试");
        if (getUser(new User().name(username)) != null)
            return new ApiResult<User>().code(ApiResultStatus.Error).message("该用户名已经注册");
        if (getUser(new User().mail(mail)) != null)
            return new ApiResult<User>().code(ApiResultStatus.Error).message("该邮箱已经注册");

        User newUser = new User().mail(mail).name(username).password(phpass.HashPassword(password)).created(TimeTools.NowTime()).activated(TimeTools.NowTime()).logged(TimeTools.NowTime());
        try
        {
            Integer sum = userMapper.addUser(newUser);
            if (sum != 1) throw new Exception("添加用户失败");
            else newUser = userMapper.getUserById(newUser.uid()); //获得数据库默认值  非必要
            // 注册成功后自动登录
            String token = getToken(newUser);
            try
            {
                redisTemplate.opsForValue().set(token, newUser, 6, TimeUnit.HOURS);
            } catch (Exception e)
            {
                log.error("redis set failed" + token);
            }
            newUser.authCode(token);//只返回前端,不写入数据库表,存在redis中仅供本系统使用


            try
            {
                redisTemplate.delete(key);
            } catch (Exception e)
            {
                log.error("redis delete failed" + key);
            }


            return new ApiResult<User>().code(ApiResultStatus.Success).message("注册成功").data(removeSecrets(newUser));
        } catch (Exception e)
        {

            String errorMessage = "注册失败";
            log.warn(errorMessage, e);
            return new ApiResult<User>().code(ApiResultStatus.Error).message("注册失败,发生未知错误");
        }
    }

    @Override
    public ApiResult<User> login(String username, String password) {
        User user = userMapper.getUserByName(username);
        if (user == null) return new ApiResult<User>().code(ApiResultStatus.Error).message("用户名不存在");

        if (!PHPass.CheckPassword(password, user.password()))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("密码错误");
        //   token ---> user map  存入redis
        String token = getToken(user);
        try
        {
            userMapper.updateUser(user.logged(TimeTools.NowTime()).activated(TimeTools.NowTime()));

            redisTemplate.opsForValue().set(token, user, 6, TimeUnit.HOURS);
            // php使用数据库的users表的authCode字段当作token和cookie验证登录,因为token生成方式不同,本人的token独立于原版typecho,不写入数据库
            user.authCode(token);//只是返回给前端,不做数据库操作
            return new ApiResult<User>().code(ApiResultStatus.Success).message("登录成功").data(removeSecrets(user));


        } catch (Exception e)
        {
            String errorMessage = "登录失败";
            log.error(errorMessage, e);
            return new ApiResult<User>().code(ApiResultStatus.Error).message(errorMessage);
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

    //可能过期
    public ApiResult<User> getUserByToken(String token) {
        if (token == null) return new ApiResult<User>().code(ApiResultStatus.Error).message("token不能为空");
        try
        {
            User user = (User) redisTemplate.opsForValue().get(token);
            if (user != null) return new ApiResult<User>().code(ApiResultStatus.Success).message("获取成功").data(user);
            else
            {
                String message = "token 无数据,已过期";
                log.warn(message);
                return new ApiResult<User>().code(ApiResultStatus.Error).message("message");
            }
        } catch (Exception e)
        {
            log.error("redis get failed" + token);
        }
        return new ApiResult<User>().code(ApiResultStatus.Error).message("从redis获取失败,请重新登录");
    }

    private User removeSecrets(User user) {
        user.password(null);
        user.created(null);
        user.activated(null);
        user.logged(null);
        user.uid(null);
        //        user .authCode(null);
        // token不存在redis中, 在user中.  返回时只移除私密数据,不移除token.
        //由controller层自行移除和设置token
        return user;
    }

    private String getToken(User user) {
        String token = PropertiesConfig.getApplicationName() + ":user:" + user.uid() + ":" + user.name();
        token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
        return token;
    }

    private String getEmailKey(String email) {
        String token = PropertiesConfig.getApplicationName() + ":sendEmail:" + email;
        token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
        return token;
    }

    void test() {
        log.warn("hello");
    }

}
