package com.muyer.docker.conctroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description: 
 * date: 2021/8/12 14:01
 * @author YeJiang
 * @version
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "Hello, SpringBoot With Docker";
    }
}
