package com.muyer.guava;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 * date: 2020/12/25 14:09
 *
 * @author YeJiang
 */
public class Jdk8OrderingDemo {

    public static void main(String[] args) {
        jdk8Compare();
    }

    /**
     * Comparator.comparing()
     * 两个参数：
     * 第一个参数：代表哪个字段要用来比比较
     * 第二个参数：参数用来表示要如何比较
     * 两种模式
     * 一个参数的：自然排序比较
     * 两个参数的：自定义比较的方法
     */
    private static void jdk8Compare() {
        List<String> list1 = Lists.newArrayList("Ram", "Shyam", "Mohan", "Sohan",
                "Ramesh", "Sureshxxxxx", "Naresh", "Aahesh", null, "", "Vikas", "Deepak");
        ArrayList<Integer> list2 = Lists.newArrayList(4, 3, 6, 1, 9, 2, 100);
        ArrayList<Student> list3 = Lists.newArrayList(
                new Student("yejiang1", 100),
                new Student("yejiang2", 99),
                new Student("yejiang2", 100),
                new Student("yejiang3", 100),
                new Student("yejiang3", 99),
                new Student("yejiang4", 10),
                new Student("yejiang4", 100)
        );
        /**
         *  传递1个参数
         * 比较普通数字类型的用自然排序
         *  1.自然逆序
         *  2.自然顺序
         */
        list2.sort(Comparator.reverseOrder());
        System.out.println(list2);
        list2.sort(Comparator.naturalOrder());
        System.out.println(list2);

        /**
         * 传递2个参数
         * eg1.按照字符串长度比较
         * eg2.按照年龄
         */
        list1.sort(Comparator.comparing(str -> Optional.ofNullable(str).orElse("").length()));
        System.out.println(list1);
//        list3.sort(Comparator.comparing(Student::getScore,(s1,s2)->{
//            return s1-s2;
//        }));
        list3.sort(Comparator.comparing(Student::getScore, Comparator.comparingInt(s -> s)));
        System.out.println("list3 : "+list3);

    }



}
