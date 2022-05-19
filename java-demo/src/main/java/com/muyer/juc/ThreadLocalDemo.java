package com.muyer.juc;

import java.awt.*;

/**
 * Description: 
 * date: 2022/5/18 11:31
 * @author YeJiang
 * @version
 */
public class ThreadLocalDemo {
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    static ThreadLocal<Point> threadLocal2 = new ThreadLocal<>();

    public static void main(String[] args) {
        //第一个线程
        new Thread(() -> {
            threadLocal.set("t1的信息");
            threadLocal2.set(new Point(1, 2));


            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Point p = threadLocal2.get();
            System.out.println("t1-----" + p);

            People people = (People) p;
            people.setAge("12");

            Point p2 = threadLocal2.get();
            People people2 = (People) p2;
            System.out.println("t1-----" + people2);


            threadLocal.remove();
        }, "t1").start();


        //第二个线程
        new Thread(() -> {
            threadLocal.set("t2的信息");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2-----" + threadLocal.get());
            threadLocal.remove();
        }, "t2").start();

        System.out.println("main-----" + threadLocal.get());

    }

    static class People extends Point {
        private String name;
        private String age;

        public People(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

}
