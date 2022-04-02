package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.muyer.mapplanning.excel.GasOilPriceDTO;
import com.muyer.mapplanning.excel.GasStationDTO;
import com.muyer.mapplanning.res.GasPriceDetail;
import com.muyer.mapplanning.res.GasStationDetail;
import com.muyer.mapplanning.res.Position;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description: 
 * date: 2022/3/31 18:54
 * @author YeJiang
 * @version
 */
public class GasStationMain {
    public static void main(String[] args) throws Exception {
        //1.获取订单轨迹点列表
        //List<Position> orderPosList = getOrderPosList();
        Map<String, List<Position>> orderPosMap = getOrderPosMap();
        for (String orderNo : orderPosMap.keySet()) {
            List<Position> orderPosList = orderPosMap.get(orderNo);
            handler(orderPosList);
        }


    }

    private static void handler(List<Position> orderPosList) {
        //2.获取轨迹点附近x公里的加油站集合 gasList
        List<GasStationDetail> gasStationDetails = GasStationExcel();
        List<String> gasNames = Lists.newArrayList();
        List<GasStationDetail> gasList = Lists.newArrayList();
        for (Position pos : orderPosList) {
            double minDistance = 0;
            GasStationDetail minDistanceDetail = null;
            for (GasStationDetail detail : gasStationDetails) {
                if (gasNames.contains(detail.getGasName())) {
                    continue;
                }
                double distance = DistanceUtil.getDistance(detail.getLng(), detail.getLat(), pos.getLng(), pos.getLat());
                if (minDistance == 0 || distance < minDistance) {
                    minDistance = distance;
                    minDistanceDetail = detail;
                }
            }
            if (Objects.nonNull(minDistanceDetail) && !gasNames.contains(minDistanceDetail.getGasName())) {
                gasNames.add(minDistanceDetail.getGasName());
                gasList.add(minDistanceDetail);
                //System.out.println(minDistance);
            }
        }
        //System.out.println(JSON.toJSONString(gasList));
        System.out.println(gasList.stream().map(GasStationDetail::getGasName).sorted().collect(Collectors.joining(",")));
        //3.遍历所有路径得到最优的路线
    }

    private static List<Position> getOrderPosList() throws IOException {
        //读数据
        Reader reader = new FileReader("F:" + File.separator + "团油数据\\OR00075764.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr;
        List<Position> list = new ArrayList<>();
        while ((lineStr = bufferedReader.readLine()) != null) {
            Position position = new Position();
            position.setLat(Double.parseDouble(lineStr.split(",")[0]));
            position.setLng(Double.parseDouble(lineStr.split(",")[1]));
            list.add(position);
        }
        bufferedReader.close();
        reader.close();
        return list;
    }

    private static Map<String, List<Position>> getOrderPosMap() throws IOException {
        //读数据
        Reader reader = new FileReader("F:" + File.separator + "团油数据\\orderPosition10.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        Map<String, List<Position>> resMap = Maps.newHashMap();
        String lineStr;
        while ((lineStr = bufferedReader.readLine()) != null) {
            String orderNo = lineStr.split(",")[0];
            List<Position> positions = resMap.get(orderNo);
            if (CollectionUtils.isEmpty(positions)) {
                positions = Lists.newArrayList();
            }
            Position position = new Position();
            position.setLat(Double.parseDouble(lineStr.split(",")[1]));
            position.setLng(Double.parseDouble(lineStr.split(",")[2]));
            positions.add(position);
            resMap.put(orderNo, positions);
        }
        bufferedReader.close();
        reader.close();
        return resMap;
    }

    private static List<GasStationDetail> GasStationExcel() {
        File file = new File("F:" + File.separator + "团油数据\\加油站.xlsx");
        List<GasStationDTO> list;
        List<GasStationDetail> resList = Lists.newArrayList();
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
                //System.out.println(JSON.toJSONString(gasStationDetail));
                resList.add(gasStationDetail);
            }
        } catch (Exception e) {
        }
        return resList;
    }
}
