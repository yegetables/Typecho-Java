package com.yegetables.controller;


import com.yegetables.pojo.User;
import com.yegetables.utils.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class WebControllerTest extends BaseController {

    @RequestMapping(value = "/sendEmail1", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail1(@RequestParam String email) {
        log.warn("[" + email + "]");
        return "test";
    }

    @RequestMapping(value = "/sendEmail2", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail2(@RequestParam Map email) {
        log.warn("[" + email + "]");
        return "test";
    }

    @RequestMapping(value = "/sendEmail3", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail3(@RequestBody String email) {
        log.warn("[" + email + "]");
        return "test";
    }

    @RequestMapping(value = "/sendEmail4", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail4(@RequestBody Map email) {
        log.warn("[" + email + "]");
        return "test";
    }

    @RequestMapping(value = "/sendEmail5", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail5(String email) {
        log.warn("[" + email + "]");
        return "test";
    }

    @RequestMapping(value = "/chinese")
    @ResponseBody
    public String chinese1() {
        return "我是中文测试";
    }

    @RequestMapping(value = "/value")
    @ResponseBody
    public String value1() {
        log.warn("max=" + PropertiesConfig.User.getNameMaxLength());
        //        log.warn("email=" + PropertiesConfig.getEmailRegex());
        log.warn("authCodeLength=" + PropertiesConfig.User.getAuthCodeLength());
        log.warn("application=" + PropertiesConfig.getApplicationName());
        return "";
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value = "/redis")
    @ResponseBody
    public String redis1() {
        log.warn("redis=" + redisTemplate);
        try
        {
            redisTemplate.opsForValue().set("test1", "value1");
            log.warn("test1=" + redisTemplate.opsForValue().get("test1"));

            redisTemplate.opsForValue().set("test2", new User().name("hello").password("123456"));
            User user2 = (User) redisTemplate.opsForValue().get("test2");
            assert user2 != null;
            log.warn("test2=" + user2.name() + "," + user2.password());
            System.out.println(user2.toString());
        } catch (Exception e)
        {
            log.error("", e);

        }
        return "";
    }

    @RequestMapping(value = "/redis2")
    @ResponseBody
    public String redis2() {
        // 执行 lua 脚本
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        //        // 指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/setAndGet.lua")));
        //        // 指定返回类型
        redisScript.setResultType(String.class);
        //        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        String result = redisTemplate.execute(redisScript, Collections.singletonList("key1"), "value1");
        System.out.println(result);
        //        try
        //        {
        //            StringBuilder lua = new StringBuilder();
        //            var reader = new BufferedReader(new FileReader(new File("CLASSPATH:lua/setAndGet.lua")));
        //            String line = "";
        //            while ((line = reader.readLine()) != null)
        //            {
        //                lua.append(line);
        //            }
        //            //            redisTemplate.execute()
        //        } catch (IOException ex)
        //        {
        //            ex.printStackTrace();
        //        }
        return "";
    }

    @RequestMapping(value = "/token")
    @ResponseBody
    public String token1(@SessionAttribute(name = "token", required = false) String token, HttpSession session, HttpServletRequest request) {
        //        User user = userService.cookieTokenToUser(session);
        //        log.warn(user.toString());
        log.warn("token=" + token);
        if (token == null)
        {
            log.warn("session=" + session.getAttribute("token"));
            log.warn("request=" + Arrays.toString(request.getCookies()));
        }
        return "";
    }

}
