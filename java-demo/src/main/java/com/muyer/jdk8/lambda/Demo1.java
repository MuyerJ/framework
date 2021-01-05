package com.muyer.jdk8.lambda;

import java.util.function.Consumer;

/**
 * Description:
 * date: 2020/12/28 18:16
 *
 * @author YeJiang
 */
public class Demo1 {
    public static void main(String[] args) {
        fun1();
    }

    /**
     *  什么是lambda表达式?
     *      一个没有名字的函数
     *  什么是函数式接口?
     *      只有一个抽象接口的函数
     *  jdk8四个默认的函数式接口
     *      Consumer
     *      Supplier
     *      Predicate
     *      Function
     *
     *      其他：
     *      BinaryOperator
     *
     */
    private static void fun1() {
        Consumer c = System.out::println;
        c.accept("yejiang...");

        Runnable run = ()->{
            System.out.println("hello ");
        };
        run.run();


    }
}
