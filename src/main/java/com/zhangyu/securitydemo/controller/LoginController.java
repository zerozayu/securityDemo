package com.zhangyu.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @PostMapping("/toMain")
    public String toMain(){
        return "redirect:/main.html";
    }

    @PostMapping("/toError")
    public String toError(){
        return "redirect:/error.html";
    }
}
