package com.muyer.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Description: 
 * date: 2021/6/1 14:10
 * @author YeJiang
 * @version
 */
public class GuavaTokenBuketLimit {

    //创建令牌桶
    private final static RateLimiter tokenBuketLimitter = RateLimiter.create(2);

    public static void main(String[] args) {
        GuavaTokenBuketLimit limit = new GuavaTokenBuketLimit();
        CountDownLatch downLatch = new CountDownLatch(1);
        Random random = new Random(10);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    downLatch.await();
                    Thread.sleep(random.nextInt(1000));
                    limit.tryGetToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        downLatch.countDown();
    }

    public void tryGetToken() {
        //尝试获取一个令牌
        if (tokenBuketLimitter.tryAcquire()) {
            System.out.println(Thread.currentThread().getName() + "正在操作，稍等...");
        } else {
            System.out.println("系统繁忙");
        }
    }
}
