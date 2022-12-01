package com.muyer.threadpool;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description: 
 * date: 2021/7/22 20:59
 * @author YeJiang
 * @version
 */
public class ThreadPoolExecutor implements Executor {
    private final AtomicInteger act = new AtomicInteger(0);
    //核心线程数
    private volatile int corePoolSize;
    //最大线程数
    private volatile int maxPoolSize;
    //任务阻塞队列
    private BlockingQueue<Runnable> workQueue;

    public ThreadPoolExecutor(int corePoolSize, int maxPoolSize, BlockingQueue<Runnable> workQueue) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.workQueue = workQueue;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, new LinkedBlockingQueue<>());
        threadPoolExecutor.execute(() -> System.out.println("测试1"));


    }

    @Override
    public void execute(Runnable command) {
        if (Objects.isNull(command)) {
            throw new NullPointerException();
        }
        int i = act.get();
        //任务数量小于核心线程数
        if (i < corePoolSize) {
            addWorker(command, true);
        } else if (workQueue.offer(command)) {
            addWorker(null, false);
        } else {
            //拒绝
        }
    }

    private void addWorker(Runnable runnable, Boolean isCore) {
        if (isCore) {
            act.incrementAndGet();
        }
        Worker worker = new Worker(runnable);
        worker.thread.start();

    }

    class Worker extends ReentrantLock implements Runnable {

        private Thread thread;
        private Runnable firstTask;

        public Worker(Runnable task) {
            this.firstTask = task;
            this.thread = new Thread(this);
        }

        @Override
        public void run() {
            try {
                this.lock();
                Runnable task = this.firstTask;
                if (task != null || (task = getTask()) != null) {
                    task.run();
                }
                System.out.println("测试2");
            } finally {
                this.unlock();
            }
        }

        private Runnable getTask() {
            if (workQueue == null) {
                return null;
            }
            try {

                return workQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
