package com.muyer;

/**
 * Description: 
 * date: 2021/3/8 17:02
 * @author YeJiang
 * @version
 */
public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
               if(j<i){
                   System.out.println(j + ":" + i);
               }
            }
        }
    }
}
