package com.muyer.springboot.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description: 
 * date: 2021/2/22 0:40
 * @author YeJiang
 * @version
 */
@Controller
public class HelloController {
    @GetMapping({"/",""})
    public String index(Model model){
        model.addAttribute("message","hello world");
        return "index";
    }
}
