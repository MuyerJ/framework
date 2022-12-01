package com.muyer.mapplanning.excel;

import lombok.Data;

/**
 * Description: 
 * date: 2022/3/9 16:16
 * @author YeJiang
 * @version
 */
@Data
public class GasOilPriceDTO {
    //油品Id
    private int oilNo;
    //油品名称
    private String oilNoName;
    //优惠价 这个为实际下单计算金额
    private double priceVip;
    //枪价
    private double priceGun;
    //国标价 发改委/官方指导价
    private double priceOfficial;
    //油品类型 1 汽油； 2 柴油； 3 天然气；
    private int oilType;
    //油品类型名称  1 汽油； 2 柴油； 3 天然气；
    private String oilTypeName;

}
