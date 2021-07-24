package com.muyer.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://blog.csdn.net/u014653854/article/details/80647013
 * 使用beforeExecute、afterExecute和terminated扩展ThreadPoolExecutor
 *
 * Description: 
 * date: 2021/7/23 10:43
 * @author YeJiang
 * @version
 */
public class MyThreadPoolFactory implements ThreadFactory {
    //线程池序号
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    //线程序号
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    //线程组
    private final ThreadGroup group;
    //线程名字前缀
    private final String namePrefix;


    //工厂的构造方法
    MyThreadPoolFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
    }

    //工厂生产一个线程
    @Override
    public Thread newThread(Runnable r) {
        String threadName = namePrefix + threadNumber.getAndIncrement();
        Thread t = new Thread(group, r, threadName, 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
