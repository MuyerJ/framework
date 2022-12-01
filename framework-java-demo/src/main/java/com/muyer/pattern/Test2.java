package com.muyer.pattern;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: 
 * date: 2021/3/16 13:48
 * @author YeJiang
 * @version
 */
public class Test2 {
    public static void main(String[] args) {
        Map<String, String> mp = new HashMap<>();
        mp.put("taskDbId", "外呼任务编号111");
        mp.put("expDt", "项目有效期111");
        mp.put("bizType", "业务类型111");
        mp.put("projectNm", "项目名称111");
        mp.put("mercIdReferralCode", "客户编号111");
        mp.put("PHONE", "注册手机号111");
        mp.put("cteNm", "创建人111");
        mp.put("cteDt", "创建日期111");
        mp.put("firstHandleDt", "首次办理时间111");
        mp.put("handleNm", "办理人111");
        mp.put("handleCompleteDt", "完成时间111");
        mp.put("status", "处理状态111");
        mp.put("taskDb", "[{\"商编\":\"700000007372051\"},{\"注册名称\":\"刘忠红 \"},{\"最后一次交易日期\":\"20201112 \"},{\"此前费率\":\"0.55000000000000004\"},{\"奖励\":\"0.5+3\"},{\"渠道\":\"03\"},{\"回访的坐席姓名\":\"王彤彤\"},{\"是否接通\":\"是\"},{\"是否配合\":\"是\"},{\"交易时间\":\"近半个月\"},{\"不使用的原因\":\"没需求\"}]");
        mp.put("taskContent", "{\"label\":\"{\\\"total\\\":2,\\\"rows\\\":[{\\\"templateType\\\":\\\"\\\",\\\"labelKey\\\":\\\"标签\\\",\\\"labelValue\\\":\\\"问题一/问题二/问题三\\\",\\\"uuid\\\":\\\"4fdccb6b8dc74f2dbacd63c0e5c94348\\\"},{\\\"templateType\\\":\\\"00\\\",\\\"labelKey\\\":\\\"是否接通\\\",\\\"labelValue\\\":\\\"是/否\\\",\\\"uuid\\\":\\\"7f3f698d07b94f78b2f14873f6fb1162\\\"}]}\",\"problem\":\"{\\\"total\\\":3,\\\"rows\\\":[{\\\"problem\\\":\\\"调研问题三\\\",\\\"uuid\\\":\\\"8e5c06241387496ba15e9b109e4a35d7\\\"},{\\\"problem\\\":\\\"调研问题二\\\",\\\"uuid\\\":\\\"f19c6421a78e4409b2feac71a3735084\\\"},{\\\"problem\\\":\\\"调研问题一\\\",\\\"uuid\\\":\\\"fb2ee6c1fb8e4f54b8675033be2ab898\\\"}]}\"}");

        String taskDbStr = mp.get("taskDb");
        List list = null;
        try {
            list = JSON.parse(taskDbStr, List.class);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, String> tempMap = new ConcurrentHashMap<>();
        list.parallelStream().forEach(obj->{
            Map<String, String> stringStringMap = (Map<String, String>) obj;
            tempMap.putAll(stringStringMap);
        });
        System.out.println("====================转成的map长度为：" + tempMap.keySet().size());
        mp.putAll(tempMap);
        System.out.println("mp===================" + mp);
    }
}
