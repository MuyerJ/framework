package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import com.muyer.mapplanning.excel.LineDTO;
import com.muyer.mapplanning.res.Position;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Description: 
 * date: 2022/4/11 14:41
 * @author YeJiang
 * @version
 */
public class PlanningMapUtil {
    public static void main(String[] args) throws Exception {
        //预热数据
        PlanningUtil.before();
        List<LineDTO> excelList = Lists.newArrayList();
        //遍历每个路径
        PlanningUtil.fileList.parallelStream().forEach(fileName -> {
            if (!fileName.contains("青岛市-")) {
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

            System.out.println(orderPosList.size());
            System.out.println(sparsePosList.size());
            StringBuilder sb = new StringBuilder();
            int count = 1;
            for (Position p : sparsePosList) {
                sb.append(p.getLngLat()).append(",");
                if (count % 2 == 0) {
                    //10个一条数据
                    LineDTO line = new LineDTO();
                    line.setLatLng(sb.toString().substring(0, sb.toString().length() - 1));
                    excelList.add(line);
                    sb = new StringBuilder();
                    sb.append(p.getLngLat()).append(",");
                }
                count++;
            }

        });
        File file = new File("F:" + File.separator + "团油数据\\青岛市-地图map.xlsx");
        EasyExcel.write(file, LineDTO.class).sheet("模板").doWrite(excelList);
    }

}
