package com.muyer.mapplanning.res;

import lombok.Data;

import java.util.List;

/**
 * Description: 
 * date: 2022/4/1 17:30
 * @author YeJiang
 * @version
 */
@Data
public class Position {

    private double lng;
    private double lat;
    private List<GasStationDetail> nearStationList;

    public static void main(String[] args) {
        //1km -> 135 ==> 105 ==> 有25个重复的网点
        System.out.println(10 + 28 + 0 + 7 + 4 + 2 + 34 + 12 + 30 + 8);
        //2km -> 155 ==> 208 ==> 有25个重复的网点
        System.out.println(18 + 45 + 0 + 8 + 8 + 5 + 46 + 22 + 42 + 14);
    }

    public String getLngLat() {
        return "[" + lng + "," + lat + "]";
    }
}
