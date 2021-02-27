package com.muyer.juc.utility;

import java.util.concurrent.CountDownLatch;

/**
 * Description: 
 * date: 2021/2/27 14:23
 * @author YeJiang
 * @version
 */
public class CountDownLatchDemo {
    static CountDownLatch latch = new CountDownLatch(2);

    /**
     * 调用CountDownLatch的countDown方法时，N就会减1
     * 调用CountDownLatch的await方法会阻塞当前线程，直到N变成零
     *
     *
     *
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println(latch.getCount());
        new Thread(()->{
            latch.countDown();
            System.out.println(latch.getCount());
        }).start();
        new Thread(()->{
            latch.countDown();
            System.out.println(latch.getCount());
        }).start();
        latch.await();
        System.out.println("main 结束");

    }
}
