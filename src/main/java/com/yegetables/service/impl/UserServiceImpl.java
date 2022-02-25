package com.yegetables.service.impl;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.User;
import com.yegetables.service.UserService;
import com.yegetables.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;
    // private final Map<String, String> mailAuthCodeMap = new HashMap<>();
    @Autowired
    MailTools mailTools;
    @Autowired
    PHPass phpass;

    @Override
    public ApiResult<String> sendEmailAuthorCode(String email) {
        //        if (getUser(new User().mail(email)) != null)
        //            return new ApiResult<String>().code(ApiResultStatus.Error).message("该邮箱已经注册");
        String text = RandomTools.getRandomAuthorCode(PropertiesConfig.User.getAuthCodeLength());
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
    @Transactional
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
            else newUser = getUser(new User().uid(newUser.uid())); //获得数据库默认值  非必要
            //            // 注册成功后自动登录
            String token = getToken(newUser);
            //            try
            //            {
            //                redisTemplate.opsForValue().set(token, newUser, 6, TimeUnit.HOURS);
            //            } catch (Exception e)
            //            {
            //                log.error("redis set failed" + token);
            //            }
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
    @Transactional
    public ApiResult<User> login(String username, String password) {
        User user = getUser(new User().name(username));
        if (user == null) return new ApiResult<User>().code(ApiResultStatus.Error).message("用户名不存在");
        if (!PHPass.CheckPassword(password, user.password()))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("密码错误");
        //   token ---> user map  存入redis
        String token = getToken(user);
        try
        {
            updateUser(user.logged(TimeTools.NowTime()).activated(TimeTools.NowTime()));
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
            User oldUser = getUser(new User().uid(user.uid()));
            if (oldUser == null) return new ApiResult<User>().code(ApiResultStatus.Error).message("用户不存在");
            if (user.name() != null && !oldUser.name().equals(user.name()) && getUser(new User().name(user.name())) != null)
                return new ApiResult<User>().code(ApiResultStatus.Error).message("用户名已存在");
            if (user.mail() != null && !oldUser.mail().equals(user.mail()) && getUser(new User().mail(user.mail())) != null)
                return new ApiResult<User>().code(ApiResultStatus.Error).message("邮箱已存在");
        } catch (Exception e)
        {
            String errorMessage = "查找用户失败";
            log.error(errorMessage, e);
            return new ApiResult<User>().code(ApiResultStatus.Error).message(errorMessage);
        }
        try
        {
            updateUser(user);
            return new ApiResult<User>().code(ApiResultStatus.Success).message("更新成功").data(removeSecrets(user).authCode(null));
        } catch (Exception e)
        {
            String errorMessage = "更新用户信息失败";
            log.error(errorMessage, e);
            return new ApiResult<User>().code(ApiResultStatus.Error).message(errorMessage);
        }
    }


    @Override
    @Transactional
    public ApiResult<User> findPassword(String email, String password, String code) {
        String key = getEmailKey(email);
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) return new ApiResult<User>().code(ApiResultStatus.Error).message("请先发送验证码");
        if (!StringUtils.equalsIgnoreCase(String.valueOf(value), code))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("验证码错误,请重试");


        User user = getUser(new User().mail(email));
        user.password(phpass.HashPassword(password));
        try
        {
            updateUser(user);
            try
            {
                redisTemplate.delete(key);
            } catch (Exception e)
            {
                log.error("redis delete failed" + key);
            }
            return new ApiResult<User>().code(ApiResultStatus.Success).message("更新成功").data(removeSecrets(user).authCode(null));
        } catch (Exception e)
        {
            String errorMessage = "更新用户信息失败";
            log.error(errorMessage, e);
            return new ApiResult<User>().code(ApiResultStatus.Error).message(errorMessage);
        }
    }

    @Override
    public ApiResult<User> deleteAccount(User user) {
        if (user == null || user.uid() == null)
            return new ApiResult<User>().code(ApiResultStatus.Error).message("用户不存在");
        User older = getUser(user);
        if (older == null) return new ApiResult<User>().code(ApiResultStatus.Error).message("用户不存在");
        String token = getToken(user);
        try
        {
            if (user.uid() != null)
            {
                token = getTokenById(user.uid());
                redisTemplate.delete(token);
            }
            if (user.name() != null)
            {
                token = getTokenByName(user.name());
                redisTemplate.delete(token);

            }
            if (user.mail() != null)
            {
                token = getTokenByMail(user.mail());
                redisTemplate.delete(token);
            }
            //            redisTemplate.opsForValue().set(token, user, 6, TimeUnit.HOURS);
            userMapper.deleteUser(user);
        } catch (Exception e)
        {
            log.error("redis get failed" + token);
        }
        return new ApiResult<User>().code(ApiResultStatus.Success).message("删除成功");
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
        //        String token = PropertiesConfig.getApplicationName() + ":user:";
        if (user.uid() != null) return getTokenById(user.uid());
        if (user.name() != null) return getTokenByName(user.name());
        if ((user.mail() != null)) return getTokenByMail(user.mail());
        return "";
    }

    private String getTokenById(Long id) {
        String token = PropertiesConfig.getApplicationName() + ":user:";
        if (id != null)
        {
            token += "uid:" + id;
            token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
            return token;
        }
        return "";
    }

    private String getTokenByName(String name) {
        String token = PropertiesConfig.getApplicationName() + ":user:";
        if (name != null)
        {
            token += "name:" + name;
            token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
            return token;
        }
        return "";
    }

    private String getEmailKey(String email) {
        String token = PropertiesConfig.getApplicationName() + ":sendEmail:" + email;
        token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
        return token;
    }

    private String getTokenByMail(String email) {
        String token = PropertiesConfig.getApplicationName() + ":user:";

        if ((email != null))
        {
            token += "mail:" + email;
            token += ":" + DigestUtils.md5DigestAsHex(token.getBytes());
            return token;
        }
        return "";
    }

    @Override
    public User getUser(User user) {
        String token = getToken(user);
        try
        {
            var result = getUserByToken(token);
            if (result.code() == ApiResultStatus.Success) return result.data();
            var newUser = userMapper.getUser(user);
            if (newUser == null) return null;
            if (newUser.uid() != null)
            {
                token = getTokenById(newUser.uid());
                redisTemplate.opsForValue().set(token, newUser, 6, TimeUnit.HOURS);
            }
            if (newUser.name() != null)
            {
                token = getTokenByName(newUser.name());
                redisTemplate.opsForValue().set(token, newUser, 6, TimeUnit.HOURS);
            }
            if (newUser.mail() != null)
            {
                token = getTokenByMail(newUser.mail());
                redisTemplate.opsForValue().set(token, newUser, 6, TimeUnit.HOURS);
            }
            return newUser;
        } catch (Exception e)
        {
            log.error("redis get failed" + token);
        }
        return null;
    }

    public void updateUser(User user) {
        if (user == null || user.uid() == null) return;
        User older = getUser(user);
        if (older == null) return;
        String token = getToken(user);
        try
        {
            if (user.uid() != null)
            {
                token = getTokenById(user.uid());
                redisTemplate.opsForValue().set(token, user, 6, TimeUnit.HOURS);
            }
            if (user.name() != null)
            {
                token = getTokenByName(user.name());
                redisTemplate.opsForValue().set(token, user, 6, TimeUnit.HOURS);
            }
            if (user.mail() != null)
            {
                token = getTokenByMail(user.mail());
                redisTemplate.opsForValue().set(token, user, 6, TimeUnit.HOURS);
            }
            //            redisTemplate.opsForValue().set(token, user, 6, TimeUnit.HOURS);
            userMapper.updateUser(user);
        } catch (Exception e)
        {
            log.error("redis get failed" + token);
        }
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
                String message = "token 已过期,请重新登录";
                log.warn(message);
                return new ApiResult<User>().code(ApiResultStatus.Error).message("message");
            }
        } catch (Exception e)
        {
            log.error("redis get failed" + token);
        }
        return new ApiResult<User>().code(ApiResultStatus.Error).message("从redis获取失败,请重新登录");
    }

}
