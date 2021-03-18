package com.muyer.collection;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Description: 
 * date: 2021/3/5 16:28
 * @author YeJiang
 * @version
 */
public class SetDemo {
    public static void main(String[] args) {
        testTraverse();
    }

    private static void testTraverse() {
        HashSet<String> set = new HashSet<>();
        set.add("yejiang1");
        set.add("yejiang2");
        set.add("yejiang3");
        set.add("yejiang4");
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
