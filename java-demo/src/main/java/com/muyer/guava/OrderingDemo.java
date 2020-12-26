package com.muyer.guava;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * date: 2020/12/25 10:25
 *
 * @author YeJiang
 */
public class OrderingDemo {

    public static void main(String[] args) {
        constructor();
        func();
    }

    private static void func() {
        ArrayList<Integer> list1 = Lists.newArrayList(4, 3, 6, 1, 9, 2, 100);
        System.out.println(Ordering.natural().reverse().sortedCopy(list1));

        //nullsFirst|nullsLast  :感觉没使用实现加入null到列表；
        List<String> list2 = Lists.newArrayList("jielun", "yejiang", "angle");
        System.out.println(Ordering.natural().nullsFirst().sortedCopy(list2));
        System.out.println(Ordering.natural().nullsLast().sortedCopy(list2));

        //compound
    }

    private static void constructor() {
        ArrayList<Integer> list = Lists.newArrayList(4, 3, 6, 1, 9, 2, 100);
        System.out.println(list);
        System.out.println(Ordering.natural().sortedCopy(list));
    }

}
