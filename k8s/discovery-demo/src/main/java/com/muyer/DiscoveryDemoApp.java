package com.muyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description:
 *
 *  1、maven引入了三个重要的包
 *      spring-cloud-starter-kubernetes
 *      spring-boot-starter-web
 *      spring-boot-starter-actuator
 *  2、注解@EnableDiscoveryClient 和 bean<DiscoveryClient>
 *      是Spring-cloud-k8s-core 提供的
 *
 *  3、spring.cloud.kubernetes.discovery.enabled=true配置
 *
 * date: 2021/8/30 11:00
 * @author YeJiang
 * @version
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryDemoApp.class, args);
    }
}
