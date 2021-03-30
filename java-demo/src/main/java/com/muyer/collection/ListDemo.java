package com.muyer.collection;

import com.google.common.collect.Lists;
import com.muyer.demo.stream_demo.People;

import java.util.ArrayList;

/**
 * Description: 
 * date: 2021/3/21 23:05
 * @author YeJiang
 * @version
 */
public class ListDemo {
    public static void main(String[] args) {
        testCopyOf();
    }

    private static void test0() {
        ArrayList<People> list = Lists.newArrayListWithCapacity(10);
        People people = new People();
        people.setAge(0);
        list.add(people);
        System.out.println(list.get(0).getAge());
        people.setAge(11);
        System.out.println(list.get(0).getAge());
    }

    /**
     * 数组扩容
     */
    public static void testCopyOf() {
        int[] arr1 = new int[]{1, 2, 3, 4};
        int[] arr2=new int[arr1.length*2];
        System.arraycopy(arr1,0,arr2,0,arr1.length);
        arr1 = arr2;
        System.out.println(arr1.length);
        for (int i = 0; i < arr1.length; i++) {
            System.out.println(arr2[i]);
        }
    }
}
