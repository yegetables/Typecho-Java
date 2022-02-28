package com.yegetables.controller;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.Meta;
import com.yegetables.pojo.MetaType;
import com.yegetables.utils.JsonTools;
import com.yegetables.utils.RandomTools;
import com.yegetables.utils.StringTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/meta")
public class MetaController extends BaseController {

    /**
     * 查询都有哪些分类
     *
     * @param
     * @return
     */
    @RequestMapping("/allCategory")
    @ResponseBody
    public String allCategory() {
        return allType(MetaType.category.toString());
    }

    /**
     * 查询都有哪些标签
     *
     * @param
     * @return
     */
    @RequestMapping("/allTag")
    @ResponseBody
    public String allTag() {
        //        return allType("{\"type\":\"tag\"}");
        return allType(MetaType.tag.toString());
    }


    /**
     * 根据slug查询meta
     *
     * @param `slug` 标识
     * @return apiResult的json字符串
     */
    @RequestMapping("/getSlug")
    @ResponseBody
    public String getSlug(@RequestBody Map<String, String> map) {
        if (map.containsKey("slug"))
        {
            String slug = StringTools.mapGetStringKey("slug", map);
            var meta = metaService.getMetaBySlug(slug);
            if (meta != null) return new ApiResult<Meta>().message("获取成功").data(meta).toString();
            else return new ApiResult<Meta>().message("无此slug").toString();
        }
        return new ApiResult<Meta>().code(ApiResultStatus.Error).message("参数错误").toString();
    }


    /**
     * 查询都有哪些类型
     *
     * @param
     * @return
     */
    @RequestMapping("/allType")
    @ResponseBody
    public String allType(@RequestBody String type) {
        type = StringTools.toOkString(type);
        if (JsonTools.isJson(type)) type = StringTools.mapGetStringKey("type", jsonTools.JsonToMap(type));
        if (!StringTools.Meta.isType(type))
            return new ApiResult<List<Meta>>().code(ApiResultStatus.Error).message("type错误").toString();
        List<Meta> metas = metaService.allType(StringTools.Meta.getType(type));
        ApiResult<List<Meta>> result = new ApiResult<List<Meta>>().code(ApiResultStatus.Success).message("查询成功").data(metas);
        return result.toString();
    }

    /**
     * 新增一种类型
     *
     * @param token, name,type等
     * @return apiResult的json字符串
     */
    @RequestMapping("/addMeta")
    @ResponseBody
    public String addMeta(@RequestBody Map<String, String> map, @SessionAttribute(value = "token", required = false) String token) {
        var userResult = userService.getUserByToken(token);
        if (userResult.getCode() != ApiResultStatus.Success) return userResult.toString();
        if (!(map.containsKey("name") && map.containsKey("type")))
            return new ApiResult<>().code(ApiResultStatus.Error).message("参数错误").toString();
        if (!map.containsKey("slug")) map.put("slug", RandomTools.getRandomName());
        Meta meta = new Meta();
        var result = fillMeta(map, meta);
        if (result.getCode() != ApiResultStatus.Success) return result.toString();
        return metaService.addMeta(meta).toString();
    }


    /**
     * 删除一种类型
     *
     * @param token, slug
     * @return apiResult的json字符串
     */
    @RequestMapping("/deleteMeta")
    @ResponseBody
    public String deleteMeta(@RequestBody Map<String, String> map, @SessionAttribute(value = "token", required = false) String token) {
        var userResult = userService.getUserByToken(token);
        if (userResult.getCode() != ApiResultStatus.Success) return userResult.toString();
        if (map.containsKey("slug"))
        {
            String slug = StringTools.mapGetStringKey("slug", map);
            if (StringTools.Meta.isSlug(slug)) return metaService.deleteMetaBySlug(slug).toString();
        }
        return new ApiResult<>().code(ApiResultStatus.Error).message("参数错误").toString();
    }


    private ApiResult<Meta> fillMeta(Map<String, String> map, Meta meta) {
        if (map.containsKey("name"))
        {
            String name = StringTools.mapGetStringKey("name", map);
            if (StringTools.Meta.isName(name)) meta.setName(name);
            else return new ApiResult<Meta>().code(ApiResultStatus.Error).message("name格式不符合要求");
        }
        if (map.containsKey("slug"))
        {
            String slug = StringTools.mapGetStringKey("slug", map);
            if (StringTools.Meta.isSlug(slug)) meta.setSlug(slug);
            else return new ApiResult<Meta>().code(ApiResultStatus.Error).message("slug格式不符合要求");
        }
        if (map.containsKey("type"))
        {
            MetaType type = StringTools.Meta.getType(StringTools.mapGetStringKey("type", map));
            if (type != null) meta.setType(type);
            else return new ApiResult<Meta>().code(ApiResultStatus.Error).message("type格式不符合要求");
        }
        if (map.containsKey("description"))
        {
            String description = StringTools.mapGetStringKey("description", map);
            if (StringTools.Meta.isDescription(description)) meta.setDescription(description);
        }
        if (map.containsKey("parent"))
        {
            Long parentId = StringTools.mapGetLongKey("parent", map);
            if (parentId != 0L) meta.parent(new Meta().mid(parentId));
        }
        if (map.containsKey("order"))
        {
            Long order = StringTools.mapGetLongKey("order", map);
            if (order != 0L) meta.setOrder(order);
        }
        return new ApiResult<Meta>().code(ApiResultStatus.Success).message("填充成功").data(meta);

    }
}
