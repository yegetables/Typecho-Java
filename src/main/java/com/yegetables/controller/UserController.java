package com.yegetables.controller;

import com.yegetables.pojo.User;
import com.yegetables.service.ApiResult;
import com.yegetables.service.ApiResultStatus;
import com.yegetables.utils.JsonTools;
import com.yegetables.utils.StringTools;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    /**
     * 直接发送邮件验证码
     *
     * @param email 收件地址
     * @return ApiResult的json串
     * @throws MailException 发件异常
     */
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail(@RequestBody String email) throws MailException {
        //        log.warn("send  email [" + email + "]");
        if (JsonTools.isJson(email))
        {
            Map map = jsonTools.JsonToMap(email);
            email = StringTools.mapGetStringKey("email", map);
            //            email = map.get("email") != null ? String.valueOf(map.get("email")) : "";
        }
        ApiResult result = null;
        if (StringTools.isEmail(email))
        {
            //            result = new ApiResult().code(ApiResultStatus.Success).message("发送成功");
            result = userService.sendEmailAuthorCode(email);
        }
        else
        {
            result = new ApiResult().code(ApiResultStatus.Error).message("email格式不正确[" + email + "]");
        }//        log.info("send email result=[" + result.toString() + "]");
        return result.toString();
    }

    /**
     * 用户注册
     *
     * @param map 包含用户名，密码，邮箱，验证码
     * @return ApiResult的json串
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String registeredAccount(@RequestBody Map<String, String> map) {
        String email = StringTools.mapGetStringKey("email", map);
        String password = StringTools.mapGetStringKey("password", map);
        String username = StringTools.mapGetStringKey("username", map);
        String code = StringTools.mapGetStringKey("code", map);
        ApiResult<User> result = null;
        if (StringTools.isGoodUser(username, password, email, code))
        {
            result = userService.register(username, password, email, code);
        }
        else
        {
            result = new ApiResult().code(ApiResultStatus.Error).message("注册信息不正确[" + map + "]");
        }
        //        log.info("send email result=[" + result.toString() + "]");
        return result.toString();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String LoginAccount(@RequestBody Map<String, String> map) {
        String password = StringTools.mapGetStringKey("password", map);
        String username = StringTools.mapGetStringKey("username", map);
        ApiResult<User> result = null;
        if (StringTools.isInLength(username, 0, 32) && StringTools.isInLength(password, 0, 64))
        {
            result = userService.login(username, password);
        }
        else
        {
            result = new ApiResult().code(ApiResultStatus.Error).message("登录信息不正确[" + map + "]");
        }
        //        log.info("send email result=[" + result.toString() + "]");
        return result.toString();
    }


}
