package com.muyer.threadlocal;

/**
 * Description:
 * date: 2020/10/19 23:06
 *
 * @author MuyerJ
 */
public class ThreadDemo extends Thread {

    ThreadLocal<Long> threadLocal;

    public ThreadDemo(ThreadLocal<Long> threadLocal) {
        this.threadLocal = threadLocal;
    }

    @Override
    public void run() {
        Long result = threadLocal.get();
        if (result == null) {
            threadLocal.set(System.currentTimeMillis());
            System.out.println(Thread.currentThread().getName() + "===>" + threadLocal.get()+"===>"+threadLocal);
        }
    }
}
