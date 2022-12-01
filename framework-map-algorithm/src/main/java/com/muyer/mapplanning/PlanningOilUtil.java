package com.muyer.mapplanning;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.muyer.mapplanning.res.GasStationDetail;
import com.muyer.mapplanning.res.Position;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: 
 * date: 2022/4/11 10:38
 * @author YeJiang
 * @version
 */
public class PlanningOilUtil {
    public static void main(String[] args) throws Exception {
        //预热数据
        PlanningUtil.before();

        Map<String, List<GasStationDetail>> res = new ConcurrentHashMap<>();

        //遍历每个路径
        PlanningUtil.fileList.parallelStream().forEach(fileName -> {
            //行车轨迹
            List<Position> orderPosList = null;
            try {
                orderPosList = GasStationMain.getOrderPosList(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String routeName = fileName.replace("货车导航轨迹.txt", "");

            List<GasStationDetail> stations = Lists.newArrayList();
            List<String> stationNames = Lists.newArrayList();
            for (Position pos : orderPosList) {
                for (GasStationDetail detail : PlanningUtil.gasStationDetails) {
                    if (stationNames.contains(detail.getGasName())) {
                        continue;
                    }
                    if (DistanceUtil.getDistance(detail.getLng(), detail.getLat(), pos.getLng(), pos.getLat()) < 3000) {
                        stationNames.add(detail.getGasName());
                        stations.add(detail);
                    }
                }

            }
            res.put(routeName, stations);
        });


        Map<String, Integer> CCCountMap = Maps.newHashMap();
        Map<String, Integer> XACountMap = Maps.newHashMap();
        Map<String, Integer> QDCountMap = Maps.newHashMap();
        for (String key : res.keySet()) {
            List<GasStationDetail> stations = res.get(key);
            Map<String, Integer> oilNameCountMap;
            if (key.contains("长春市-")) {
                oilNameCountMap = CCCountMap;
            } else if (key.contains("西安市-")) {
                oilNameCountMap = XACountMap;
            } else {
                oilNameCountMap = QDCountMap;
            }
            for (GasStationDetail detail : stations) {
                Integer count = oilNameCountMap.get(detail.getGasName());
                if (Objects.isNull(count) || count == 0) {
                    oilNameCountMap.put(detail.getGasName(), 1);
                } else {
                    oilNameCountMap.put(detail.getGasName(), count + 1);
                }
            }
        }

        System.out.println(CCCountMap);
    }
}
