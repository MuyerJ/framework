package com.muyer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: <br/>
 * date: 2020/8/14 0:53<br/>
 *
 * @author MuyerJ<br />
 */
@RestController
public class ProviderController8001 {

    @GetMapping(value = "/provider/hello")
    public String helloProvider8001(){
        return "helloProvider8001";
    }
}
