package com.muyer.springcloud.quick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description: 
 * date: 2021/7/13 21:46
 * @author YeJiang
 * @version
 */
@SpringBootApplication
@EnableDiscoveryClient
public class QuickApp {
    public static void main(String[] args) {
        SpringApplication.run(QuickApp.class, args);
    }

}
