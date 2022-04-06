package com.muyer.mapplanning;

import lombok.Data;

import java.io.*;
import java.util.regex.Matcher;

/**
 * Description: 
 * date: 2022/4/4 18:51
 * @author YeJiang
 * @version
 */
@Data
public class DistanceDTO {
    private String key;
    private double distance;

    public DistanceDTO() {
    }

    public DistanceDTO(String key, double distance) {
        this.key = key;
        this.distance = distance;
    }

    public static void main(String[] args) throws Exception {

        //写数据
        File file = new File("F:" + File.separator + "distance.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        //加true是追加，不加true就是覆盖了
        Writer writer = new FileWriter(file, true);
        writer.write("111.11&32.11#113.11&32.1,1111111\n");
        writer.write("111.11&32.11#113.11&32.1,2222\n");
        writer.close();

        //读数据
        Reader reader = new FileReader("F:" + File.separator + "distance.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr = "";
        while ((lineStr = bufferedReader.readLine()) != null) {
            String[] split = lineStr.split(",");
            String key = split[0];
            double distance = Double.parseDouble(split[1]);
            DistanceDTO distanceDTO = new DistanceDTO(key, distance);
            System.out.println(distanceDTO);
        }
        bufferedReader.close();
        reader.close();

    }

}
