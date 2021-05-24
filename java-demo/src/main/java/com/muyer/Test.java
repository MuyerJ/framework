package com.muyer;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: 
 * date: 2021/3/8 17:02
 * @author YeJiang
 * @version
 */
public class Test {

    public static void main(String[] args) {
//        byte[] meLicenceTypeFirstLetter = "A3".getBytes();
//        byte[] herLicenceTypeFirstLetter = "A2".getBytes();
//        if (meLicenceTypeFirstLetter[0] > herLicenceTypeFirstLetter[0] ||
//                meLicenceTypeFirstLetter[0] == herLicenceTypeFirstLetter[0] && meLicenceTypeFirstLetter[1] < herLicenceTypeFirstLetter[1]) {
//        }
        List<Object> collect = Stream.of(null, Lists.newArrayList()).flatMap(Collection::stream).distinct().collect(Collectors.toList());

        System.out.println(1);
    }

    private static void Test1() {
        Map<String, List<String>> driverLicenseMap = Maps.newHashMap();
        driverLicenseMap.put("A1", Lists.newArrayList("1","2","3"));
        driverLicenseMap.put("A2",Lists.newArrayList("11","22","33"));
        driverLicenseMap.put("A3",Lists.newArrayList("111","222","333"));
        driverLicenseMap.put("B1",Lists.newArrayList("1111","2222","3333"));
        driverLicenseMap.put("B2",Lists.newArrayList("11111","22222","33333"));
        driverLicenseMap.put("B3",Lists.newArrayList("111111","222222","333333"));
        Map<String, List<String>> finalLicenseMap = Maps.newHashMapWithExpectedSize(9);
        List<String> licenseList = Lists.newArrayList("A1", "A2", "A3", "B1", "B2", "B3");
        for (int i = 0; i < licenseList.size(); i++) {
            List<String> canDriveOrderList = Lists.newArrayList();
            for (int j = i; j < licenseList.size(); j++) {
                //循环获取向下兼容的驾照订单列表
                List<String> orderDTOList = driverLicenseMap.get(licenseList.get(j));
                if (CollectionUtils.isNotEmpty(orderDTOList)) {
                    canDriveOrderList.addAll(orderDTOList);
                }
            }
            finalLicenseMap.put(licenseList.get(i), canDriveOrderList);
        }
        System.out.println(1);
    }
}
