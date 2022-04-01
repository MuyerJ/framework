package com.muyer.leetcode.util;

/**
 * Description: 
 * date: 2022/2/1 14:01
 * @author YeJiang
 * @version
 */
public class Utils {

    public static void swap(int[] arr, int indexA, int indexB) {
        int temp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = temp;
    }
}
