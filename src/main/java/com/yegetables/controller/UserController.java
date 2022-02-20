package com.yegetables.controller;

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
        else result = new ApiResult().code(ApiResultStatus.Error).message("email格式不正确[" + email + "]");
        //        log.info("send email result=[" + result.toString() + "]");
        return result.toString();
    }
}
