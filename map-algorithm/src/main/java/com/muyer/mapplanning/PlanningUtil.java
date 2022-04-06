package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.muyer.mapplanning.excel.AvgPriceDTO;
import com.muyer.mapplanning.excel.LineDTO;
import com.muyer.mapplanning.res.GasStationDetail;
import com.muyer.mapplanning.res.Position;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description: 
 * date: 2022/4/4 16:32
 * @author YeJiang
 * @version
 */
public class PlanningUtil {

    //TODO 提交需要删除
    public static String app_key = "";

    public static RestTemplate restTemplate = new RestTemplate();
    public static List<GasStationDetail> gasStationDetails;
    public static List<String> fileList;
    public static Map<String, DistanceDTO> distanceMap = Maps.newHashMap();

    public static void main(String[] args) throws Exception {

        before();

        List<AvgPriceDTO> avgList = Lists.newArrayListWithCapacity(50);

        for (String fileName : fileList) {
            //行车轨迹
            List<Position> orderPosList = GasStationMain.getOrderPosList(fileName);

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
            }
            GasStationDetail startGas = new GasStationDetail("起点", orderPosList.get(0).getLng(), orderPosList.get(0).getLat(), type);
            List<GasStationDetail> stations = Lists.newArrayList(startGas);
            List<String> stationNames = Lists.newArrayList();
            Map<String, GasStationDetail> cityMap = Maps.newHashMap();
            for (Position pos : orderPosList) {
                GasStationDetail minDistanceDetail = null;
                for (GasStationDetail detail : gasStationDetails) {
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
                    if (DistanceUtil.getDistance(lastStation.getLng(), lastStation.getLat(), minDistanceDetail.getLng(), minDistanceDetail.getLat()) < 5000) {
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
            List<String> pathList = SubSet.getPathList(stations.size());
            System.out.println(stations.size() + ":" + pathList.size());
            double maxMoney = 0;
            double maxMoneyDistance = 0;
            String maxPath = "";
            double minMoney = 0;
            double minMoneyDistance = 0;
            String minPath = "";
            File file = new File("F:\\团油数据\\最优路线计算结果\\" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            //加true是追加，不加true就是覆盖了
            Writer writer = new FileWriter(file, false);
            for (String path : pathList) {
                StringBuilder sb = new StringBuilder("经过的油站:" + path);
                String[] indexs = path.split(",");
                double totalMoney = 0;
                double totalDistance = 0;
                int lastIndex = 0;
                for (int i = 0; i < indexs.length; i++) {
                    GasStationDetail lastStation = stations.get(lastIndex);
                    GasStationDetail nowStation = stations.get(Integer.parseInt(indexs[i]));
                    double distance = getDistance(lastStation.getLng(), lastStation.getLat(),
                            nowStation.getLng(), nowStation.getLat());
                    double money = MathUtil.mul(MathUtil.div(distance, 5000), lastStation.getCheap());
                    totalMoney = MathUtil.add(totalMoney, money);
                    totalDistance = MathUtil.add(totalDistance, distance);
                    lastIndex = Integer.parseInt(indexs[i]);
                    sb.append("***出发地:" + lastStation.getGasName() + "|目的地:" + nowStation.getGasName() +
                            "|距离:" + distance + "|单价:" + lastStation.getCheap() + "|加油的钱：" + money);
                }

                GasStationDetail lastStation = stations.get(lastIndex);
                GasStationDetail endPoint = stations.get(stations.size() - 1);
                double distance = getDistance(lastStation.getLng(), lastStation.getLat(),
                        endPoint.getLng(), endPoint.getLat());
                double money = MathUtil.mul(MathUtil.div(distance, 5000), lastStation.getCheap());
                totalMoney = MathUtil.add(totalMoney, money);
                totalDistance = MathUtil.add(totalDistance, distance);
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
                if (minMoney > totalMoney) {
                    minMoney = totalMoney;
                    minMoneyDistance = totalDistance;
                    minPath = path;
                }
                if (maxMoney < totalMoney) {
                    maxMoney = totalMoney;
                    maxMoneyDistance = totalDistance;
                    maxPath = path;
                }
            }
            writer.write("maxMoney:" + maxMoney + ",distance:" + maxMoneyDistance + ",经过的油站:" + maxPath);
            writer.write(", \r\n");
            writer.write("minMoney:" + minMoney + ",distance:" + minMoneyDistance + ",经过的油站:" + minPath);
            writer.write("\r\n");
            double distance = getDistance(orderPosList.get(0).getLng(), orderPosList.get(0).getLat(),
                    orderPosList.get(orderPosList.size() - 1).getLng(), orderPosList.get(orderPosList.size() - 1).getLat());
            double money = MathUtil.mul(
                    MathUtil.div(getDistance(orderPosList.get(0).getLng(), orderPosList.get(0).getLat(),
                            orderPosList.get(orderPosList.size() - 1).getLng(), orderPosList.get(orderPosList.size() - 1).getLat()), 5000),
                    stations.get(0).getCheap());
            writer.write("money:" + money + ",distance:" + distance + "(原有方式)");
            writer.write("\r\n");
            writer.close();
            avgList.add(new AvgPriceDTO(fileName.replace("货车导航轨迹.txt", ""), minMoney, money));
        }

        File file = new File("F:" + File.separator + "团油数据\\价格.xlsx");
        EasyExcel.write(file, AvgPriceDTO.class).sheet("模板").doWrite(avgList);
        after();


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

    private static void before() throws Exception {
        //获取缓存
        //获取团油所有加油站
        gasStationDetails = GasStationMain.GasStationExcel();
        fileList = GasStationMain.getFileName("F:\\团油数据\\导航轨迹-超过60个订单的路线");
        //fileList = Lists.newArrayList("重庆市-重庆市货车导航轨迹.txt");

        //读数据
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
