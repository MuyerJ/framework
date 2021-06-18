package com.muyer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description: 
 * date: 2021/6/18 23:17
 * @author YeJiang
 * @version
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);


//    @RequestMapping("/")
//    public String home() {
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("当前登陆用户：" + name);
//        return "home.html";
//    }

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("msg", "welcome you!");
        return "login";
    }


    @GetMapping("/test/login")
    public String login(){
        return "login";
    }

    @GetMapping("/admin")
    @ResponseBody
//    @PreAuthorize("hasRole('ROLE_admin')")
    public String printAdmin() {
        return "ROLE_admin角色";
    }

    @GetMapping("/user")
    @ResponseBody
//    @PreAuthorize("hasRole('ROLE_user')")
    public String printUser() {
        return "ROLE_user角色";
    }
}
