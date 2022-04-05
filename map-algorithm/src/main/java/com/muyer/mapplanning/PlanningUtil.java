package com.muyer.mapplanning;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.muyer.mapplanning.res.GasStationDetail;
import com.muyer.mapplanning.res.Position;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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

    public static void main(String[] args) throws IOException {

        //获取团油所有加油站
        List<GasStationDetail> gasStationDetails = GasStationMain.GasStationExcel();

        List<String> fileList = GasStationMain.getFileName("F:\\团油数据\\导航轨迹");

        for (String fileName : fileList) {
            //行车轨迹
            List<Position> orderPosList = GasStationMain.getOrderPosList(fileName);

            //轨迹上5公里的加油站,并排序
            GasStationDetail startGas = new GasStationDetail("起点", orderPosList.get(0).getLng(), orderPosList.get(0).getLat(), fileName.contains("长春") ? 3 : 1);
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
            String maxPath = "";
            double minMoney = 0;
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
                int lastIndex = 0;
                for (int i = 0; i < indexs.length; i++) {
                    GasStationDetail lastStation = stations.get(lastIndex);
                    GasStationDetail nowStation = stations.get(Integer.parseInt(indexs[i]));
                    double distance = DistanceUtil.getDistance(lastStation.getLng(), lastStation.getLat(),
                            nowStation.getLng(), nowStation.getLat());
                    double money = MathUtil.mul(MathUtil.div(distance, 5000), lastStation.getCheap());
                    totalMoney = totalMoney + money;
                    lastIndex = Integer.parseInt(indexs[i]);
                    sb.append("出发地:" + lastStation.getGasName() + ",价格:" + lastStation.getCheap() +
                            ",目的地:" + nowStation.getGasName() + ",距离:" + distance + ",加油的钱：" + money);
                }

                GasStationDetail lastStation = stations.get(lastIndex);
                GasStationDetail endPoint = stations.get(stations.size() - 1);
                double distance = DistanceUtil.getDistance(lastStation.getLng(), lastStation.getLat(),
                        endPoint.getLng(), endPoint.getLat());
                double money = MathUtil.mul(MathUtil.div(distance, 5000), lastStation.getCheap());
                totalMoney = totalMoney + money;
                sb.append("出发地:" + lastStation.getGasName() + ",价格:" + lastStation.getCheap() +
                        ",目的地:" + endPoint.getGasName() + ",距离:" + distance + ",加油的钱：" + money);
                sb.append(",totalMoney:" + totalMoney);
                writer.write(sb.toString());
                writer.write("\r\n");
                if (maxMoney == 0) {
                    maxMoney = totalMoney;
                    minMoney = totalMoney;
                }
                if (minMoney > totalMoney) {
                    minMoney = totalMoney;
                    minPath = path;
                }
                if (maxMoney < totalMoney) {
                    maxMoney = totalMoney;
                    maxPath = path;
                }
            }
            writer.write("maxMoney:" + maxMoney + ",经过的油站:" + maxPath);
            writer.write(", \r\n");
            writer.write("minMoney:" + minMoney + ",经过的油站:" + minPath);
            writer.write("\r\n");
            writer.close();
        }


    }
}
