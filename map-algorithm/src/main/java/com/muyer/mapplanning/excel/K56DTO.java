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
public class K56DTO {
    @ExcelProperty(value = "order_no")
    private String orderNo;
    @ExcelProperty(value = "master_flag")
    private int masterFlag;
    @ExcelProperty(value = "start_truck_time")
    private long startTruckTime;
    @ExcelProperty(value = "return_truck_time")
    private long returnTruckTime;
    @ExcelProperty(value = "temp_license")
    private String tempLicense;
    @ExcelProperty(value = "customer_full_name")
    private String customerFullName;
    @ExcelProperty(value = "发车时间")
    private String t1;
    @ExcelProperty(value = "收车时间")
    private String t2;
}
