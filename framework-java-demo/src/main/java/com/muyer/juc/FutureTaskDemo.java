package com.muyer.juc;

import java.util.concurrent.*;

/**
 * Description: 
 * date: 2022/5/18 10:18
 * @author YeJiang
 * @version
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws Exception {
        //Thread 搭配 FutureTask
        FutureTask<Long> future = new FutureTask(new ReturnCallable());
        Thread thread = new Thread(future);
        thread.start();
        System.out.println(future.get());
        //线程池 搭配 FutureTask
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        FutureTask<Long> future2 = new FutureTask(new ReturnCallable());
        executorService.submit(future2);
        System.out.println(future2.get());
        executorService.shutdownNow();
    }

    static class ReturnCallable implements Callable<Long> {

        @Override
        public Long call() throws Exception {
            for (int i = 0; i < 5; i++) {
                System.out.println("执行 " + i);
                Thread.sleep(500);
            }
            return 10L;
        }
    }
}
