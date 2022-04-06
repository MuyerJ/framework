package com.muyer.mapplanning.res;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 
 * date: 2022/4/1 11:05
 * @author YeJiang
 * @version
 */
@Data
public class GasStationDetail {
    //8.5元/升  ==>  1升能跑5000m
    static double _default_oil_price = 8;
    private String id;
    private String gasName;
    private String province;
    private String city;
    private String region;
    private double lng;
    private double lat;
    //0 代表正常加油站，1代表发车地，2代表收车地
    private int gasType = 0;
    private List<GasPriceDetail> priceList = Lists.newArrayList();

    public GasStationDetail() {
    }

    public GasStationDetail(String gasName, double lng, double lat, int gasType) {
        this.gasName = gasName;
        this.lng = lng;
        this.lat = lat;
        this.gasType = gasType;
    }

    public double getCheap() {

        if (gasType == 1) {
            return _default_oil_price;
        }

        if (gasType == 2) {
            return 0;
        }

        //长春基地的起点
        if (gasType == 3) {
            return 7.45;
        }

        //济宁基地的起点
        if (gasType == 4) {
            return 7.83;
        }

        //北京基地的起点
        if (gasType == 5) {
            return 8.1;
        }

        //济南基地的起点
        if (gasType == 6) {
            return 8.03;
        }

        //西安基地的起点
        if (gasType == 7) {
            return 8.06;
        }
        return priceList.stream().sorted(Comparator.comparing(GasPriceDetail::getVipPrice)).collect(Collectors.toList()).get(0).getVipPrice();
    }

}
