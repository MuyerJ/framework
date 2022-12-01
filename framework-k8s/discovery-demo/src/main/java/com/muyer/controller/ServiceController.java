package com.muyer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: 
 * date: 2021/8/30 10:56
 * @author YeJiang
 * @version
 */
@RestController
public class ServiceController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/service")
    public List<String> getServiceList(){
        return discoveryClient.getServices();
    }

    @GetMapping("/instance")
    public Object getInstance(@RequestParam("name") String name){
        return discoveryClient.getInstances(name);
    }
}
