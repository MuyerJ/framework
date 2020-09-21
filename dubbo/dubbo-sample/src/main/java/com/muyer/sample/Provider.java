package com.muyer.sample;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Description: <br/>
 * date: 2020/9/21 22:09<br/>
 *
 * @author MuyerJ<br   />
 */
public class Provider implements HelloDubbo {

    public void sayHello(String name) {
        System.out.println("hello..." + name);
    }

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        context.start();

        System.in.read();
    }
}
