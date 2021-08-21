package com.muyer.customer.controller;


import com.muyer.customer.client.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/service")
    public Object getClient() {
        return discoveryClient.getServices();
    }

    @GetMapping("/instance")
    public List<ServiceInstance> getInstance(String instanceId) {
        return discoveryClient.getInstances(instanceId);
    }

    @GetMapping("/ping")
    public String ping() {
        return providerClient.ping();
    }

}
