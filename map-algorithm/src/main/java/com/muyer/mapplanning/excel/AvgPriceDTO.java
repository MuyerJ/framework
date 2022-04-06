package com.muyer.mapplanning.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Description: 
 * date: 2022/4/5 19:58
 * @author YeJiang
 * @version
 */
@Data
public class AvgPriceDTO {
    @ExcelProperty(value = "路线名称")
    private String name;
    @ExcelProperty(value = "必经一个团油站最油钱")
    private double min;
    @ExcelProperty(value = "原来油钱")
    private double nomal;

    public AvgPriceDTO() {
    }

    public AvgPriceDTO(String name,double min, double nomal) {
        this.name = name;
        this.min = min;
        this.nomal = nomal;
    }
}
