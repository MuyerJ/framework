package com.muyer.forkjoin;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Description: 
 * date: 2021/3/26 18:00
 * @author YeJiang
 * @version
 */
public class ForkJoinTemplate {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(64);
        try {
            pool.submit(() -> {
                Lists.newArrayListWithCapacity(100).parallelStream().forEach(obj->{
                    System.out.println("做点什么。。。");
                });
            }).get();
        } catch (Exception e) {
            System.out.println("打印点什么。。。");
        } finally {
            pool.shutdown();
        }
    }
}
