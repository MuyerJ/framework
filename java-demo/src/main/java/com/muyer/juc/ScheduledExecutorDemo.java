package com.muyer.juc;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 *
 * date: 2021/8/4 10:00
 * @author YeJiang
 * @version
 */
public class ScheduledExecutorDemo {
    /* 初始化定时任务线程池 */
    private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);

    public static void main(String[] args) {


        /**
         * 参数说明：
         *  initialDelay : 第一次执行任务的延时时间
         *  period : 周期执行的间隔时间
         *
         * #scheduleAtFixedRate :
         *          initialDelay后执行一次，每隔period再次执行
         *
         * #scheduleWithFixedDelay :
         *          initialDelay后执行一次，随后每次任务执行完延时delay后再次执行
         *
         * #schedule :
         *          initialDelay后，只执行一次
         *
         */


        //scheduleAtFixedRate();

        scheduleWithFixedDelay();

        //schedule();


    }

    private static void scheduleAtFixedRate() {
        Runnable command = () -> {
            Date date = new Date();
            System.out.println(date + "定时说Yes1");
            try { TimeUnit.SECONDS.sleep(6); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(date + "定时说Yes2");
        };
        scheduledThreadPool.scheduleAtFixedRate(command, 0, 5, TimeUnit.SECONDS);
    }

    private static void scheduleWithFixedDelay() {
        Runnable command = () -> {
            Date date = new Date();
            System.out.println(date + "定时说NO1");
            try { TimeUnit.SECONDS.sleep(6); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(date + "定时说NO2");
        };
        scheduledThreadPool.scheduleWithFixedDelay(command, 0, 5, TimeUnit.SECONDS);
    }

    private static void schedule() {
        Runnable command = () -> System.out.println("定时说hello");
        scheduledThreadPool.schedule(command, 30, TimeUnit.SECONDS);
    }
}
