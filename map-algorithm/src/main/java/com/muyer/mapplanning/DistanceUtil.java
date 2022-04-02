package com.muyer.mapplanning;

import com.google.common.collect.Lists;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.util.List;

/**
 * <p>Description: 距离工具</p>
 */
public class DistanceUtil {

    private static final double EARTH_RADIUS = 6378137;//赤道半径

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @author: qulibin
     * @description:
     * @date: 9:52 PM 2020/3/22
     * @return: 单位米
     */
    public static double getDistance(double lng, double lat, double lng1, double lat1) {
        double radLat1 = rad(lat);
        double radLat2 = rad(lat1);
        double a = radLat1 - radLat2;
        double b = rad(lng) - rad(lng1);
        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s;
    }

    public static double getDistanceSphere(double latFrom, double lngFrom, double latTo, double lngTo) {
        GlobalCoordinates from = new GlobalCoordinates(latFrom, lngFrom);
        GlobalCoordinates to = new GlobalCoordinates(latTo, lngTo);
        return getDistanceByDiffCoordinateSystem(from, to, Ellipsoid.Sphere);
    }

    public static double getDistanceWGS84(double latFrom, double lngFrom, double latTo, double lngTo) {
        GlobalCoordinates from = new GlobalCoordinates(latFrom, lngFrom);
        GlobalCoordinates to = new GlobalCoordinates(latTo, lngTo);
        return getDistanceByDiffCoordinateSystem(from, to, Ellipsoid.WGS84);
    }

    public static double getDistanceByDiffCoordinateSystem(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
        return geoCurve.getEllipsoidalDistance();
    }


    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("OR00033126", "OR00033126", "OR00033127", "OR00033127", "OR00033128", "OR00033128", "OR00033129", "OR00033129", "OR00033130", "OR00033130", "OR00033131", "OR00033131", "OR00033132", "OR00033132", "OR00033133", "OR00033419", "OR00033420", "OR00033421", "OR00033422", "OR00033423", "OR00033424", "OR00033425", "OR00033426", "OR00033427", "OR00033428", "OR00033429", "OR00033430", "OR00033431", "OR00033432", "OR00033464", "OR00033464", "OR00033465", "OR00033465", "OR00033466", "OR00033466", "OR00033467", "OR00033467", "OR00033468", "OR00033468", "OR00034144", "OR00034145", "OR00034146", "OR00034147", "OR00034148", "OR00034187", "OR00034281", "OR00034281", "OR00034282", "OR00034282", "OR00034283", "OR00034283", "OR00034411", "OR00034412", "OR00034413", "OR00034414", "OR00034415", "OR00034416", "OR00034453", "OR00034454", "OR00034455", "OR00034456", "OR00034457", "OR00034458", "OR00034459", "OR00034460", "OR00034461", "OR00034462", "OR00034463", "OR00034464", "OR00034465", "OR00034466", "OR00034491", "OR00034491", "OR00034492", "OR00034492", "OR00034493", "OR00034493", "OR00034568", "OR00034569", "OR00034570", "OR00034571", "OR00034572", "OR00034573", "OR00034574", "OR00034575", "OR00034576", "OR00034577", "OR00034578", "OR00034579", "OR00034580", "OR00034581", "OR00034582", "OR00034583", "OR00034584", "OR00034585", "OR00034586", "OR00034587", "OR00034588", "OR00034589", "OR00034590", "OR00034591", "OR00034592", "OR00034593", "OR00034594", "OR00034595", "OR00034596", "OR00034597", "OR00034598", "OR00034599", "OR00034762", "OR00034763", "OR00034764", "OR00034765", "OR00034766", "OR00034767", "OR00034768", "OR00034769", "OR00034770", "OR00034772", "OR00034773", "OR00034774", "OR00034775", "OR00034776", "OR00034777", "OR00034778", "OR00034779", "OR00034780", "OR00034781", "OR00034782", "OR00034783", "OR00034784", "OR00034785", "OR00034786", "OR00034787", "OR00034788", "OR00034789", "OR00034790", "OR00034814", "OR00034815", "OR00034816", "OR00034819", "OR00034824", "OR00034835", "OR00034865", "OR00034866", "OR00034867", "OR00034885", "OR00034886", "OR00034887", "OR00034888", "OR00034890", "OR00034893", "OR00034895", "OR00034896", "OR00034897", "OR00034898", "OR00034899", "OR00034900", "OR00034901", "OR00034902", "OR00034903", "OR00034905", "OR00034906", "OR00034907", "OR00034908", "OR00034909", "OR00034910", "OR00034911", "OR00034912", "OR00034913", "OR00034914", "OR00034915", "OR00034916", "OR00034918", "OR00034919", "OR00034920", "OR00034950", "OR00034951", "OR00034952", "OR00034953", "OR00034954", "OR00034955", "OR00034956", "OR00034957", "OR00034960", "OR00034962", "OR00034986", "OR00034994", "OR00034995", "OR00034996", "OR00034997", "OR00034998", "OR00035004", "OR00035037", "OR00035145", "OR00035175", "OR00035268", "OR00035311", "OR00035311", "OR00035312");

        for (String orderNo : list) {
            if ("10".equals(String.format("%02d", Math.abs(orderNo.hashCode()) % 20))) {
                System.out.println(orderNo);
            }
        }
    }
}
