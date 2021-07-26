package com.muyer.threadpool;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description: 
 * date: 2021/7/25 16:38
 * @author YeJiang
 * @version
 */
public class MonitorThreadPoolUtil {

    private ThreadPoolExecutor threadPoolExecutor;
    private int seconds;
    private volatile boolean run = true;


    public MonitorThreadPoolUtil(ThreadPoolExecutor threadPoolExecutor, int delay) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.seconds = delay;
    }


    public void shutDown() {
        this.run = false;
    }

    public void run() {
        while (run) {

            if (this.threadPoolExecutor.isTerminated()) {
                System.out.println("任务已经执行完毕");
                break;
            }
            System.out.println(
                    String.format("[monitor] 线程池大小:%d,核心数:%d,活跃数:%d,完成数:%d,任务数:%d",
                            this.threadPoolExecutor.getPoolSize(),
                            this.threadPoolExecutor.getCorePoolSize(),
                            this.threadPoolExecutor.getActiveCount(),
                            this.threadPoolExecutor.getCompletedTaskCount(),
                            this.threadPoolExecutor.getTaskCount())
            );

            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
