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
public class KaiPiaoDTO {
    @ExcelProperty(value = "tag")
    private String tag;
    @ExcelProperty(value = "订单号")
    private String orderNo;
}
