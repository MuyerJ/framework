package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.muyer.mapplanning.excel.K56DTO;
import com.muyer.mapplanning.excel.KaiPiaoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * Description: 
 * date: 2022/4/5 12:32
 * @author YeJiang
 * @version
 */
public class K56Main {
    static Writer writer;

    static {
        File file = new File("F:" + File.separator + "updateSql.sql");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //加true是追加，不加true就是覆盖了
        try {
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception {
        List<K56DTO> list = EasyExcel.read(new File("F:\\aa.xlsx")).autoTrim(true)
                .head(K56DTO.class).sheet().doReadSync();

        Map<String, List<K56DTO>> map = Maps.newHashMap();

        for (K56DTO dto : list) {
            if (dto.getMasterFlag() == 0) {
                continue;
            }
            List<K56DTO> k56DTOS = map.get(dto.getTempLicense());
            if (CollectionUtils.isEmpty(k56DTOS)) {
                k56DTOS = Lists.newArrayList();
            }
            k56DTOS.add(dto);
            map.put(dto.getTempLicense(), k56DTOS);
        }

        Map<String, List<K56DTO>> resultMap = Maps.newHashMap();

        for (String key : map.keySet()) {
            List<K56DTO> kList = map.get(key);
            if (kList.size() == 1) {
                continue;
            }

            resultMap.put(key,kList);
        }

        writer.close();
    }

    public boolean isOverlap(K56DTO d1, K56DTO d2) {
        if (d2.getStartTruckTime() >= d1.getStartTruckTime() && d1.getReturnTruckTime() <= d2.getReturnTruckTime()) {
            return true;
        }
        return false;
    }

}
