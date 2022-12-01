package com.muyer.job.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *  线程池方式实现job
 * date: 2021/7/11 13:57
 * @author YeJiang
 * @version
 */
public class ThreadPoolDemo {

    static long count = 0;

    public static void main(String[] args) {
        Executors.newSingleThreadScheduledExecutor().schedule(getTask(), 10, TimeUnit.SECONDS);
    }

    public static Runnable getTask() {
        Runnable task = () -> {
            count++;
            System.out.println(count);
        };
        return task;
    }
}
