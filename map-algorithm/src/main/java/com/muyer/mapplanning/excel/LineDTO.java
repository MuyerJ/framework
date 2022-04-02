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
public class LineDTO {
    @ExcelProperty(value = "起点")
    private String start;
    @ExcelProperty(value = "终点")
    private String end;
    @ExcelProperty(value = "划分")
    private String partition;
    @ExcelProperty(value = "经纬度")
    private String latLng;
}
