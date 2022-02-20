package com.yegetables.controller;


import com.yegetables.utils.PropertiesConfig;
import com.yegetables.utils.StringTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        log.warn("max=" + PropertiesConfig.getNameMaxLength());
        log.warn("emailRegex=" + PropertiesConfig.getEmailRegex());
        return "";
    }


}
