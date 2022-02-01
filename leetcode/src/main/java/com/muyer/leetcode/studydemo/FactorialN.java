package com.muyer.leetcode.studydemo;

/**
 * Description: 
 * date: 2022/2/1 13:30
 * @author YeJiang
 * @version
 */
public class FactorialN {

    public static void func1(int n) {
        if (n <= 0) {
            System.out.println("输入的数必须大于0");
        }
        //处理每一个 1  2  3  ... n
        int total = 0;
        for (int i = 1; i <= n; i++) {
            //单独算 1*2*..*i
            total = total + sumNum(i);
        }

        System.out.println(total);
    }

    private static int sumNum(int n) {
        int sum = 1;
        for (int i = 1; i <= n; i++) {
            sum = sum * i;
        }
        return sum;
    }

    /**
     * 1 1
     * 2 1 1+2 = 3
     * 3 1 1+2 3+3*3 = 12
     * 3 1 1+2 12
     *
     *
     * @param n
     */
    public static void func2(int n) {
        if (n <= 0) {
            System.out.println("输入的数必须大于0");
        }
        //处理每一个 1  2  3  ... n
        int total = 0;
        int factorialInit = 1;
        for (int i = 1; i <= n; i++) {
            factorialInit = factorialInit * i;
            total = total + factorialInit;
        }

        System.out.println(total);
    }

    public static void main(String[] args) {
        func1(1);
        func1(2);
        func1(3);
        func1(4);
        System.out.println("=====");
        func2(1);
        func2(2);
        func2(3);
        func2(4);
    }
}
