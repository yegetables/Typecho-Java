package com.yegetables.controller;

import com.yegetables.pojo.User;
import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.utils.JsonTools;
import com.yegetables.utils.PropertiesConfig;
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
        if (JsonTools.isJson(email)) email = StringTools.mapGetStringKey("email", jsonTools.JsonToMap(email));
        if (!StringTools.isEmail(email))
            return new ApiResult<String>().code(ApiResultStatus.Error).message("email格式不正确[" + email + "]").toString();
        return userService.sendEmailAuthorCode(email).toString();
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
        if (!StringTools.isGoodUser(username, password, email, code))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("注册信息不正确[" + map + "]").toString();
        return userService.register(username, password, email, code).toString();
    }

    /**
     * 用户登录
     *
     * @param map 包含用户名，密码
     * @return ApiResult的json串
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String LoginAccount(@RequestBody Map<String, String> map) {
        String password = StringTools.mapGetStringKey("password", map);
        String username = StringTools.mapGetStringKey("username", map);
        if (!StringTools.isInLength(username, PropertiesConfig.getNameMinLength(), PropertiesConfig.getNameMaxLength()) || !StringTools.isInLength(password, PropertiesConfig.getPasswordMinLength(), PropertiesConfig.getPasswordMaxLength()))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("登录信息不正确[" + map + "]").toString();
        return userService.login(username, password).toString();
    }


    /**
     * 用户信息修改
     *
     * @param map 必须有(uid),可选(username，password，url,screenName)暂不支持(email,group)
     * @return ApiResult的json串
     */
    @RequestMapping(value = "/changeUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public String changeUserInfo(@RequestBody Map map) {
        if (!map.containsKey("uid"))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("uid不能为空").toString();
        Long uid = StringTools.mapGetLongKey("uid", map);
        if (uid <= 0)
            return new ApiResult<User>().code(ApiResultStatus.Error).message("uid不正确[" + map + "]").toString();
        User user = new User().uid(uid);
        if (map.containsKey("username"))
        {
            String newUserName = StringTools.mapGetStringKey("username", map);
            if (StringTools.isInLength(newUserName, PropertiesConfig.getNameMinLength(), PropertiesConfig.getNameMaxLength()))
                user.name(newUserName);
        }
        if (map.containsKey("password"))
        {
            String newPassword = StringTools.mapGetStringKey("password", map);
            if (StringTools.isInLength(newPassword, PropertiesConfig.getPasswordMinLength(), PropertiesConfig.getPasswordMaxLength()))
                user.password(newPassword);
        }
        if (map.containsKey("url"))
        {
            String newUrl = StringTools.mapGetStringKey("url", map);
            user.url(newUrl);
        }
        if (map.containsKey("screenName"))
        {
            String newScreenName = StringTools.mapGetStringKey("screenName", map);
            user.screenName(newScreenName);
        }
        //暂不支持TODO(支持邮箱更改,用户组更改)
        //        if (map.containsKey("email"))
        //        {
        //            String newEmail = StringTools.mapGetStringKey("email", map);
        //            if (StringTools.isEmail(newEmail))
        //            {
        //                // TODO:邮箱是否已经被注册,＋验证码
        //                // user.email(newEmail);
        //            }
        //        }
        //        if (map.containsKey("group"))
        //        {
        //            String newGroup = StringTools.mapGetStringKey("group", map);
        //            //            user.group(newGroup);
        //        }
        return userService.changeUserInfo(user).toString();
    }


    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getUserInfo(@RequestBody Map map) {
        if (map.containsKey("username"))
        {
            String username = StringTools.mapGetStringKey("username", map);
            if (StringTools.isInLength(username, PropertiesConfig.getNameMinLength(), PropertiesConfig.getNameMaxLength()))
            {
                User user = userService.getUser(new User().name(username));
                return new ApiResult<User>().message("获取用户信息成功").data(user).toString();
            }
            else return new ApiResult<User>().code(ApiResultStatus.Error).message("用户名不正确[" + map + "]").toString();
        }
        if (map.containsKey("uid"))
        {
            Long uid = StringTools.mapGetLongKey("uid", map);
            if (uid > 0)
            {
                User user = userService.getUser(new User().uid(uid));
                return new ApiResult<User>().message("获取用户信息成功").data(user).toString();
            }
            else return new ApiResult<User>().code(ApiResultStatus.Error).message("uid不正确[" + map + "]").toString();
        }
        if (map.containsKey("email"))
        {
            String email = StringTools.mapGetStringKey("email", map);
            if (StringTools.isEmail(email))
            {
                User user = userService.getUser(new User().mail(email));
                return new ApiResult<User>().message("获取用户信息成功").data(user).toString();
            }
            else return new ApiResult<User>().code(ApiResultStatus.Error).message("邮箱不正确[" + map + "]").toString();
        }
        return new ApiResult<User>().code(ApiResultStatus.Error).message("参数不正确[" + map + "]" + ",需要uid,username,email任意一个").toString();
    }
}
