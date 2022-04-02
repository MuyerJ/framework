package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.muyer.mapplanning.excel.GasOilPriceDTO;
import com.muyer.mapplanning.excel.GasStationDTO;
import com.muyer.mapplanning.excel.LineDTO;
import com.muyer.mapplanning.excel.PointDTO;
import com.muyer.mapplanning.res.GasPriceDetail;
import com.muyer.mapplanning.res.GasStationDetail;
import com.muyer.mapplanning.res.Position;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description: 
 * date: 2022/3/31 18:54
 * @author YeJiang
 * @version
 */
public class GasStationMain {

    public static int D5KM = 5000;
    public static int D10KM = 10000;

    public static void main(String[] args) throws Exception {
        writeLineExcel();
    }


    private static void writeLineExcel() {
        List<String> fileList = getFileName("F:\\团油数据\\导航轨迹");
        List<LineDTO> list = Lists.newArrayList();
        for (String fileName : fileList) {
            try {
                List<Position> orderPosList = getOrderPosList(fileName);
                StringBuilder sb = new StringBuilder();
                int count = 1;
                for (Position p : orderPosList) {
                    sb.append(p.getLngLat()).append(",");
                    if (count % 2 == 0) {
                        //10个一条数据
                        LineDTO line = new LineDTO();
                        line.setLatLng(sb.toString().substring(0, sb.toString().length() - 1));
                        list.add(line);
                        sb = new StringBuilder();
                        sb.append(p.getLngLat()).append(",");
                    }
                    count++;
                }
            } catch (IOException e) {

            }

        }
        File file = new File("F:" + File.separator + "团油数据\\数据模板\\数据模板-前10条热点线路.xlsx");
        EasyExcel.write(file, LineDTO.class).sheet("模板").doWrite(list);
    }


    private static void writeLineExcelTemplate() {
        List<LineDTO> list = Lists.newArrayList();
        LineDTO l1 = new LineDTO();
        l1.setPartition("华北");
        l1.setStart("北京");
        l1.setLatLng("[116.427287,39.904983],[117.201538,39.085294],[114.514793,38.042225]");
        LineDTO l2 = new LineDTO();
        l2.setPartition("华东");
        l2.setStart("上海");
        l2.setLatLng("[121.473658,31.230378],[118.77085,32.051114],[120.199072,30.369281]");
        LineDTO l3 = new LineDTO();
        l3.setPartition("华南");
        l3.setStart("广州");
        l3.setLatLng("[113.272505,23.134003],[114.096479,22.511246],[113.461968,22.180771]");
        list.add(l1);
        list.add(l2);
        list.add(l3);
        File file = new File("F:" + File.separator + "团油数据\\数据模板-线.xlsx");
        EasyExcel.write(file, LineDTO.class).sheet("模板").doWrite(list);
    }

    //写加油站到excel
    private static void writePointExcel() {
        List<PointDTO> points = GasStationExcel().stream().map(g -> {
            PointDTO pointDTO = new PointDTO();
            pointDTO.setName(g.getGasName());
            pointDTO.setLngLat(g.getLng() + "," + g.getLat());
            pointDTO.setProvince(StringUtils.isEmpty(g.getProvince()) ? g.getCity() : g.getProvince());
            return pointDTO;
        }).collect(Collectors.toList());
        File file = new File("F:" + File.separator + "团油数据\\数据模板-加油站.xlsx");
        EasyExcel.write(file, PointDTO.class).sheet("订单模板").doWrite(points);
    }


    private static void handlerWithX(List<Position> orderPosList, int x) {
        //2.获取轨迹点附近x公里的加油站集合 gasList
        List<GasStationDetail> gasStationDetails = GasStationExcel();
        List<String> gasNames = Lists.newArrayList();
        List<GasStationDetail> gasList = Lists.newArrayList();
        for (Position pos : orderPosList) {
            GasStationDetail minDistanceDetail = null;
            for (GasStationDetail detail : gasStationDetails) {
                if (gasNames.contains(detail.getGasName())) {
                    continue;
                }
                double distance = DistanceUtil.getDistance(detail.getLng(), detail.getLat(), pos.getLng(), pos.getLat());
                if (distance < x) {
                    System.out.println(detail.getGasName() + " 距离 ：" + distance);
                    minDistanceDetail = detail;
                }
            }
            if (Objects.nonNull(minDistanceDetail) && !gasNames.contains(minDistanceDetail.getGasName())) {
                gasNames.add(minDistanceDetail.getGasName());
                gasList.add(minDistanceDetail);
            }
        }
        System.out.println(gasList.stream().map(GasStationDetail::getGasName).sorted().collect(Collectors.joining(",")));
        //3.遍历所有路径得到最优的路线
    }

    private static void handlerWithMin(List<Position> orderPosList) {
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

    private static List<Position> getOrderPosList(String fileName) throws IOException {
        //读数据
        Reader reader = new FileReader("F:" + File.separator + "团油数据\\导航轨迹\\" + fileName);
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


    public static List<String> getFileName(String path) {
        List<String> list = Lists.newArrayList();
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return list;
        }

        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
//            if (fs.isDirectory()) {
//                System.out.println(fs.getName() + " [目录]");
//            } else {
//                System.out.println(fs.getName());
//            }
            list.add(fs.getName());
        }
        return list;
    }
}
