package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.muyer.mapplanning.excel.AliyunLogDTO;
import com.muyer.mapplanning.excel.AvgPriceDTO;
import com.muyer.mapplanning.excel.DriverInfoDTO;
import com.muyer.mapplanning.excel.KaiPiaoDTO;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: 
 * date: 2022/4/5 12:32
 * @author YeJiang
 * @version
 */
public class AliyunLogMain {

    public static void main(String[] args) throws Exception {
        List<AliyunLogDTO> list = EasyExcel.read(new File("F:\\15天用小程序已签约的司机清单.xls")).autoTrim(true)
                .head(AliyunLogDTO.class).sheet().doReadSync();


        List<DriverInfoDTO> driverInfoList = Lists.newArrayList();
        Set<String> idSet = Sets.newHashSet();
        for (AliyunLogDTO dto : list) {
            String[] driverInfoArray = dto.getLog().split("data=DriverInfoDto\\(")[1].split(",");
            String id = driverInfoArray[0];
            String name = driverInfoArray[1];
            String phone = driverInfoArray[2];
            String driverId = id.split("=")[1];
            if (idSet.contains(driverId)) {
                continue;
            }
            idSet.add(driverId);
            DriverInfoDTO infoDTO = new DriverInfoDTO();
            infoDTO.setId(driverId);
            infoDTO.setName(name.split("=")[1]);
            infoDTO.setPhone(phone.split("=")[1]);
            driverInfoList.add(infoDTO);
        }

        File file1 = new File("F:" + File.separator + "\\15天用小程序已签约的司机清单.xlsx");
        EasyExcel.write(file1, DriverInfoDTO.class).sheet("模板").doWrite(driverInfoList);
    }
}

