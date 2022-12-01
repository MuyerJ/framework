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
    @ExcelProperty(value = "计算后的油钱")
    private double min;
    @ExcelProperty(value = "原来油钱")
    private double nomal;
    @ExcelProperty(value = "订单量")
    private int orderCount;
    @ExcelProperty(value = "总油耗")
    private double totalOil;

    public AvgPriceDTO() {
    }

    public AvgPriceDTO(String name, double min, double nomal, int orderCount, double totalOil) {
        this.name = name;
        this.min = min;
        this.nomal = nomal;
        this.orderCount = orderCount;
        this.totalOil = totalOil;
    }
}
