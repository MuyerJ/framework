package com.muyer.juc;

/**
 * Description: 
 * date: 2022/5/18 11:31
 * @author YeJiang
 * @version
 */
public class ThreadLocalDemo {
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        //第一个线程
        new Thread(() -> {
            threadLocal.set("t1的信息");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1-----" + threadLocal.get());
            threadLocal.remove();
        }, "t1").start();


        //第二个线程
        new Thread(() -> {
            threadLocal.set("t2的信息");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2-----" + threadLocal.get());
            threadLocal.remove();
        }, "t2").start();

        System.out.println("main-----" + threadLocal.get());

    }
}
