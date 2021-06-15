package com.muyer.util;

import org.apache.commons.lang.RandomStringUtils;

import java.util.Random;

/**
 * Description: 
 * date: 2021/6/10 15:21
 * @author YeJiang
 * @version
 */
public class RandomUtils {

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {

            System.out.println(new Random(10).nextInt(10));
        }


        // Creates a 64 chars length random string of number.
        String result = RandomStringUtils.random(64, false, true);
        System.out.println("random = " + result);

        // Creates a 64 chars length of random alphabetic string.
        result = RandomStringUtils.randomAlphabetic(64);
        System.out.println("random = " + result);

        // Creates a 32 chars length of random ascii string.
        result = RandomStringUtils.randomAscii(32);
        System.out.println("random = " + result);

        // Creates a 32 chars length of string from the defined array of
        // characters including numeric and alphabetic characters.
        result = RandomStringUtils.random(2, 0, 20, true, true, "qw32rfHIJk9iQ8Ud7h0X".toCharArray());
        System.out.println("random = " + result);
    }
}
