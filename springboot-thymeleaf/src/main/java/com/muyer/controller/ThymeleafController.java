package com.muyer.controller;

import com.google.common.collect.Lists;
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

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/template")
    public String template(Model model) {
        return "template";
    }

    @RequestMapping("/layui")
    public String layui2(Model model) {
        return "layui";
    }

    @RequestMapping("/main")
    public String main(Model model) {
        List<Menu> allMenus = menuDao.findAll();
        //排除已经删除的数据
        allMenus = allMenus.stream().filter(menu -> 1 == menu.getStatus()).collect(Collectors.toList());
        //转换为map
        Map<Long, Menu> keyMenu = allMenus.stream().collect(Collectors.toMap(Menu::getId, Function.identity()));
        //封装结果
        Map<Long, Menu> treeMenu = new HashMap<>(16);
//        keyMenu.forEach((id, menu) -> {
//            //不是按钮
//            if(!menu.getType().equals(3)){
//                //当前菜单项有父亲节点
//                if(keyMenu.get(menu.getPid()) != null){
//                    //将当前节点放在父亲节点的 childrenList里
//                    keyMenu.get(menu.getPid()).getChildren().put(Long.valueOf(menu.getSort()), menu);
//                }else{
//                    //当前菜单项是 目录，把当前菜单项放入目录结果里
//                    if(menu.getType().equals(1)){
//                        treeMenu.put(Long.valueOf(menu.getSort()), menu);
//                    }
//                }
//            }
//        });
        model.addAttribute("treeMenu", keyMenu);
        return "main";
    }
}
