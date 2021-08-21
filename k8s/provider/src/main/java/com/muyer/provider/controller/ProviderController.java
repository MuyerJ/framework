package com.muyer.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Description: 
 * date: 2021/8/19 9:33
 * @author YeJiang
 * @version
 */
@RestController
public class ProviderController {

    @GetMapping("/ping")
    @ResponseBody
    public String ping() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "Pong";
        }
    }
}
