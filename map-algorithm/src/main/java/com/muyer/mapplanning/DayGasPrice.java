package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.muyer.mapplanning.excel.DayGasPriceDTO;
import com.muyer.mapplanning.excel.GasOilPriceDTO;
import com.muyer.mapplanning.excel.GasStationDTO;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Description: 
 * date: 2022/5/9 10:23
 * @author YeJiang
 * @version
 */
public class DayGasPrice {
    public static void main(String[] args) {
        File file = new File("E:" + File.separator + "test1.xlsx");
        List<DayGasPriceDTO> resList = Lists.newArrayList();
        try {
            List<GasStationDTO> list = EasyExcel.read(file).autoTrim(true).head(GasStationDTO.class).sheet().doReadSync();
            for (GasStationDTO dto : list) {
                DayGasPriceDTO dayGasPriceDTO = new DayGasPriceDTO();
                dayGasPriceDTO.setGasName(dto.getProductName());
                Map<String, String> map = RegexUtils.addressPattern(dto.getDetailAddress());
                dayGasPriceDTO.setProvince(map.get(RegexUtils.PROVINCE));
                dayGasPriceDTO.setCity(map.get(RegexUtils.CITY));
                dayGasPriceDTO.setRegion(map.get(RegexUtils.REGION));
                List<GasOilPriceDTO> oilPriceList = JSON.parseArray(dto.getPriceExtJson(), GasOilPriceDTO.class);
                for (GasOilPriceDTO dto2 : oilPriceList) {
                    if("0#".equals(dto2.getOilNoName())){
                        dayGasPriceDTO.setVipPrice(dto2.getPriceVip());
                        dayGasPriceDTO.setGunPrice(dto2.getPriceGun());
                    }
                }
                resList.add(dayGasPriceDTO);
            }
        } catch (Exception e) {
        }
        File file2 = new File("E:" + File.separator + "test2.xlsx");
        EasyExcel.write(file2, DayGasPriceDTO.class).sheet("油站").doWrite(resList);
    }
}
