package com.yegetables.service.impl;

import com.yegetables.pojo.User;
import com.yegetables.service.ApiResult;
import com.yegetables.service.ApiResultStatus;
import com.yegetables.service.UserService;
import com.yegetables.utils.MailTools;
import com.yegetables.utils.RandomTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private final Map<String, String> mailAuthCodeMap = new HashMap<>();
    @Autowired
    MailTools mailTools;


    void test() {
        log.warn("hello");
    }

    @Override
    public ApiResult<String> sendEmailAuthorCode(String email) {
        String text = RandomTools.getRandomText(8);
        try
        {
            mailTools.sendSimpleMail(email, "验证码", text);
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

    public ApiResult<String> register(String username, String password, String mail, String code) {
        if (mailAuthCodeMap.get(mail) == null)
        {
            return new ApiResult<String>().code(ApiResultStatus.Error).message("请先发送验证码");
        }
        if (!mailAuthCodeMap.get(mail).equals(code))
        {
            return new ApiResult<String>().code(ApiResultStatus.Error).message("验证码错误,请重试");
        }
        try
        {
            Integer sum = userMapper.addUser(new User().mail(mail).name(username).password(password));
            if (sum != 1) throw new Exception();
            return new ApiResult<String>().code(ApiResultStatus.Success).message("注册成功");

        } catch (Exception e)
        {
            String errorMessage = "注册失败";
            log.warn(errorMessage, e);
            return new ApiResult<String>().code(ApiResultStatus.Error).message("注册失败,发生未知错误");
        }
    }


}
