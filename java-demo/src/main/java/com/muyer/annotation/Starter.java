package com.muyer.annotation;

/**
 * Description: 
 * date: 2022/5/27 14:31
 * @author YeJiang
 * @version
 */
public class Starter {
    public static void main(String[] args) {
        Father f1 = new KidA();
        Father f2 = new KidB();
        Father f3 = new KidC();
        System.out.println(f1.do1());
        System.out.println(f2.do1());
        System.out.println(f3.do1());
    }
}
