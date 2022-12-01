package com.muyer.juc.utility;

/**
 * Description: 
 * date: 2021/2/27 15:28
 * @author YeJiang
 * @version
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 屏障计数器可以重置
 */
public class CyclicBarrierDemo2 {
    static CyclicBarrier barrier = new CyclicBarrier(2, () -> {
        System.out.println("执行完成。。。");
    });

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "第一次到达屏障");
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "第二次到达屏障");
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "退出屏障");

            }, "thread" + i).start();
        }

        System.out.println("main 退出");
    }

}
