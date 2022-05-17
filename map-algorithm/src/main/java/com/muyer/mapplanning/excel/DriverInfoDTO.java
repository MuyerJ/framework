package com.muyer.mapplanning.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Description: 
 * date: 2022/5/12 14:29
 * @author YeJiang
 * @version
 */
@Data
public class DriverInfoDTO {
    @ExcelProperty(value = "司机Id")
    private String id;
    @ExcelProperty(value = "司机姓名")
    private String name;
    @ExcelProperty(value = "司机手机号")
    private String phone;
}
