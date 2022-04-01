package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.muyer.mapplanning.excel.GasOilPriceDTO;
import com.muyer.mapplanning.excel.GasStationDTO;
import com.muyer.mapplanning.res.GasPriceDetail;
import com.muyer.mapplanning.res.GasStationDetail;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Description: 
 * date: 2022/3/31 18:54
 * @author YeJiang
 * @version
 */
public class GasStationMain {
    public static void main(String[] args) {
        File file = new File("F:" + File.separator + "团油数据\\加油站.xlsx");
        List<GasStationDTO> list;
        List<GasStationDetail> resList;
        try {
            list = EasyExcel.read(file).autoTrim(true).head(GasStationDTO.class).sheet().doReadSync();
            for (GasStationDTO dto : list) {
                GasStationDetail gasStationDetail = new GasStationDetail();
                gasStationDetail.setGasName(dto.getProductName());
                Map<String, String> map = RegexUtils.addressPattern(dto.getDetailAddress());
                gasStationDetail.setProvince(map.get(RegexUtils.PROVINCE));
                gasStationDetail.setCity(map.get(RegexUtils.CITY));
                gasStationDetail.setRegion(map.get(RegexUtils.REGION));
                gasStationDetail.setLat(dto.getLat());
                gasStationDetail.setLng(dto.getLng());
                List<GasOilPriceDTO> oilPriceList = JSON.parseArray(dto.getPriceExtJson(), GasOilPriceDTO.class);
                for (GasOilPriceDTO dto2 : oilPriceList) {
                    GasPriceDetail detail = new GasPriceDetail();
                    detail.setOilNo(dto2.getOilNo());
                    detail.setGunPrice(dto2.getPriceGun());
                    detail.setVipPrice(dto2.getPriceVip());
                    gasStationDetail.getPriceList().add(detail);
                }
                System.out.println(JSON.toJSONString(gasStationDetail));
            }
        } catch (Exception e) {
        }
    }
}
