package com.muyer.spider;

import java.util.HashMap;

/**
 * Description:
 * date: 2021/1/5 18:47
 *
 * @author YeJiang
 */
public class Main {
    public static void main(String[] args) {
        ///v2/grab-order/aggregation-list
        HashMap<String, String> map = new HashMap<>();
        map.put("token","040f90e48556f77a6e48fb6ac76dde65");
        map.put("app_ver","1.5.2");
        map.put("timestamp","2021-01-04 20:22:22");
        map.put("mac",null);
        map.put("model","OPPO-PDVM00");
        map.put("open_udid","3fefde54-6f61-491d-861b-cacb7d1c1569");
        map.put("os","OPPO29,10");
        for (int i = 0; i < 1; i++) {
            Request post = new Request().post("https://api.edipao.cn/api/driver/grab/v2/grab-order/aggregation-list", map);
            System.out.println(post.getContent());
        }
    }
}
