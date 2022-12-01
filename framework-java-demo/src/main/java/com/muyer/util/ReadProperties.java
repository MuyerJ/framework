package com.muyer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Description:
 *  https://www.cnblogs.com/sebastian-tyd/p/7895182.html
 * date: 2021/6/2 14:47
 * @author YeJiang
 * @version
 */
public class ReadProperties {


    public static void main(String[] args) throws IOException {
        InputStream in = ReadProperties.class.getClassLoader().getResourceAsStream("application.properties");

        Properties map = new Properties();
        map.load(in);
        System.out.println(map.getProperty("scanPackage"));
    }
}
