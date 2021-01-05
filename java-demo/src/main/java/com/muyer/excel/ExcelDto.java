package com.muyer.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * Description:
 * date: 2021/1/4 10:57
 *
 * @author YeJiang
 */
public class ExcelDto {
    @ExcelProperty(value = "所在市")
    private String end_city;
    @ExcelProperty(value = "地址代码")
    private String addr_code;

    public String getEnd_city() {
        return end_city;
    }

    public void setEnd_city(String end_city) {
        this.end_city = end_city;
    }

    public String getAddr_code() {
        return addr_code;
    }

    public void setAddr_code(String addr_code) {
        this.addr_code = addr_code;
    }
}
