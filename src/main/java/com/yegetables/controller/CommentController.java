package com.yegetables.controller;

import com.yegetables.controller.dto.ApiResult;
import com.yegetables.controller.dto.ApiResultStatus;
import com.yegetables.pojo.Comment;
import com.yegetables.pojo.CommentType;
import com.yegetables.pojo.Content;
import com.yegetables.pojo.User;
import com.yegetables.utils.StringTools;
import com.yegetables.utils.TimeTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    /**
     * @param token 登陆之后的cookie
     * @param map   cid(文章id),text,  authorName和email(登陆后不需要) ,可选 (parent,type,status,ip,agent...)
     * @return ApiResult
     */
    @RequestMapping("/addComment")
    @ResponseBody
    public String addComment(@SessionAttribute(name = "token", required = false) String token, @RequestBody Map<String, String> map) {


        var result = fillComment(map, null);
        if (result.getCode() != ApiResultStatus.Success) return result.toString();
        var comment = result.data();
        if (comment.text() == null || comment.text().length() == 0)
        {
            return new ApiResult<Comment>().code(ApiResultStatus.Error).message("评论内容不能为空").toString();
        }
        if (comment.content() == null)
            return new ApiResult<Comment>().code(ApiResultStatus.Error).message("文章cid不能为空").toString();

        if (comment.authorName() == null || comment.email() == null)
        {
            var userResult = userService.getUserByToken(token);
            if (userResult.getCode() != ApiResultStatus.Success || userResult.data() == null)
                return new ApiResult<Comment>().code(ApiResultStatus.Error).message("请携带authorName 和 email , 或者先登陆").toString();
            var user = userResult.data();
            comment.authorName(user.screenName() == null ? user.screenName() : user.name());
            comment.authorId(user.uid());
            comment.email(user.mail());
            comment.url(user.url());
        }
        if (comment.parent() == null) comment.parent(new Comment().coid(0L));

        return commentService.addComment(comment).toString();

    }


    /**
     * 查询评论
     *
     * @param map coid评论id
     * @return ApiResult
     */
    @ResponseBody
    @RequestMapping("/getComment")
    public String getComment(@RequestBody Map<String, String> map) {
        if (!map.containsKey("coid"))
            return new ApiResult<Comment>().code(ApiResultStatus.Error).message("文章cid不能为空").toString();
        Long coid = StringTools.mapGetLongKey("coid", map);
        Comment comment1 = commentService.getComment(new Comment().coid(coid));
        if (comment1 == null) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("评论不存在").toString();
        return new ApiResult<Comment>().data(comment1).code(ApiResultStatus.Success).message("查询成功").toString();
    }

    /**
     * 查询文章所有评论
     *
     * @param map cid或者slug ,cid为主
     * @return ApiResult
     */
    @ResponseBody
    @RequestMapping("/getCommentsByContent")
    public String getCommentsByContent(@RequestBody Map<String, String> map) {
        if (!map.containsKey("cid") && !map.containsKey("slug"))
            return new ApiResult<Comment>().code(ApiResultStatus.Error).message("文章cid或slug不能为空").toString();
        Content content = new Content();
        if (map.containsKey("slug"))
        {
            String slug = StringTools.mapGetStringKey("slug", map);
            content.slug(slug);
        }
        if (map.containsKey("cid"))
        {
            Long cid = StringTools.mapGetLongKey("cid", map);
            content.slug(null);
            content.cid(cid);
        }
        return commentService.getCommentsByContent(content).toString();
    }


    @ResponseBody
    @RequestMapping("/deleteComment")
    public String deleteComment(@SessionAttribute(name = "token", required = false) String token, @RequestBody Map<String, String> map) {

        if (!map.containsKey("coid"))
            return new ApiResult<Comment>().code(ApiResultStatus.Error).message("文章cid不能为空").toString();
        Long coid = StringTools.mapGetLongKey("coid", map);

        var userResult = userService.getUserByToken(token);
        if (userResult.getCode() != ApiResultStatus.Success || userResult.data() == null)
            return new ApiResult<Comment>().code(ApiResultStatus.Error).message("请先登陆").toString();
        var user = userResult.data();
        Comment comment = commentService.getComment(new Comment().coid(coid));
        if (comment == null) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("评论不存在").toString();
        if (!Objects.equals(comment.authorId(), user.uid()))
        {
            return new ApiResult<Comment>().code(ApiResultStatus.Error).message("没有权限删除该评论").toString();
        }
        return commentService.deleteComment(new Comment().coid(coid)).toString();

    }


    private ApiResult<Comment> fillComment(Map<String, String> map, Comment comment) {
        if (comment == null) comment = new Comment();
        if (!map.containsKey("cid")) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("提供cid");
        else
        {
            Long cid = StringTools.mapGetLongKey("cid", map);
            if (cid <= 0) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("cid 错误");
            comment.content(new Content().cid(cid));
        }
        comment.created(TimeTools.NowTime());
        if (map.containsKey("authorName"))
        {
            String authorName = StringTools.mapGetStringKey("authorName", map);
            if (StringTools.User.isUserName(authorName))
            {
                comment.authorName(authorName);
            }
            else
            {
                return new ApiResult<Comment>().code(ApiResultStatus.Error).message("authorName 错误");
            }
        }
        if (map.containsKey("authorId"))
        {
            Long authorId = StringTools.mapGetLongKey("authorId", map);
            if (authorId <= 0) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("authorId格式错误");
            comment.authorId(authorId);
        }

        if (map.containsKey("text"))
        {
            comment.text(map.get("text"));
        }
        if (map.containsKey("agent"))
        {
            comment.agent(map.get("agent"));
        }
        if (map.containsKey("ip"))
        {
            comment.ip(map.get("ip"));
        }
        if (map.containsKey("url"))
        {
            comment.url(map.get("url"));
        }
        if (map.containsKey("email"))
        {
            String email = StringTools.mapGetStringKey("email", map);
            if (StringTools.User.isEmail(email)) comment.email(map.get("email"));
            else return new ApiResult<Comment>().code(ApiResultStatus.Error).message("email格式错误");
        }
        if (map.containsKey("owner"))
        {
            Long userId = StringTools.mapGetLongKey("owner", map);
            if (userId <= 0)
            {
                String userName = StringTools.mapGetStringKey("owner", map);
                if (!StringTools.User.isUserName(userName))
                {
                    return new ApiResult<Comment>().code(ApiResultStatus.Error).message("owner格式错误");
                }
                else
                {
                    comment.owner(new User().name(userName));
                }
            }
            else comment.owner(new User().uid(userId));
        }
        if (map.containsKey("parent"))
        {
            Long parent = StringTools.mapGetLongKey("parent", map);
            if (parent <= 0) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("parent格式错误");
            comment.parent(new Comment().coid(parent));
        }

        //        if (map.containsKey("status"))
        //        {
        //            CommentStatus status = StringTools.Comment.getStatus(map.get("status"));
        //            if (status == null) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("status格式错误");
        //            comment.status(status);
        //        }
        if (map.containsKey("type"))
        {
            CommentType type = StringTools.Comment.getType(map.get("type"));
            if (type == null) return new ApiResult<Comment>().code(ApiResultStatus.Error).message("type格式错误");
            comment.type(type);
        }

        return new ApiResult<Comment>().code(ApiResultStatus.Success).data(comment);
    }
}


