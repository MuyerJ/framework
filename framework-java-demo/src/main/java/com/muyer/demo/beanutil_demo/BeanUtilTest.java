package com.muyer.demo.beanutil_demo;

import org.springframework.beans.BeanUtils;

/**
 * Description:
 * date: 2020/11/10 17:50
 *
 * @author YeJiang
 */
public class BeanUtilTest {
    public static void main(String[] args) {
        A a = new A();
        a.setAge(11);
        a.setId("124");
        a.setName("yejiang");
        B b = new B();
        BeanUtils.copyProperties(a, b);
        System.out.println(a);
        System.out.println(b);

    }
}
