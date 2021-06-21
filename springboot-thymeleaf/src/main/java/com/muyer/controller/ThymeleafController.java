package com.muyer.controller;

import com.google.common.collect.Lists;
import com.muyer.model.People;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * Description: 
 * date: 2021/6/20 16:13
 * @author YeJiang
 * @version
 */

@Controller
public class ThymeleafController {

    @RequestMapping("/sample")
    public String list(Model model){
        model.addAttribute("msg","<h5>hello yejiang</h5>");

        List<People> list = Lists.newArrayList(
                new People("yejiang1","11","男1"),
                new People("yejiang2","12","男2"),
                new People("yejiang3","13","男3"),
                new People("yejiang4","14","男4")
        );
        model.addAttribute("list",list);

        model.addAttribute("now",new Date());
        model.addAttribute("id",3);

        return "sample";
    }


    @RequestMapping("/layout")
    public String layui(Model model) {
        return "layout";
    }

    @RequestMapping("/color")
    public String color(Model model) {
        return "color";
    }


    @RequestMapping("/demo")
    public String demo(Model model) {
        return "demo";
    }

    @RequestMapping("/layui")
    public String layui2(Model model) {
        return "layui";
    }
}
