package com.muyer.juc.utility;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Description: 
 * date: 2021/2/27 14:53
 * @author YeJiang
 * @version
 */
public class CyclicBarrierDemo1 {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(7, () -> {
            System.out.println("七颗龙珠，召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println("发现一颗龙珠。。。" + finalI + "星龙珠");
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("----" + finalI);
            }, i + "星龙珠").start();
        }
        System.out.println("main退出。。");
    }
}
