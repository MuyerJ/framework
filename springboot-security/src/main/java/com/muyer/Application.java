package com.muyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description: 
 * date: 2021/6/18 23:24
 * @author YeJiang
 * @version
 */

@SpringBootApplication(scanBasePackages = {"com.muyer"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
