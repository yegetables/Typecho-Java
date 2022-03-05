package com.yegetables.controller;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.Content;
import com.yegetables.pojo.User;
import com.yegetables.utils.JsonTools;
import com.yegetables.utils.RandomTools;
import com.yegetables.utils.StringTools;
import com.yegetables.utils.TimeTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/content")
//@SessionAttributes("token")
public class ContentController extends BaseController {
    /**
     * 填充文章
     *
     * @param token 登录
     * @param map   title必填,
     * @return apiResult
     */
    @RequestMapping("/addContent")
    @ResponseBody
    public String addContent(@SessionAttribute(name = "token", required = false) String token, @RequestBody Map<String, String> map) {
        if (!map.containsKey("title"))
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("标题不能为空").toString();

        var userResult = userService.getUserByToken(token);
        if (userResult.getCode() != ApiResultStatus.Success || userResult.data() == null) return userResult.toString();
        User user = userResult.getData();

        Content content = new Content().author(user).created(TimeTools.NowTime()).modified(TimeTools.NowTime());
        var result = fillContent(map, content);
        if (result.getCode() != ApiResultStatus.Success) return result.toString();
        if (content.slug() == null) content.slug(RandomTools.getRandomName());
        //slug 唯一,parent填充
        return contentService.addContent(content).toString();

    }

    private ApiResult<Content> fillContent(Map<String, String> map, Content content) {

        if (map.containsKey("title"))
        {
            String newTitle = StringTools.mapGetStringKey("title", map);
            if (StringTools.Content.isTitle(newTitle)) content.title(newTitle);
            else return new ApiResult<Content>().code(ApiResultStatus.Error).message("标题不合法");
        }
        if (map.containsKey("slug"))
        {
            String newSlug = StringTools.mapGetStringKey("slug", map);
            if (StringTools.Content.isSlug(newSlug)) content.slug(newSlug);
            else return new ApiResult<Content>().code(ApiResultStatus.Error).message("slug概述不合法");
        }
        if (map.containsKey("template"))
        {
            String newTemplate = StringTools.mapGetStringKey("template", map);
            if (StringTools.Content.isTemplate(newTemplate)) content.template(newTemplate);
            else return new ApiResult<Content>().code(ApiResultStatus.Error).message("模板不合法");
        }
        if (map.containsKey("status"))
        {
            String newStatus = StringTools.mapGetStringKey("status", map);
            if (StringTools.Content.isStatus(newStatus)) content.status(StringTools.Content.getStatus(newStatus));
            else return new ApiResult<Content>().code(ApiResultStatus.Error).message("状态不合法");
        }
        if (map.containsKey("type"))
        {
            String newType = StringTools.mapGetStringKey("type", map);
            if (StringTools.Content.isType(newType)) content.type(StringTools.Content.getType(newType));
            else return new ApiResult<Content>().code(ApiResultStatus.Error).message("类型不合法");
        }
        if (map.containsKey("password"))
        {
            String newPassword = StringTools.mapGetStringKey("password", map);
            if (StringTools.Content.isPassword(newPassword)) content.password(newPassword);
            else return new ApiResult<Content>().code(ApiResultStatus.Error).message("密码不合法");
        }
        if (map.containsKey("allowComment"))
        {
            content.allowComment(Boolean.valueOf(map.get("allowComment")));
        }
        if (map.containsKey("allowPing"))
        {
            content.allowPing(Boolean.valueOf(map.get("allowPing")));
        }
        if (map.containsKey("allowFeed"))
        {
            content.allowFeed(Boolean.valueOf(map.get("allowFeed")));
        }
        if (map.containsKey("parent"))
        {
            content.parent(new Content().cid(Long.valueOf(map.get("parent"))));
        }
        if (map.containsKey("order"))
        {
            content.order(Long.valueOf(map.get("order")));
        }
        if (map.containsKey("text"))
        {
            content.text(map.get("text"));
        }
        return new ApiResult<Content>().code(ApiResultStatus.Success).data(content);
    }

    /**
     * 删除内容
     *
     * @param token 用户token
     * @param json  内容id
     * @return 删除结果
     */
    @RequestMapping("/deleteContent")
    @ResponseBody
    public String deleteContent(@SessionAttribute(name = "token", required = false) String token, @RequestBody String json) {
        long cid = -1L;
        if (JsonTools.isJson(json)) cid = StringTools.mapGetLongKey("cid", jsonTools.JsonToMap(json));
        if (cid < 0) return new ApiResult<Content>().code(ApiResultStatus.Error).message("cid不合法").toString();

        var userResult = userService.getUserByToken(token);
        if (userResult.getCode() != ApiResultStatus.Success || userResult.data() == null) return userResult.toString();
        var user = userResult.data();

        var content = contentService.getContent(new Content().cid(cid));
        if (content == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("文章不存在").toString();

        if (content.author() == null || !Objects.equals(content.author().uid(), user.uid()))
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("没有权限").toString();

        return contentService.deleteContent(content).toString();
    }

    /**
     * 获取内容
     *
     * @param token 用户token
     * @param map   内容id
     * @return 内容
     */
    @RequestMapping("/updateContent")
    @ResponseBody
    public String updateContent(@SessionAttribute(name = "token", required = false) String token, @RequestBody Map<String, String> map) {
        var userResult = userService.getUserByToken(token);
        if (userResult.getCode() != ApiResultStatus.Success || userResult.data() == null) return userResult.toString();
        User user = userResult.getData();

        if (!map.containsKey("cid"))
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("请传递cid参数").toString();
        var cid = StringTools.mapGetLongKey("cid", map);
        var content = contentService.getContent(new Content().cid(cid));
        if (content == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("文章不存在").toString();
        if (content.author() == null || !Objects.equals(content.author().uid(), user.uid()))
        {
            return new ApiResult<Content>().code(ApiResultStatus.Error).message("没有权限").toString();
        }

        var result = fillContent(map, content);
        if (result.getCode() != ApiResultStatus.Success) return result.toString();
        return contentService.updateContent(content).toString();

    }


    /**
     * 获取内容
     *
     * @param map 内容id
     * @return 内容
     */
    @RequestMapping("/getContent")
    @ResponseBody
    public String getContent(@RequestBody Map<String, String> map) {
        if (map.containsKey("cid"))
        {
            var cid = StringTools.mapGetLongKey("cid", map);
            var content = contentService.getContent(new Content().cid(cid));
            if (content == null)
                return new ApiResult<Content>().code(ApiResultStatus.Error).message("文章不存在").toString();
            return new ApiResult<Content>().code(ApiResultStatus.Success).data(content).toString();
        }
        if (map.containsKey("authorName") || map.containsKey("authorId"))
        {
            User author = null;
            if (map.containsKey("authorName"))
            {
                author = userService.getUser(new User().name(StringTools.mapGetStringKey("authorName", map)));
            }
            if (map.containsKey("authorId"))
            {
                author = userService.getUser(new User().uid(StringTools.mapGetLongKey("authorId", map)));

            }
            if (author == null) return new ApiResult<Content>().code(ApiResultStatus.Error).message("用户不存在").toString();
            var content = contentService.getContentByAuthor(author);
            if (content == null || content.size() == 0)
                return new ApiResult<Content>().code(ApiResultStatus.Error).message("用户没有发表文章").toString();
            return new ApiResult<List<Content>>().code(ApiResultStatus.Success).data(content).toString();
        }


        return new ApiResult<Content>().code(ApiResultStatus.Error).message("请传递cid参数").toString();
    }

}
