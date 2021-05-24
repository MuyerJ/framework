package com.muyer.forkjoin;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

/**
 * Description: 
 * date: 2021/3/31 16:16
 * @author YeJiang
 * @version
 */
public class ForkJoinTest1 {
    public static void main(String[] args) {
        ArrayList<Object> lists = Lists.newArrayList();
        ForkJoinPool pool = lists.size() > 64 ? new ForkJoinPool(64) : new ForkJoinPool(1);
        try {
            pool.submit(() -> {
//                lists.parallelStream().forEach(dto -> markOftenCityCombinationOrderCityTag(oftenCities, dto));
            }).get();
        } catch (Exception e) {
        } finally {
            pool.shutdown();
        }
    }

    public static void merge(int[] a, int[] b, int i, int m, int n) {
        int j;
        int k = i;
        for (j = m + 1; i <= m && j <= n; k++) {
            if (a[i] < a[j]) {
                b[k] = a[i++];
            } else {
                b[k] = a[j++];
            }
        }
        while (i <= m) {
            b[k] = a[i];
            k++;
            i++;
        }
        while (j <= n) {
            b[k] = a[j];
            k++;
            j++;
        }
    }
}
