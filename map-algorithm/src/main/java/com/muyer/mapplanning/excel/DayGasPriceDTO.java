package com.muyer.mapplanning.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Description: 
 * date: 2022/5/9 10:27
 * @author YeJiang
 * @version
 */
@Data
public class DayGasPriceDTO {
    @ExcelProperty(value = "油站名称")
    private String gasName;
    @ExcelProperty(value = "省")
    private String province;
    @ExcelProperty(value = "市")
    private String city;
    @ExcelProperty(value = "区")
    private String region;
    @ExcelProperty(value = "0# 油枪价格")
    private double gunPrice;
    @ExcelProperty(value = "0# 优惠价格")
    private double vipPrice;
}
