package com.yegetables.controller;


import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.User;
import com.yegetables.utils.JsonTools;
import com.yegetables.utils.PropertiesConfig;
import com.yegetables.utils.StringTools;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/user")
//@SessionAttributes("token")
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
            return new ApiResult<String>().code(ApiResultStatus.Error).message("email不正确[" + email + "]").toString();
        return userService.sendEmailAuthorCode(email).toString();
    }

    /**
     * 用户注册
     *
     * @param map 包含username,password,email,code验证码
     * @return ApiResult的json串
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String registeredAccount(@RequestBody Map<String, String> map, HttpSession session) {
        String email = StringTools.mapGetStringKey("email", map);
        String password = StringTools.mapGetStringKey("password", map);
        String username = StringTools.mapGetStringKey("username", map);
        String code = StringTools.mapGetStringKey("code", map);
        if (!StringTools.isGoodUser(username, password, email, code))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("注册信息填写不正确[" + map + "]").toString();

        ApiResult<User> result = userService.register(username, password, email, code);
        setToken(result, session);
        return result.toString();

    }

    /**
     * 用户登录
     *
     * @param map 包含username,password
     * @return ApiResult的json串
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String LoginAccount(@RequestBody Map<String, String> map, HttpSession session) {
        String password = StringTools.mapGetStringKey("password", map);
        String username = StringTools.mapGetStringKey("username", map);
        if (!StringTools.isInLength(username, PropertiesConfig.getNameMinLength(), PropertiesConfig.getNameMaxLength()) || !StringTools.isInLength(password, PropertiesConfig.getPasswordMinLength(), PropertiesConfig.getPasswordMaxLength()))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("登录信息不正确[" + map + "]").toString();
        ApiResult<User> result = userService.login(username, password);
        setToken(result, session);
        return result.toString();
    }


    /**
     * 用户信息修改
     *
     * @param map 可选(username，password,url,screenName,email)
     * @return ApiResult的json串
     */
    @RequestMapping(value = "/changeUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public String changeUserInfo(@RequestBody Map<String, String> map, @SessionAttribute(name = "token", required = false) String token) {
        var userResult = userService.getUserByToken(token);
        if (userResult.getCode() != ApiResultStatus.Success || userResult.data() == null) return userResult.toString();
        User user = userResult.getData();

        if (map.containsKey("username"))
        {
            String newUserName = StringTools.mapGetStringKey("username", map);
            if (StringTools.isInLength(newUserName, PropertiesConfig.getNameMinLength(), PropertiesConfig.getNameMaxLength()))
                user.name(newUserName);
            else
                return new ApiResult<User>().code(ApiResultStatus.Error).message("用户名不正确[" + newUserName + "]").toString();
        }
        if (map.containsKey("password"))
        {
            String newPassword = StringTools.mapGetStringKey("password", map);
            if (StringTools.isInLength(newPassword, PropertiesConfig.getPasswordMinLength(), PropertiesConfig.getPasswordMaxLength()))
                user.password(newPassword);
            else
                return new ApiResult<User>().code(ApiResultStatus.Error).message("密码不正确[" + newPassword + "]").toString();
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
        //        if (map.containsKey("group"))
        //        {
        //            String newGroup = StringTools.mapGetStringKey("group", map);
        //            if (StringTools.isGroupName(newGroup)){
        //                user.group(newGroup);
        //            }
        //            else
        //            {
        //                return new ApiResult<User>().code(ApiResultStatus.Error).message("用户组不正确[" + newGroup + "]").toString();
        //            }
        //        }

        //暂不支持TODO(支持邮箱更改,用户组更改)
        if (map.containsKey("email"))
        {
            String newEmail = StringTools.mapGetStringKey("email", map);
            if (StringTools.isEmail(newEmail))
            {

                user.mail(newEmail);
            }
            else
            {
                return new ApiResult<User>().code(ApiResultStatus.Error).message("邮箱不正确[" + newEmail + "]").toString();
            }
        }

        return userService.changeUserInfo(user).toString();
    }


    /**
     * 用户信息查询
     *
     * @return ApiResult的json串
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getUserInfo(@SessionAttribute(name = "token", required = false) String token) {
        var userResult = userService.getUserByToken(token);
        if (userResult.getCode() != ApiResultStatus.Success) return userResult.toString();
        User user = userResult.getData();
        user.authCode(null);
        user.password(null);
        return new ApiResult<User>().code(ApiResultStatus.Success).data(user).toString();
    }

    private void setToken(ApiResult<User> result, HttpSession session) {
        if (result.getCode() == ApiResultStatus.Success && result.getData() != null)
        {
            // key:"token" value:redis 的随机字符串
            session.setAttribute("token", result.getData().authCode());
            result.getData().authCode(null);
        }
    }


    /**
     * 用户密码找回,先申请邮箱验证码
     *
     * @param map password,email,code 新密码和邮箱和验证码
     * @return ApiResult的json串
     */
    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    @ResponseBody
    public String findPassword(@RequestBody Map<String, String> map) {
        String email = StringTools.mapGetStringKey("email", map);
        String password = StringTools.mapGetStringKey("password", map);
        String code = StringTools.mapGetStringKey("code", map);
        if (!StringTools.isGoodUser("admin", password, email, code))
            return new ApiResult<User>().code(ApiResultStatus.Error).message("信息填写不正确[" + map + "]").toString();
        ApiResult<User> result = userService.findPassword(email, password, code);
        return result.toString();
    }


}
