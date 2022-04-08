package com.muyer.mapplanning.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Description: 
 * date: 2022/3/9 16:16
 * @author YeJiang
 * @version
 */
@Data
public class PointDTO {
    @ExcelProperty(value = "所在省份")
    private String province;
    @ExcelProperty(value = "所在城市")
    private String city;
    @ExcelProperty(value = "省内5A景区数量")
    private String count;
    @ExcelProperty(value = "景区名称")
    private String name;
    @ExcelProperty(value = "评定年份")
    private String year;
    @ExcelProperty(value = "经纬度")
    private String lngLat;
    @ExcelProperty(value = "油耗")
    private double oil;
}
