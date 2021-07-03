package com.muyer.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.muyer.entity.Menu;
import com.muyer.entity.People;
import com.muyer.repository.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: 
 * date: 2021/6/20 16:13
 * @author YeJiang
 * @version
 */

@Controller
public class ThymeleafController {

    @Autowired
    private MenuDao menuDao;

    @RequestMapping("/sample")
    public String list(Model model) {
        model.addAttribute("msg", "<h5>hello yejiang</h5>");

        List<People> list = Lists.newArrayList(
                new People("yejiang1", "11", "男1"),
                new People("yejiang2", "12", "男2"),
                new People("yejiang3", "13", "男3"),
                new People("yejiang4", "14", "男4")
        );
        model.addAttribute("list", list);

        model.addAttribute("now", new Date());
        model.addAttribute("id", 3);

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


    @RequestMapping("/demo2")
    public String demo2(Model model) {
        return "demo2";
    }

    @RequestMapping("/table")
    public String table(Model model) {
        return "table";
    }

    @RequestMapping("/template")
    public String template(Model model) {
        return "template";
    }

    @RequestMapping("/layui")
    public String layui2(Model model) {
        return "layui";
    }

    @RequestMapping("/")
    public String main(Model model) {
        //封装结果
        Map<Long, Menu> treeMenu = new HashMap<>(16);
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setUrl("#");
        menu.setTitle("系统管理");
        Menu menu2 = new Menu();
        menu2.setId(2L);
        menu2.setUrl("/template");
        menu2.setTitle("菜单管理");
        HashMap<Long, Menu> map = Maps.newHashMap();
        map.put(menu2.getId(), menu2);
        menu.setChildren(map);
        treeMenu.put(menu.getId(), menu);
        model.addAttribute("treeMenu", treeMenu);
        return "main";
    }
}
