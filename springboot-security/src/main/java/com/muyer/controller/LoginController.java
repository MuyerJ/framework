package com.muyer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "/login-success", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String loginRequest() {
        return "登录成功";
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

    //    @RequestMapping({"/", "/index"})
    @RequestMapping({"/index"})
    public String index(Model model) {
        model.addAttribute("msg", "index");
        return "index";
    }
}
