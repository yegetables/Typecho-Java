package com.yegetables.controller;

import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.Content;
import com.yegetables.pojo.User;
import com.yegetables.utils.PropertiesConfig;
import com.yegetables.utils.StringTools;
import com.yegetables.utils.TimeTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

@Controller
@RequestMapping("/content")
//@SessionAttributes("token")
public class ContentController extends BaseController {
    @RequestMapping("/addContent")
    @ResponseBody
    public String addContent(@SessionAttribute(name = "token", required = false) String token, @RequestBody Map<String, String> map) {
        var userResult = userService.getUserByToken(token);
        if (userResult.getCode() != ApiResultStatus.Success || userResult.data() == null) return userResult.toString();
        User user = userResult.getData();
        Content content = new Content().author(user).created(TimeTools.NowTime()).modified(TimeTools.NowTime());


        return "addContent";
    }

    private Content fillContent(Map<String, String> map, Content content) {

        if (map.containsKey("title"))
        {
            String newTitle = StringTools.mapGetStringKey("title", map);
            content.title(newTitle);
        }
        if (map.containsKey("password"))
        {
            String newPassword = StringTools.mapGetStringKey("password", map);
            if (StringTools.isInLength(newPassword, PropertiesConfig.User.getPasswordMinLength(), PropertiesConfig.User.getPasswordMaxLength()))
                ;
            //                user.password(newPassword);
            //            else
            //                return new ApiResult<Content>().code(ApiResultStatus.Error).message("密码不正确[" + newPassword + "]");
        }
        if (map.containsKey("url"))
        {
            String newUrl = StringTools.mapGetStringKey("url", map);
            //            user.url(newUrl);
        }
        if (map.containsKey("screenName"))
        {
            String newScreenName = StringTools.mapGetStringKey("screenName", map);
            //            user.screenName(newScreenName);
        }
        return content;
    }

    @RequestMapping("/deleteContent")
    @ResponseBody
    public String deleteContent() {
        return "addContent";
    }

    @RequestMapping("/updateContent")
    @ResponseBody
    public String updateContent() {
        return "addContent";
    }


}
