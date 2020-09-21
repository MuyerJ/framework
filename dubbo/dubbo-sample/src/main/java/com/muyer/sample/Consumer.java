package com.muyer.sample;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Description: <br/>
 * date: 2020/9/21 22:24<br/>
 *
 * @author MuyerJ<br />
 */
public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("consumer.xml");
        c.start();
        HelloDubbo helloDubbo = (HelloDubbo) c.getBean("provider");
        helloDubbo.sayHello("yejiang");
    }
}
