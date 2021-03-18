package com.muyer.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * Description: 
 * date: 2021/3/18 15:00
 * @author YeJiang
 * @version
 */
public class ExcelDto2 {
    @ExcelProperty(value = "叶")
    private String x1;
    @ExcelProperty(value = "江")
    private String x2;

    public String getX1() {
        return x1;
    }

    public void setX1(String x1) {
        this.x1 = x1;
    }

    public String getX2() {
        return x2;
    }

    public void setX2(String x2) {
        this.x2 = x2;
    }
}
