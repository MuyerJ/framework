package com.muyer.mapplanningv2;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

/**
 * Description: 
 * date: 2022/4/13 11:43
 * @author YeJiang
 * @version
 */
public class PlanningV2 {

    public static void main(String[] args) throws Exception {
        List<RouteDTO> list = Lists.newArrayList();
        Reader reader = new FileReader("F:\\团油数据\\订单数量.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr;
        while ((lineStr = bufferedReader.readLine()) != null) {
            String[] split = lineStr.split(",");
            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setStart(split[0]);
            routeDTO.setEnd(split[1]);
            routeDTO.setCount(Integer.parseInt(split[2]));
            list.add(routeDTO);
        }
        bufferedReader.close();
        reader.close();

        List<RouteDTO> cc = Lists.newArrayList();
        List<RouteDTO> jnan = Lists.newArrayList();
        List<RouteDTO> jning = Lists.newArrayList();
        for (RouteDTO routeDTO : list) {
            String start = routeDTO.getStart();
            if (StringUtils.contains(start, "长春")) {
                cc.add(routeDTO);
                continue;
            }
            if (StringUtils.contains(start, "济宁")) {
                jning.add(routeDTO);
                continue;
            }
            if (StringUtils.contains(start, "济南")) {
                jnan.add(routeDTO);
            }
        }
        System.out.println(cc);
        System.out.println(jnan);
        System.out.println(jning);


    }
}
