package com.muyer.demo.threadlocal_demo;

/**
 * Description:
 * date: 2020/10/19 23:02
 *
 * @author MuyerJ
 */
public class ThreadLocalDemo {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal();

    public static void main(String[] args) {
        ThreadDemo t1 = new ThreadDemo(threadLocal);
        ThreadDemo t2 = new ThreadDemo(threadLocal);
        t1.start();
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        t2.start();

    }

}
