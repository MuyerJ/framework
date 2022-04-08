package com.muyer.mapplanning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

/**
 * Description: 
 * date: 2022/4/5 12:32
 * @author YeJiang
 * @version
 */
public class HotRouteMain {
    public static void main(String[] args) throws Exception {
        String pre = "select CONCAT(`start_city`,'-', `end_city` ,'&', `start_lat` ,',', `start_lng` ,'&', `end_lat` ,',', `end_lng` ) s from `t_waybill` WHERE  `create_time` > '20220101000000' and ";
        String suffix = " LIMIT 1 ;";
        Reader reader = new FileReader("F:\\团油数据\\热点路线-西安青岛长春.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr = "";
        while ((lineStr = bufferedReader.readLine()) != null) {
            String[] split = lineStr.split("-");
            System.out.println(pre + "`start_city` = '" + split[0] + " ' and `end_city` = '" + split[1] + "'"+suffix);
        }
        bufferedReader.close();
        reader.close();

    }
}
