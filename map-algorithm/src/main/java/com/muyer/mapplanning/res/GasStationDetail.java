package com.muyer.mapplanning.res;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Description: 
 * date: 2022/4/1 11:05
 * @author YeJiang
 * @version
 */
@Data
public class GasStationDetail {
    private String gasName;
    private String province;
    private String city;
    private String region;
    private double lng;
    private double lat;
    private List<GasPriceDetail> priceList = Lists.newArrayList();
}
