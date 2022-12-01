package com.muyer.demo.stream_demo;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * date: 2020/12/23 10:16
 *
 * @author YeJiang
 */
public class Test_list {
    static List<People> ids = new ArrayList<>();

    private static void init() {
        ids.add(new People("yejiang1"));
        ids.add(new People("yejiang2"));
        ids.add(new People("yejiang3"));
        ids.add(new People("yejiang4"));
    }

    public static void main(String[] args) {
        init();
        fun2();
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
        ArrayList<Integer> list = Lists.newArrayList(1, null, 2, 3, 4, 5, 6, null);
        list.stream().filter(o->o!=null).forEach(System.out::println);
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

    /**
     * list.stream().mapToDouble求最大、最小、和、平均值
     *  list.stream().mapToDouble(User::getAge).sum()//和
     *  list.stream().mapToDouble(User::getAge).max()//最大
     *  list.stream().mapToDouble(User::getAge).min()//最小
     *  list.stream().mapToDouble(User::getAge).average()//平均值
     */

    /**
     * 获取最新时间
     *  Optional<Date> maxDate = list.stream()
     *      .max((p1,p2) -> p1.getDate().compareTo(p2.getDate()))
     *      .map(object -> object.getDate());
     *   Date date = maxDate.get();
     *
     *
     */

}
