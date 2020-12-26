package com.muyer.demo.stream_demo;

import java.util.Optional;

/**
 * Description:
 * date: 2020/12/24 17:37
 *
 * @author YeJiang
 */
public class Test_Optional {
    public static void main(String[] args) {
        func1();
    }

    private static void func1() {
        Optional<String> op = Optional.of("yejiang");

        System.out.println(op.isPresent());

        //方法1
        op.ifPresent(o->{
            System.out.println(op.get());
        });
        //方法2
        op.ifPresent(System.out::println);
    }
}
