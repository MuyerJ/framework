package com.muyer.mapplanning;

import com.muyer.mapplanning.res.GasStationDetail;

import java.util.List;

/**
 * Description: 
 * date: 2022/4/11 15:31
 * @author YeJiang
 * @version
 */
public class GasGetUtil {

    public static void main(String[] args) throws Exception {
        //System.out.println(DistanceUtil.getDistance(109.076026, 34.490914, 109.4661, 33.956587));
        //预热数据
        PlanningUtil.before();
        List<GasStationDetail> stationDetails = PlanningUtil.gasStationDetails;
        //
        double lat = 34.745037;
        double lng = 109.26798;
        double minDistance = 0;
        String minName = "";

        for (GasStationDetail gas : stationDetails) {

            double distance = DistanceUtil.getDistance(gas.getLng(), gas.getLat(), lng, lat);
            if (minDistance == 0 || minDistance > distance) {
                minDistance = distance;
                minName = gas.getGasName();
            }
        }
        System.out.println(minName);

    }

}
