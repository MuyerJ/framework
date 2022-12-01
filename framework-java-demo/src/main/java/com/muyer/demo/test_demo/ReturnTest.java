package com.muyer.demo.test_demo;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * date: 2020/12/14 10:08
 *
 * @author YeJiang
 */
public class ReturnTest {

    public void test1(){
        System.out.println("this is test1... before call test2");
        test2();
        System.out.println("this is test1... after call test2");
    }

    public void test2(){
        return;
    }

    public static void main(String[] args) {
//        for (int i = 0; i < driverLiceseTypes.size(); i++) {
//            System.out.println(driverLiceseTypes.get(i));
//        }
        System.out.println(BackwardDriverLicese("A3"));
        System.out.println(BackwardDriverLicese("B3"));
        System.out.println(BackwardDriverLicese("B1"));
        System.out.println(BackwardDriverLicese("B2"));
    }

    private static List<String> BackwardDriverLicese(String type) {
        ArrayList<String> result = Lists.newArrayList();
        ArrayList<String> driverLiceseTypes = Lists.newArrayList("A1", "A2", "A3", "B1", "B2", "B3","C1", "C2", "C3");
        int index = driverLiceseTypes.indexOf(type);
        for (int i = 0; i < driverLiceseTypes.size(); i++) {
            if (i >= index) {
                result.add(driverLiceseTypes.get(i));
            }
        }
        return result;
    }

    public static void fun1(String driverLicenceTypeStr) {
        final List<String> driverLicenceTypeList = Lists.newArrayListWithCapacity(4);
        final char[] chars = driverLicenceTypeStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            if (c == 'A') {
                driverLicenceTypeList.add(String.valueOf(c) + String.valueOf(chars[i + 1]));
            } else if (c == 'B') {
                driverLicenceTypeList.add(String.valueOf(c) + String.valueOf(chars[i + 1]));
            } else if (c == 'C') {
                driverLicenceTypeList.add(String.valueOf(c) + String.valueOf(chars[i + 1]));
            }
        }
        System.out.println(driverLicenceTypeList);
    }
}
