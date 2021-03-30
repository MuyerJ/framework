package com.muyer;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 
 * date: 2021/3/8 17:02
 * @author YeJiang
 * @version
 */
public class Test {

    public static void main(String[] args) {
        List<Integer> collect = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9).stream().filter(i -> {
            if (i == 1) {
                return false;
            }
            System.out.println(i);
            return true;
        }).collect(Collectors.toList());

    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        //产生随机数范围为[0,maxSize]
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            //产生[-maxValue,maxValue]的元素
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }
}
