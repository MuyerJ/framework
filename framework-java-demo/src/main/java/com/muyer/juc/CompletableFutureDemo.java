package com.muyer.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Description: 
 * date: 2021/7/9 22:47
 * @author YeJiang
 * @version
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<CompletableFuture> list = new ArrayList<>();
        //3个
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        //3个
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        //3个
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        //3个
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        list.add(CompletableFuture.runAsync(() -> sleep2s()));
        CompletableFuture.allOf(list.toArray(new CompletableFuture[]{})).join();
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
