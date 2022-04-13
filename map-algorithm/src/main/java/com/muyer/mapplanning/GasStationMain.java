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

    public static void main(String[] args) throws Exception {
        List<String> fileList = getFileName("F:\\团油数据\\导航轨迹");
        String s = "济宁市-济宁市货车导航轨迹";
        //获取团油所有加油站
        List<GasStationDetail> gasStationDetails = GasStationExcel();
        Map<String, GasStationDetail> res = Maps.newHashMap();
        for (String fileName : fileList) {
            List<Position> orderPosList = getOrderPosList(fileName);
            Map<String, GasStationDetail> detailMap = handlerWithX(orderPosList, gasStationDetails, 5000);
            //System.out.println(detailMap.size());
            res.putAll(detailMap);
            //handlerWithMin(orderPosList, gasStationDetails);
        }
    }


    /**
     * 10条路线经过的省市
     * @throws IOException
     */
    public static void getProviceAndCity() throws IOException {
        List<String> fileList = getFileName("F:\\团油数据\\导航轨迹");
        //获取团油所有加油站
        List<GasStationDetail> gasStationDetails = GasStationExcel();
        Set<String> provinceSet = Sets.newHashSet();
        Set<String> citySet = Sets.newHashSet();

        Map<String, GasStationDetail> res = Maps.newHashMap();
        for (String fileName : fileList) {
            List<Position> orderPosList = getOrderPosList(fileName);
            Map<String, GasStationDetail> detailMap = handlerWithX(orderPosList, gasStationDetails, 20000);
            detailMap.values().forEach(o -> {
                provinceSet.add(o.getProvince());
                citySet.add(o.getCity());
            });
            //System.out.println(detailMap.size());
            res.putAll(detailMap);
            //handlerWithMin(orderPosList, gasStationDetails);
        }
        System.out.println(provinceSet.stream().collect(Collectors.joining(",")));
        System.out.println(citySet.stream().collect(Collectors.joining(",")));
    }


    /**
     * 生成：数据模板-前10条热点线路.xlsx
     */
    public static void writeLineExcel() {
        //List<String> fileList = getFileName("F:\\团油数据\\导航轨迹");
        List<String> fileList = Lists.newArrayList("重庆市-伊犁哈萨克自治州货车导航轨迹.txt");
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
        File file = new File("F:" + File.separator + "团油数据\\数据模板\\数据模板-长春市-随州市货车导航轨迹.xlsx");
        EasyExcel.write(file, LineDTO.class).sheet("模板").doWrite(list);
    }


    /**
     * 数据模板-加油站.xlsx
     */
    public static void writePointExcel() {
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


    /**
     * 获取轨迹上x 米的加油站集合
     * @param orderPosList
     * @param x
     */
    public static Map<String, GasStationDetail> handlerWithX(List<Position> orderPosList, List<GasStationDetail> gasStationDetails, int x) {
        Map<String, GasStationDetail> res = Maps.newHashMap();

        for (Position pos : orderPosList) {
            GasStationDetail minDistanceDetail = null;
            for (GasStationDetail detail : gasStationDetails) {
                if (res.keySet().contains(detail.getGasName())) {
                    continue;
                }
                double distance = DistanceUtil.getDistance(detail.getLng(), detail.getLat(), pos.getLng(), pos.getLat());
                if (distance < x) {
                    //System.out.println(detail.getGasName() + " 距离 ：" + distance);
                    minDistanceDetail = detail;
                }
            }
            if (Objects.nonNull(minDistanceDetail) && !res.keySet().contains(minDistanceDetail.getGasName())) {
                res.put(minDistanceDetail.getGasName(), minDistanceDetail);
            }
        }
        //System.out.println(res.keySet().stream().sorted().collect(Collectors.joining(",")));
        //System.out.println(res.keySet().size());
        //3.遍历所有路径得到最优的路线
        return res;
    }

    /**
     * 获取轨迹上最近的加油站集合
     * @param orderPosList
     */
    public static Map<String, GasStationDetail> handlerWithMin(List<Position> orderPosList, List<GasStationDetail> gasStationDetails) {
        Map<String, GasStationDetail> res = Maps.newHashMap();
        for (Position pos : orderPosList) {
            double minDistance = 0;
            GasStationDetail minDistanceDetail = null;
            for (GasStationDetail detail : gasStationDetails) {
                if (res.keySet().contains(detail.getGasName())) {
                    continue;
                }
                double distance = DistanceUtil.getDistance(detail.getLng(), detail.getLat(), pos.getLng(), pos.getLat());
                if (minDistance == 0 || distance < minDistance) {
                    minDistance = distance;
                    minDistanceDetail = detail;
                }
            }
            if (Objects.nonNull(minDistanceDetail) && !res.keySet().contains(minDistanceDetail.getGasName())) {
                res.put(minDistanceDetail.getGasName(), minDistanceDetail);
            }
        }
        System.out.println(res.keySet().stream().sorted().collect(Collectors.joining(",")));
        System.out.println(res.keySet().size());
        //3.遍历所有路径得到最优的路线
        return res;
    }

    public static List<Position> getOrderPosList(String fileName) throws IOException {
        //读数据
        Reader reader = new FileReader("F:" + File.separator + "团油数据\\导航轨迹-西安青岛长春\\" + fileName);
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

    public static Map<String, List<Position>> getOrderPosMap() throws IOException {
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

    public static List<GasStationDetail> GasStationExcel() {
        File file = new File("F:" + File.separator + "团油数据\\加油站.xlsx");
        List<GasStationDTO> list;
        List<GasStationDetail> resList = Lists.newArrayList();
        try {
            list = EasyExcel.read(file).autoTrim(true).head(GasStationDTO.class).sheet().doReadSync();
            for (GasStationDTO dto : list) {
                GasStationDetail gasStationDetail = new GasStationDetail();
                gasStationDetail.setId(dto.getProductNo());
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

