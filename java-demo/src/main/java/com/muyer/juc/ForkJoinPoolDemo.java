package com.muyer.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Description: 
 * date: 2021/7/9 23:01
 * @author YeJiang
 * @version
 */
public class ForkJoinPoolDemo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(9);
        List<Runnable> list = new ArrayList<>();
        //3个
        list.add(() -> sleep2s());
        list.add(() -> sleep2s());
        list.add(() -> sleep2s());
        //3个
        list.add(() -> sleep2s());
        list.add(() -> sleep2s());
        list.add(() -> sleep2s());
        //3个
        list.add(() -> sleep2s());
        list.add(() -> sleep2s());
        list.add(() -> sleep2s());
        try {
            pool.submit(() -> list.parallelStream().forEach(Runnable::run)).get();
        } catch (Exception e) {
        } finally {
            pool.shutdown();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }


    public static void sleep2s() {

        System.out.println(1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
