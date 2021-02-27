package com.muyer.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Description: 
 * date: 2021/2/27 9:55
 * @author YeJiang
 * @version
 */
public class AtomicIntegerArrayDemo1 {
    public static void main(String[] args) {
        AtomicIntegerArray integerArray = new AtomicIntegerArray(10);//new int[length] 默认0
        integerArray.set(6, 10);
        System.out.println(integerArray);
        System.out.println("将第6元素设置为10：" + integerArray.get(6));
        System.out.println(integerArray.getAndSet(6, 5));
        System.out.println(integerArray);


        System.out.println(integerArray.compareAndSet(5, 1, 9));
        System.out.println(integerArray.compareAndSet(5, 0, 10));
        System.out.println(integerArray);

    }
}
