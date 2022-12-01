package com.muyer.juc.atomic;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: 
 * date: 2021/2/27 9:42
 * @author YeJiang
 * @version
 */
public class AtomicIntegerDemo {
    //    public static int i = 0;
    public static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) {
        List<Thread> list = Lists.newArrayList();
        for (int j = 0; j < 10000; j++) {
            Thread thread = new Thread(
                    () -> {
                        for (int k = 0; k < 100; k++) {
//                            i++;
                            i.getAndIncrement();
                        }
                    }
                    , "thread" + j
            );
            thread.start();
            list.add(thread);
        }

        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("最后值i=" + i);
    }

}
