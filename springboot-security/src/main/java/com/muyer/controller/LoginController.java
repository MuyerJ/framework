package com.muyer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description: 
 * date: 2021/6/18 23:17
 * @author YeJiang
 * @version
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping("/")
    public String home() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("当前登陆用户：" + name);
        return "home.html";
    }


    @RequestMapping("/login")
    public String login(){
        return "login.html";
    }


}
