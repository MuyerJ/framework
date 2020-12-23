package com.muyer.stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * date: 2020/12/23 10:16
 *
 * @author YeJiang
 */
public class Test1 {
    static List<People> ids = new ArrayList<>();

    private static void init() {
        ids.add(new People("yejiang1"));
        ids.add(new People("yejiang2"));
        ids.add(new People("yejiang3"));
        ids.add(new People("yejiang4"));
    }

    public static void main(String[] args) {
        init();
        fun1();
    }

    /**
     *  拼接收集器：以逗号拼接
     */
    private static void fun1() {
        String str = ids.stream().map(People::getName).collect(Collectors.joining("|"));
        System.out.println(str);
    }
    /**
     * 1.List 去重
     *     .stream().distinct().collect(Collectors.toList());//去重
     */
    private static void fun2() {

    }

    /**
     * 2.双重循环判断list重复元素
     *      for (int i = 0; i <list.size() ; i++) {
     *         for (int j = i+1; j <list.size() ; j++) {
     *             System.out.println(list.get(i)+"----------------"+list.get(j));
     *         }
     *      }
     *
     */

}
