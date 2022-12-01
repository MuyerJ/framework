package com.muyer.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * Description: 
 * date: 2021/6/16 23:54
 * @author YeJiang
 * @version
 */
@Configuration
@ComponentScan(basePackages = "com.muyer.security",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class ApplicationConfig {
    //配置除controller以外的bean,db数据库连接池、事务管理器、业务bean
}
