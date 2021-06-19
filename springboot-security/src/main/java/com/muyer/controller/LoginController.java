package com.muyer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description: 
 * date: 2021/6/19 3:14
 * @author YeJiang
 * @version
 */
@Controller
public class LoginController {

    @RequestMapping("/login-view")
    public String hello() {
        //这边我们,默认是返到templates下的login.html
        return "login";
    }

    @RequestMapping(value = "/login-success", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String loginRequest1() {
        return "login-success";
    }

    @RequestMapping(value = "/login-fail", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String loginRequest2() {
        return "login-fail";
    }

    @GetMapping(value = "/r/r1", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String r1() {
        return "访问资源r1";
    }


    @GetMapping(value = "/r/r2", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String r2() {
        return "访问资源r2";
    }

}
