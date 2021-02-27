package com.muyer.juc.utility;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * Description: 
 * date: 2021/2/27 15:41
 * @author YeJiang
 * @version
 */
public class CyclicBarrierDemo3 {

    static CyclicBarrier barrier = new CyclicBarrier(5, () -> {
        System.out.println("所有人游戏加载完成");
    });

    public static void main(String[] args) {
        List<String> heroList = Arrays.asList("黄忠", "马超", "关羽", "张飞", "赵云");
        for (int i = 0; i < 5; i++) {
            new Thread(new Hero(heroList.get(i),barrier)).start();
        }

        System.out.println("main 退出");

    }


    static class Hero implements Runnable {
        String name;
        CyclicBarrier barrier;

        public Hero(String name, CyclicBarrier barrier) {
            this.name = name;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("玩家" + name + "正在加载。。。");
                System.out.println("玩家" + name + "加载成功！");
                barrier.await();
                System.out.println("玩家" + name + "进入游戏");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
