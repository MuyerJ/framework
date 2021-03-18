package com.muyer.io;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 
 * date: 2021/3/6 16:53
 * @author YeJiang
 * @version
 */
public class IODemo {

    static Pattern pattern = Pattern.compile(", orderNo=OR\\d*");

    public static void main(String[] args) throws Exception {
        //读数据
        StringBuffer sb = new StringBuffer("");
        Reader reader = new FileReader("F:" + File.separator + "new 2.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr = "";
        String resultStr = null;
        int i = 0;
        while ((lineStr = bufferedReader.readLine()) != null) {
            Matcher matcher = pattern.matcher(lineStr);
            String value = null;
            while (matcher.find()) {
                value = matcher.group().replace(", ","").trim();
                resultStr = resultStr + value.split("=")[1] + "\r\n";
                i++;
            }
        }
        bufferedReader.close();
        reader.close();


        System.out.println(i);


        //写数据
        File file = new File("F:" + File.separator + "out3.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        //加true是追加，不加true就是覆盖了
        Writer writer = new FileWriter(file, true);
        writer.write(resultStr);
        writer.close();
    }

    public static void test1(){

    }
}
