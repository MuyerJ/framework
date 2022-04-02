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

    public String getLngLat() {
        return "[" + lng +","+ lat + "]";
    }
}
