package com.muyer.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Description: 
 * date: 2021/2/25 11:57
 * @author YeJiang
 * @version
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        int[] src = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ForkJoinPool pool = new ForkJoinPool();
        Task1 task1 = new Task1(src);
        pool.invoke(task1);
        ForkJoinTask<Integer> fork = task1.fork();
        System.out.println("结果：" + fork.join());
//        System.out.println("结果：" + task1.join());
//        if(task1.isCompletedAbnormally()){
//            System.out.printf("Main: An exception has ocurred\n");
//            System.out.printf("Main: %s\n",task1.getException());
//        }
    }
}
