package com.muyer.demo.test_demo;

/**
 * Description:
 * date: 2020/12/14 10:08
 *
 * @author YeJiang
 */
public class ReturnTest {

    public void test1(){
        System.out.println("this is test1... before call test2");
        test2();
        System.out.println("this is test1... after call test2");
    }

    public void test2(){
        return;
    }

    public static void main(String[] args) {

    }
}
