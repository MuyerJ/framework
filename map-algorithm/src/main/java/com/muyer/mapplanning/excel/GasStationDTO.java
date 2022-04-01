package com.muyer.mapplanning.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Description: 
 * date: 2022/3/31 18:56
 * @author YeJiang
 * @version
 */
@Data
public class GasStationDTO {
    @ExcelProperty(value = "id")
    private long id;
    @ExcelProperty(value = "supplier_no")
    private String supplierNo;
    @ExcelProperty(value = "supplier_type")
    private String supplierType;
    @ExcelProperty(value = "product_no")
    private String productNo;
    @ExcelProperty(value = "product_name")
    private String productName;
    @ExcelProperty(value = "contract_phone")
    private String contractPhone;
    @ExcelProperty(value = "contract_name")
    private String contractName;
    @ExcelProperty(value = "remark")
    private String remark;
    @ExcelProperty(value = "lng")
    private double lng;
    @ExcelProperty(value = "lat")
    private double lat;
    @ExcelProperty(value = "detail_address")
    private String detailAddress;
    @ExcelProperty(value = "unit_price")
    private double unitPrice;
    @ExcelProperty(value = "effective_time")
    private long effectiveTime;
    @ExcelProperty(value = "status")
    private int status;
    @ExcelProperty(value = "is_delete")
    private Integer isDelete; // 是否删除 0 否 1 删除
    @ExcelProperty(value = "create_user")
    private String createUser;
    @ExcelProperty(value = "update_user")
    private String updateUser;
    @ExcelProperty(value = "create_time")
    private long createTime;
    @ExcelProperty(value = "update_time")
    private long updateTime;
    @ExcelProperty(value = "temp_license_price")
    private double tempLicensePrice;
    @ExcelProperty(value = "three_exceeding_price")
    private double threeExceedingPrice;
    @ExcelProperty(value = "compulsory_insurance_price")
    private double compulsoryInsurancePrice;
    @ExcelProperty(value = "price_ext_json")
    private String priceExtJson;
    @ExcelProperty(value = "settle_way")
    private String settleWay = "";
}
