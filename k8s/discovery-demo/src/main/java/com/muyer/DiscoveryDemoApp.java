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
 *  3、/instance?name接口可知：
 *      1) spring-cloud-starter-k8s能够知道当前namespace下所有的service
 *      2) 通过DiscoveryClient#getInstances(serviceName) 获取到服务的基本信息
 *
 *  4、配置参数 可参考：
 *      spring.cloud.kubernetes.discovery.enabled=true 配置
 *      KubernetesDiscoveryProperties
 *      https://blog.csdn.net/qq_32641153/article/details/97750791
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
