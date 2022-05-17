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
public class AliyunLogDTO {
    @ExcelProperty(value = "log")
    private String log;
}
