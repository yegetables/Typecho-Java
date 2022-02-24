package com.yegetables.controller;


import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.User;
import com.yegetables.utils.JsonTools;
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
        if (!StringTools.User.isEmail(email))
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
        //        if (!(map.containsKey("code") && map.containsKey("email") && map.containsKey("password") && map.containsKey("username")))
        //            return new ApiResult<String>().code(ApiResultStatus.Error).message("注册信息不完整[" + map + "]").toString();
        if (!(StringTools.User.isCode(code) && StringTools.User.isUserName(username) && StringTools.User.isPassword(password) && StringTools.User.isEmail(email)))
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
        if (!(StringTools.User.isUserName(username) && StringTools.User.isPassword(password)))
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

        var result = fillUser(map, user);
        if (result.getCode() != ApiResultStatus.Success) return result.toString();
        else user = result.getData();

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
        removeSecrets(user);
        return new ApiResult<User>().code(ApiResultStatus.Success).data(user).toString();
    }

    private void setToken(ApiResult<User> result, HttpSession session) {
        if (result.getCode() == ApiResultStatus.Success && result.getData() != null)
        {
            // key:"token" value:redis 的随机字符串
            session.setAttribute("token", result.getData().authCode());
            removeSecrets(result.getData());
        }
    }

    private User removeSecrets(User user) {
        user.authCode(null);
        user.password(null);
        return user;
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
        ApiResult<User> result;
        if ((StringTools.User.isPassword(password) && StringTools.User.isEmail(email) && StringTools.User.isCode(code)))
            return userService.findPassword(email, password, code).toString();
        else return new ApiResult<User>().code(ApiResultStatus.Error).message("密码找回信息不正确[" + map + "]").toString();
    }

    private ApiResult<User> fillUser(Map<String, String> map, User user) {
        if (user == null) user = new User();
        if (map.containsKey("username"))
        {
            String newUserName = StringTools.mapGetStringKey("username", map);
            if (StringTools.User.isUserName(newUserName)) user.name(newUserName);
            else return new ApiResult<User>().code(ApiResultStatus.Error).message("用户名不正确[" + newUserName + "]");
        }
        if (map.containsKey("password"))
        {
            String newPassword = StringTools.mapGetStringKey("password", map);
            if (StringTools.User.isPassword(newPassword)) user.password(newPassword);
            else return new ApiResult<User>().code(ApiResultStatus.Error).message("密码不正确[" + newPassword + "]");
        }
        if (map.containsKey("url"))
        {
            String newUrl = StringTools.mapGetStringKey("url", map);
            if (StringTools.User.isUrl(newUrl)) user.url(newUrl);
            else return new ApiResult<User>().code(ApiResultStatus.Error).message("url不正确[" + newUrl + "]");
        }
        if (map.containsKey("screenName"))
        {
            String newScreenName = StringTools.mapGetStringKey("screenName", map);
            if (StringTools.User.isScreenName(newScreenName)) user.screenName(newScreenName);
            else return new ApiResult<User>().code(ApiResultStatus.Error).message("昵称不正确[" + newScreenName + "]");
        }
        if (map.containsKey("email"))
        {
            String newEmail = StringTools.mapGetStringKey("email", map);
            if (StringTools.User.isEmail(newEmail)) user.mail(newEmail);
            else return new ApiResult<User>().code(ApiResultStatus.Error).message("邮箱不正确[" + newEmail + "]");
        }


        //        if (map.containsKey("group"))
        //        {
        //            String newGroup = StringTools.mapGetStringKey("group", map);
        //            if (StringTools.User.isGroupName(newGroup))
        //            {
        //                user.group(newGroup);
        //            }
        //            else
        //            {
        //                return new ApiResult<User>().code(ApiResultStatus.Error).message("用户组不正确[" + newGroup + "]");
        //            }
        //        }


        return new ApiResult<User>().code(ApiResultStatus.Success).data(user);


    }

}
