package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import com.muyer.mapplanning.excel.LineDTO;
import com.muyer.mapplanning.excel.PointDTO;
import com.muyer.mapplanning.res.Position;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description: 
 * date: 2022/5/13 13:04
 * @author YeJiang
 * @version
 */
public class IntersectPointUtil {

    public static void main(String[] args) throws Exception {
        List<String> names = Lists.newArrayList("济宁市-益阳市", "济南市-青岛市", "济南市-长沙市", "济宁市-随州市", "济宁市-沈阳市", "济南市-随州市", "济宁市-石家庄市", "济宁市-沧州市", "济宁市-济宁市", "西安市-唐山市", "济宁市-徐州市", "西安市-十堰市", "济南市-徐州市", "济宁市-邢台市", "长春市-石家庄市", "长春市-随州市", "北京市-连云港市", "西安市-德州市", "济宁市-昆明市", "西安市-临沂市", "济宁市-乌鲁木齐市", "济宁市-长春市", "西安市-潍坊市", "西安市-青岛市", "济宁市-邯郸市", "十堰市-伊犁哈萨克自治州", "济宁市-无锡市", "西安市-巴彦淖尔市", "济宁市-唐山市", "济南市-漳州市", "西安市-锡林郭勒盟", "西安市-洛阳市", "西安市-济宁市", "济宁市-南通市", "济南市-苏州市", "西安市-益阳市", "长春市-徐州市", "济南市-滁州市", "济宁市-青岛市", "济南市-贵阳市", "西安市-烟台市", "重庆市-伊犁哈萨克自治州", "长春市-洛阳市", "济宁市-秦皇岛市", "济宁市-天津市", "济南市-阜阳市", "长春市-上海市", "重庆市-石家庄市");

        //预热数据
        PlanningUtil.before();
        List<LineDTO> excelList = Lists.newArrayList();
        //遍历每个路径
        PlanningUtil.fileList.parallelStream().forEach(fileName -> {
            String endCityName = fileName.replace("货车导航轨迹.txt", "");
            if (!names.contains(endCityName)) {
                //System.out.println(endCityName);
                return;
            }
            //行车轨迹
            List<Position> orderPosList = null;
            try {
                orderPosList = GasStationMain.getOrderPosList(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //稀疏一点的轨迹
            List<Position> sparsePosList = Lists.newArrayList();
            for (Position o : orderPosList) {
                if (CollectionUtils.isEmpty(sparsePosList)) {
                    sparsePosList.add(o);
                    continue;
                }
                Position position = sparsePosList.get(sparsePosList.size() - 1);
                if (DistanceUtil.getDistance(position.getLng(), position.getLat(), o.getLng(), o.getLat()) < 5000) {
                    continue;
                }
                sparsePosList.add(o);
            }

            //System.out.println(orderPosList.size());
            //System.out.println(sparsePosList.size());
            StringBuilder sb = new StringBuilder();
            int count = 1;
            for (Position p : sparsePosList) {
                sb.append(p.getLngLat()).append(",");
                if (count % 2 == 0) {
                    //10个一条数据
                    LineDTO line = new LineDTO();
                    line.setLatLng(sb.toString().substring(0, sb.toString().length() - 1));
                    line.setLat(p.getLat());
                    line.setLng(p.getLng());
                    line.setPartition(fileName);
                    excelList.add(line);
                    sb = new StringBuilder();
                    sb.append(p.getLngLat()).append(",");
                }
                count++;
            }

        });
        //File file = new File("F:" + File.separator + "团油数据\\路线-地图map.xlsx");
        //EasyExcel.write(file, LineDTO.class).sheet("模板").doWrite(excelList);
        System.out.println(excelList.size());

        List<LineDTO> lineList = Lists.newArrayList();
        for (int i = 0; i < excelList.size(); i++) {
            LineDTO lineDTO = excelList.get(i);
            List<LineDTO> subList = excelList.stream()
                    .filter(o -> Objects.nonNull(o) &&
                            !o.getPartition().equals(lineDTO.getPartition()))
                    .collect(Collectors.toList());
            int count = 0;
            for (LineDTO p : subList) {
                if (DistanceUtil.getDistance(lineDTO.getLng(), lineDTO.getLat(), p.getLng(), p.getLat()) < 5000) {
                    count++;
                }
            }
            if (count > 3) {
                lineList.add(lineDTO);
            }
        }

        List<PointDTO> points = Lists.newArrayList();

        for (LineDTO p : lineList) {
            System.out.println(p.getLng() + "," + p.getLat());
            PointDTO pointDTO = new PointDTO();
            pointDTO.setLngLat(p.getLng() + "," + p.getLat());
            points.add(pointDTO);
        }
        File file = new File("F:" + File.separator + "团油数据\\交叉点map.xlsx");
        EasyExcel.write(file, PointDTO.class).sheet("模板").doWrite(points);

    }
}


