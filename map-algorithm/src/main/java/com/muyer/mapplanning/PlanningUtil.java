package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.muyer.mapplanning.excel.AvgPriceDTO;
import com.muyer.mapplanning.excel.PointDTO;
import com.muyer.mapplanning.res.GasPriceDetail;
import com.muyer.mapplanning.res.GasStationDetail;
import com.muyer.mapplanning.res.Position;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description: 
 * date: 2022/4/4 16:32
 * @author YeJiang
 * @version
 */
public class PlanningUtil {

    //TODO 提交需要删除
    public static String app_key = "7AKBZ-E36RF-OAYJM-JCAHX-UDAC5-GBB2G";

    public static RestTemplate restTemplate = new RestTemplate();
    public static double avg_oil_wear = 6.76;
    public static Map<String, Double> oilWearMap = Maps.newHashMap();
    public static Map<String, Integer> routeOrderCountMap = Maps.newHashMap();
    public static List<GasStationDetail> gasStationDetails;
    public static Map<String, GasStationDetail> gasStationDetailMap = Maps.newHashMap();
    public static List<String> fileList;
    public static Map<String, DistanceDTO> distanceMap = Maps.newHashMap();

    public static void main(String[] args) throws Exception {

        before();

        List<AvgPriceDTO> avgList = Lists.newArrayListWithCapacity(50);
        Map<String, PointDTO> niceGasMap = Maps.newHashMap();

        for (String fileName : fileList) {
            //行车轨迹
            List<Position> orderPosList = GasStationMain.getOrderPosList(fileName);
            String routeName = fileName.replace("货车导航轨迹.txt", "");
            //获取油耗
            double oilWear = Objects.nonNull(oilWearMap.get(routeName)) &&
                    oilWearMap.get(routeName) > 0 ?
                    oilWearMap.get(routeName) :
                    avg_oil_wear;

            //轨迹上5公里的加油站,并排序
            int type = 1;
            if (fileName.contains("长春")) {
                type = 3;
            } else if (fileName.contains("济宁")) {
                type = 4;
            } else if (fileName.contains("北京")) {
                type = 5;
            } else if (fileName.contains("济南")) {
                type = 6;
            } else if (fileName.contains("西安")) {
                type = 7;
            } else if (fileName.contains("青岛")) {
                type = 8;
            }
            GasStationDetail startGas = new GasStationDetail("起点", orderPosList.get(0).getLng(), orderPosList.get(0).getLat(), type);
            List<GasStationDetail> stations = Lists.newArrayList(startGas);
            List<String> stationNames = Lists.newArrayList();
            Map<String, GasStationDetail> cityMap = Maps.newHashMap();
            for (Position pos : orderPosList) {
                GasStationDetail minDistanceDetail = null;
                for (GasStationDetail detail : gasStationDetails) {
                    if ((type == 3 || type == 4 || type == 6) && !detail.getGasName().contains("品牌")) {
                        continue;
                    }
                    if (stationNames.contains(detail.getGasName())) {
                        continue;
                    }
                    double distance = DistanceUtil.getDistance(detail.getLng(), detail.getLat(), pos.getLng(), pos.getLat());
                    if (distance < 2000) {
                        minDistanceDetail = detail;
                    }
                }
                if (Objects.nonNull(minDistanceDetail) && cityMap.keySet().contains(minDistanceDetail.getCity())) {
                    GasStationDetail cityGas = cityMap.get(minDistanceDetail.getCity());
                    if (cityGas.getCheap() < minDistanceDetail.getCheap()) {
                        continue;
                    }
                }
                if (Objects.nonNull(minDistanceDetail) && !stationNames.contains(minDistanceDetail.getGasName())) {
                    GasStationDetail lastStation = stations.get(stations.size() - 1);
                    if (DistanceUtil.getDistance(lastStation.getLng(), lastStation.getLat(), minDistanceDetail.getLng(), minDistanceDetail.getLat()) < 10000) {
                        continue;
                    }
                    stationNames.add(minDistanceDetail.getGasName());
                    cityMap.put(minDistanceDetail.getCity(), minDistanceDetail);
                    stations.add(minDistanceDetail);

                }
            }
            GasStationDetail endGas = new GasStationDetail("终点", orderPosList.get(orderPosList.size() - 1).getLng(),
                    orderPosList.get(orderPosList.size() - 1).getLat(),
                    2);
            stations.add(endGas);
            //计算最划算的加油策略
            //出发地到第一个加油站距离
            System.out.print(stations.size() + ":");

            List<String> pathList = SubSet.getPathListV2(stations.size());
            System.out.println(pathList.size());
            double maxMoney = 0;
            double maxMoneyDistance = 0;
            String maxPath = "";
            double minMoney = 0;
            double minMoneyDistance = 0;
            String minPath = "";
            String minPathOil = "";
            File file = new File("F:\\团油数据\\最优路线计算结果-v2\\" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            //加true是追加，不加true就是覆盖了
            Writer writer = new FileWriter(file, false);
            for (String path : pathList) {
                StringBuilder sb = new StringBuilder("经过的油站:" + path);
                StringBuilder oilAmountSb = new StringBuilder();
                String[] indexs = path.split(",");
                double totalMoney = 0;
                double totalDistance = 0;
                int lastIndex = 0;
                for (int i = 0; i < indexs.length; i++) {
                    GasStationDetail lastStation = stations.get(lastIndex);
                    GasStationDetail nowStation = stations.get(Integer.parseInt(indexs[i]));
                    double distance = getDistance(lastStation.getLng(), lastStation.getLat(),
                            nowStation.getLng(), nowStation.getLat());
                    double oilAmount = MathUtil.div(distance, oilWear * 1000);
                    double money = MathUtil.mul(oilAmount, lastStation.getCheap());
                    totalMoney = MathUtil.add(totalMoney, money);
                    totalDistance = MathUtil.add(totalDistance, distance);
                    lastIndex = Integer.parseInt(indexs[i]);
                    sb.append("***出发地:" + lastStation.getGasName() + "|目的地:" + nowStation.getGasName() +
                            "|距离:" + distance + "|单价:" + lastStation.getCheap() + "|加油的钱：" + money);
                    //非起点
                    if (!"起点".equals(lastStation.getGasName())) {
                        oilAmountSb.append(lastStation.getGasName()).append(":").append(oilAmount).append(",");
                    }
                }

                GasStationDetail lastStation = stations.get(lastIndex);
                GasStationDetail endPoint = stations.get(stations.size() - 1);
                double distance = getDistance(lastStation.getLng(), lastStation.getLat(),
                        endPoint.getLng(), endPoint.getLat());
                double oilAmount = MathUtil.div(distance, oilWear * 1000);
                double money = MathUtil.mul(oilAmount, lastStation.getCheap());
                totalMoney = MathUtil.add(totalMoney, money);
                totalDistance = MathUtil.add(totalDistance, distance);
                oilAmountSb.append(lastStation.getGasName()).append(":").append(oilAmount);
                sb.append("***出发地:" + lastStation.getGasName() + "|目的地:" + endPoint.getGasName() +
                        "|距离:" + distance + "|单价:" + lastStation.getCheap() + "|加油的钱：" + money);
                sb.append(",totalMoney:" + totalMoney);
                sb.append(",totalDistance:" + totalDistance);
                writer.write(sb.toString());
                writer.write("\r\n");
                if (maxMoney == 0) {
                    maxMoney = totalMoney;
                    minMoney = totalMoney;
                    minPath = path;
                    maxPath = path;
                }
                if (minMoney >= totalMoney) {
                    minMoney = totalMoney;
                    minMoneyDistance = totalDistance;
                    minPath = path;
                    minPathOil = oilAmountSb.toString();
                }
                if (maxMoney <= totalMoney) {
                    maxMoney = totalMoney;
                    maxMoneyDistance = totalDistance;
                    maxPath = path;
                }
            }
            writer.write("maxMoney:" + maxMoney + ",distance:" + maxMoneyDistance + ",经过的油站:" + maxPath);
            writer.write(", \r\n");
            writer.write("minMoney:" + minMoney + ",distance:" + minMoneyDistance + ",经过的油站:" + minPath);
            writer.write("\r\n");
            writer.write("minMoneyOilUser:" + minPathOil);
            writer.write("\r\n");
            double distance = getDistance(orderPosList.get(0).getLng(), orderPosList.get(0).getLat(),
                    orderPosList.get(orderPosList.size() - 1).getLng(), orderPosList.get(orderPosList.size() - 1).getLat());
            double money = MathUtil.mul(
                    MathUtil.div(getDistance(orderPosList.get(0).getLng(), orderPosList.get(0).getLat(),
                            orderPosList.get(orderPosList.size() - 1).getLng(), orderPosList.get(orderPosList.size() - 1).getLat()), oilWear * 1000),
                    stations.get(0).getCheap());
            writer.write("money:" + money + ",distance:" + distance + "(原有方式)");
            writer.write("\r\n");
            writer.close();
            Integer orderCount = routeOrderCountMap.get(routeName);
            avgList.add(new AvgPriceDTO(routeName, minMoney, money, Objects.nonNull(orderCount) ? orderCount : 0, MathUtil.div(distance, oilWear * 1000)));
            //当此处规划省钱了，把相关的加油站缓存起来
//            if (money > minMoney) {
//                String[] pathArray = minPath.split(",");
//                String[] pathOilArray = minPathOil.split(",");
//                Map<String, Double> pathOilMap = Maps.newHashMap();
//                for (String pathOil : pathOilArray) {
//                    String[] pathOilSplit = pathOil.split(":");
//                    pathOilMap.put(pathOilSplit[0], Double.valueOf(pathOilSplit[1]));
//                }
//                for (String path : pathArray) {
//                    GasStationDetail station = stations.get(Integer.parseInt(path));
//                    String lngLat = station.getLng() + "," + station.getLat();
//                    PointDTO pointDTO = new PointDTO();
//                    pointDTO.setOil(pathOilMap.get(station.getGasName()));
//                    pointDTO.setName(station.getGasName());
//                    pointDTO.setLngLat(lngLat);
//                    pointDTO.setProvince(StringUtils.isEmpty(station.getProvince()) ? station.getCity() : station.getProvince());
//                    pointDTO.setCity(station.getCity());
//                    if (Objects.nonNull(niceGasMap.get(lngLat))) {
//                        pointDTO.setOil(MathUtil.add(pointDTO.getOil(), niceGasMap.get(lngLat).getOil()));
//                    }
//                    niceGasMap.put(lngLat, pointDTO);
//                }
//
//            }
            after();
        }

        after();
        //相关路线费用写入Excel
        File file1 = new File("F:" + File.separator + "团油数据\\价格1-v2.xlsx");
        File file2 = new File("F:" + File.separator + "团油数据\\价格2-v2.xlsx");
        EasyExcel.write(file1, AvgPriceDTO.class).sheet("模板").doWrite(avgList);
        EasyExcel.write(file2, AvgPriceDTO.class).sheet("模板").doWrite(avgList.stream().filter(o -> o.getMin() <= o.getNomal()).collect(Collectors.toList()));

        //相关最优加油站写入Excel
        File file3 = new File("F:" + File.separator + "团油数据\\最优油站点集-v2.xlsx");
        EasyExcel.write(file3, PointDTO.class).sheet("模板").doWrite(new ArrayList<>(niceGasMap.values()));


    }

    private static void after() throws IOException {
        //缓存最新的
        //写数据
        File file = new File("F:\\团油数据\\distance.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        //加true是追加，不加true就是覆盖了
        Writer writer = new FileWriter(file, false);
        for (String key : distanceMap.keySet()) {
            DistanceDTO dto = distanceMap.get(key);
            if (Objects.isNull(dto)) {
                continue;
            }
            writer.write(dto.getKey() + "," + dto.getDistance() + "\n");
        }
        writer.close();
    }

    public static void before() throws Exception {
        //获取缓存
        //获取团油所有加油站
        gasStationDetails = GasStationMain.GasStationExcel();
        //过滤没有0号油的油站
        gasStationDetails = gasStationDetails.stream().filter(gas -> {
            boolean has0Oil = false;
            for (GasPriceDetail detail : gas.getPriceList()) {
                if (detail.getOilNo() == 0) {
                    has0Oil = true;
                }
            }
            gasStationDetailMap.put(gas.getGasName(), gas);
            return has0Oil;
        }).collect(Collectors.toList());
        fileList = GasStationMain.getFileName("F:\\团油数据\\导航轨迹-超过60个订单的路线");
        //fileList = Lists.newArrayList("重庆市-重庆市货车导航轨迹.txt");

        //读加油站之间距离缓存
        Reader reader = new FileReader("F:\\团油数据\\distance.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr = "";
        while ((lineStr = bufferedReader.readLine()) != null) {
            String[] split = lineStr.split(",");
            String key = split[0];
            double distance = Double.parseDouble(split[1]);
            distanceMap.put(key, new DistanceDTO(key, distance));
        }
        bufferedReader.close();
        reader.close();

        //读取油耗缓存
        Reader readOilWear = new FileReader("F:\\团油数据\\不同路线的油耗.txt");
        BufferedReader readOilWearBufferedReader = new BufferedReader(new FileReader("F:\\团油数据\\不同路线的油耗.txt"));
        String oilWearLineStr = "";
        while ((oilWearLineStr = readOilWearBufferedReader.readLine()) != null) {
            String[] split = oilWearLineStr.split(",");
            double oilWear = Double.parseDouble(split[1]);
            oilWearMap.put(split[0], oilWear);
        }
        readOilWearBufferedReader.close();
        readOilWear.close();

        //读取路线订单量
        FileReader fr = new FileReader("F:\\团油数据\\路线单量.txt");
        BufferedReader r = new BufferedReader(new FileReader("F:\\团油数据\\路线单量.txt"));
        String s;
        while ((s = r.readLine()) != null) {
            String[] split = s.split(",");
            int orderCount = Integer.parseInt(split[1]);
            routeOrderCountMap.put(split[0], orderCount);
        }
        r.close();
        fr.close();


    }


    public static double getDistance(double currentLng, double currentLat, double destinationLng, double destinationLat) {
        String key = currentLng + "&" + currentLat + "#" + destinationLng + "&" + destinationLat;
        DistanceDTO dto = distanceMap.get(key);
        if (Objects.nonNull(dto)) {
            return dto.getDistance();
        }
        double distance = tencentEstimatedDistance(currentLng, currentLat, destinationLng, destinationLat).getDouble("distance");
        if (distance <= 0) {
            distance = DistanceUtil.getDistance(currentLng, currentLat, destinationLng, destinationLat);
            System.out.println("DistanceUtil.getDistance : " + key);
        }
        distanceMap.put(key, new DistanceDTO(key, distance));
        return distance;
    }

    /**
     * 预计到达路程+时间 API （腾讯）https://apis.map.qq.com/ws/distance/v1/matrix
     *
     * @return
     */
    public static JSONObject tencentEstimatedDistance(double currentLng, double currentLat, double destinationLng, double destinationLat) {
        if (currentLng > 0 && currentLng > 0 && destinationLng > 0 && destinationLat > 0) {
            String url = "https://apis.map.qq.com/ws/distance/v1/matrix/?mode=driving&from=" + currentLat + "," + currentLng + "&to=" + destinationLat + "," + destinationLng + "&key=" + app_key;
            ResponseEntity<String> response = null;
            try {
                response = restTemplate.getForEntity(url, String.class);
            } catch (Exception e) {

            }
            if (response != null && onSuccessed(response.getBody())) {
                //time 总时间(秒)
                //distance 总距离(米)
                JSONObject resultObj = JSONObject.parseObject(response.getBody());
                if (resultObj != null) {

                    JSONObject result = resultObj.getJSONObject("result");
                    if (result != null) {
                        JSONArray resultArray = result.getJSONArray("rows");
                        if (resultArray != null && resultArray.size() > 0) {
                            JSONArray elements = resultArray.getJSONObject(0).getJSONArray("elements");
                            if (elements != null && elements.size() > 0) {
                                return elements.getJSONObject(0);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static boolean onSuccessed(String result) {
        if (StringUtils.isNotBlank(result)) {
            try {
                JSONObject jsonResult = JSON.parseObject(result);
                if (jsonResult != null && jsonResult.getInteger("status") == 0) {
                    return true;
                }
            } catch (Exception e) {

            }
        }
        return false;
    }

}
