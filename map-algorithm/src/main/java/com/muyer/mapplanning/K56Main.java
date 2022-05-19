package com.muyer.mapplanning;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.muyer.mapplanning.excel.K56DTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:
 *  订单号
 *  客户全称
 *  发车时间
 *  收车时间
 *  临牌号
 * date: 2022/4/5 12:32
 * @author YeJiang
 * @version
 */
public class K56Main {
    private static final TimeZone TIMEZONE = TimeZone.getTimeZone("GMT+8");
    static Writer writer;
    static Set<String> questionSet = Sets.newHashSet();

    public static void main(String[] args) throws Exception {
        List<K56DTO> list = EasyExcel.read(new File("F:\\www.xlsx")).autoTrim(true)
                .head(K56DTO.class).sheet().doReadSync();

        Map<String, List<K56DTO>> map = Maps.newHashMap();
        Map<String, K56DTO> map2 = Maps.newHashMap();

        for (K56DTO dto : list) {
            map2.put(dto.getOrderNo(), dto);
            if (dto.getMasterFlag() == 0 || dto.getStartTruckTime() < 20210000000000L) {
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

        int count = 0;
        for (String key : map.keySet()) {
            List<K56DTO> kList = map.get(key);
            if (kList.size() == 1) {
                continue;
            }
            count += kList.size();
            sort(kList);
            resultMap.put(key, kList);
        }


        //组装数据写入Excel
        List<K56DTO> excelList = Lists.newArrayList();
        for (String orderNo : questionSet) {
            K56DTO k56DTO = map2.get(orderNo);
            if (Objects.isNull(orderNo)) {
                continue;
            }
            k56DTO.setT1(format(k56DTO.getStartTruckTime()));
            k56DTO.setT2(format(k56DTO.getReturnTruckTime()));
            excelList.add(k56DTO);
        }


        File file2 = new File("F:" + File.separator + "临牌订单数据.xlsx");
        EasyExcel.write(file2, K56DTO.class).sheet("数据").doWrite(excelList);

    }

    public static void sort(List<K56DTO> kList) {
        for (int i = 0; i < kList.size() - 1; i++) {
            for (int j = 0; j < kList.size() - 1 - i; j++) {
                K56DTO d1 = kList.get(j);
                K56DTO d2 = kList.get(j + 1);
                if (!(d2.getReturnTruckTime() <= d1.getStartTruckTime() || d2.getStartTruckTime() >= d1.getReturnTruckTime())) {
                    questionSet.add(d1.getOrderNo());
                    questionSet.add(d2.getOrderNo());
                }
            }
        }
    }

    public static String format(Date date, String format) {
        Preconditions.checkArgument(date != null, "Param date must be not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(format), "Param format must be not null or empty");
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    public static String format(long dateTime) {
        if (dateTime <= 0) {
            return "";
        }
        try {
            return DateUtils.format(parse(dateTime), "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            return "";
        }
    }

    public static Date parse(long format) {
        String str = String.valueOf(format);
        if (str.length() >= 8) {
            Calendar calendar = Calendar.getInstance(TIMEZONE);
            calendar.clear();
            calendar.set(1, Integer.parseInt(str.substring(0, 4)));
            calendar.set(2, Integer.parseInt(str.substring(4, 6)) - 1);
            calendar.set(5, Integer.parseInt(str.substring(6, 8)));
            if (str.length() >= 14) {
                calendar.set(11, Integer.parseInt(str.substring(8, 10)));
                calendar.set(12, Integer.parseInt(str.substring(10, 12)));
                calendar.set(13, Integer.parseInt(str.substring(12, 14)));
            }

            if (str.length() == 17) {
                calendar.set(14, Integer.parseInt(str.substring(14, 17)));
            }

            return calendar.getTime();
        } else {
            return null;
        }
    }


}
