package com.muyer.mapplanning.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.google.common.collect.Lists;
import com.muyer.mapplanning.GasStationMain;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static void main(String[] args) {
        GasStationMain.writeLineExcel();
    }
}
