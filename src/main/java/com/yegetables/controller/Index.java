package com.yegetables.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index {
    @RequestMapping("/hello")
    public String index() {
        return "index1";
    }
}
