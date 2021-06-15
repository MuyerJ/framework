package com.muyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Description: 
 * date: 2021/6/14 18:44
 * @author YeJiang
 * @version
 */
@SpringBootApplication(scanBasePackages = {"com.muyer"})
public class JpaApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplication(JpaApp.class).run(args);

        System.out.println(context);

    }
}
