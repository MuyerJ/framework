package com.muyer.demo.caculate_demo;

import java.util.Date;

/**
 * Description:
 * date: 2020/12/23 21:03
 *
 * @author YeJiang
 */
public class caculate {
    public static void main(String[] args) {
        test1();

    }
    /**
     * 两个long型相除，保留两位小数
     */
    private static void test1() {
        long time = new Date().getTime();
        double d = time * 1.4 / (double) time;
    }



}
