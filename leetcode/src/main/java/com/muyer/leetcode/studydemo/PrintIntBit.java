package com.muyer.leetcode.studydemo;

/**
 * Description: 
 * date: 2022/2/1 11:47
 * @author YeJiang
 * @version
 */
public class PrintIntBit {

    public static void main(String[] args) {
        printIntBit(1);
        printIntBit(2);
        printIntBit(3);

        //一个数左移1位 <==> 这个数乘以2
        printIntBit(1 << 1);
        printIntBit(2 << 1);
        printIntBit(3 << 1);

        //反码 和 补码

        //无符号：0 ~ 2^32-1  ==> 0 ~ 42亿多
        //有符号：-2^31 ~ 2^31-1  ==> 0 ~ 21亿多
        System.out.println(Integer.MAX_VALUE);
        printIntBit(Integer.MAX_VALUE);

        //负数 去掉符号位，其他取反+1
        printIntBit(-1);
        printIntBit(Integer.MIN_VALUE);

        //取反 ~
        int a = 12344;
        printIntBit(a);
        printIntBit(~a);


        //右移
        // 带符号右移 >>
        System.out.println("===== 带符号右移 >>");
        int b = 1024;
        printIntBit(b);
        printIntBit(b >> 1);

        printIntBit(Integer.MIN_VALUE);
        printIntBit(Integer.MIN_VALUE >> 1);


        // 不带符号右移 >>>
        System.out.println("===== 不带符号右移 >>>");
        printIntBit(b);
        printIntBit(b >>> 1);

        printIntBit(Integer.MIN_VALUE);
        printIntBit(Integer.MIN_VALUE >>> 1);


        //相反数 ： n  -n <==> (~n + 1)
        System.out.println("相反数");
        int c = 5;
        int d1 = -c;
        int d2 = (~c + 1);
        System.out.println(d1);
        System.out.println(d2);

        //最小值的相反数是自己
        System.out.println(Integer.MIN_VALUE);
        System.out.println(-Integer.MIN_VALUE);
        System.out.println(~Integer.MIN_VALUE + 1);


    }

    public static void printIntBit(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 31; i >= 0; i--) {
            int numLeftMove = num & 1 << i;
            sb.append(numLeftMove == 0 ? "0" : "1");
        }

        System.out.println(sb);
    }

}
