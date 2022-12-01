package com.muyer.juc.utility;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description: 
 * date: 2021/2/27 15:56
 * @author YeJiang
 * @version
 */
public class ExchangerDemo1 {

    static Exchanger<String> exchanger = new Exchanger<>();

    static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        executorService.execute(() -> {
            String A = "A";
            try {
                String s = exchanger.exchange(A);
                System.out.println("A获取的是：" + s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.execute(() -> {
            String B = "B";
            try {
                String s = exchanger.exchange(B);
                System.out.println("B获取的是：" + s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
}
