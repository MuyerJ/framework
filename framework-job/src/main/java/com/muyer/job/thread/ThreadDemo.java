package com.muyer.job.thread;

/**
 * Description:
 *  Thread方式实现Job
 * date: 2021/7/11 13:47
 * @author YeJiang
 * @version
 */
public class ThreadDemo {

    static long count = 0;

    public static void main(String[] args) {
        new Thread(getJobRunnable()).start();
    }

    public static Runnable getJobRunnable() {
        Runnable runnable = () -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    count++;
                    System.out.println(count);
                } catch (Exception e) {
                }
            }
        };

        return runnable;
    }
}
