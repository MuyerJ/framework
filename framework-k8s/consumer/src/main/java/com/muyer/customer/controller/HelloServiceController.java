package com.muyer.customer.controller;


import com.alibaba.fastjson.JSON;
import com.muyer.customer.client.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Description: 
 * date: 2021/8/17 10:14
 * @author YeJiang
 * @version
 */
@RestController
public class HelloServiceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ProviderClient providerClient;

    /**
     * 探针检查响应类
     * @return
     */
    @RequestMapping("/health")
    public String health() {
        return "health";
    }

    /**
     * 返回远程调用的结果
     * @return
     */
    @RequestMapping("/getservicedetail")
    public String getservicedetail(
            @RequestParam(value = "servicename", defaultValue = "") String servicename) {
        return "Service [" + servicename + "]'s instance list : " + JSON.toJSONString(discoveryClient.getInstances(servicename));
    }

    /**
     * 返回发现的所有服务
     * @return
     */
    @RequestMapping("/services")
    public String services() {
        return this.discoveryClient.getServices().toString()
                + ", "
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @GetMapping("/hello")
    public String hello() {
        return providerClient.hello();
    }

    @GetMapping("/hello2")
    public String hello2() {
        return providerClient.hello2();
    }

}
