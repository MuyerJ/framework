package com.muyer.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.muyer.entity.City;
import com.muyer.entity.User;
import com.muyer.repository.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 
 * date: 2021/6/21 21:55
 * @author YeJiang
 * @version
 */

@RestController
public class DataController {

    static List<User> users = Lists.newArrayList();

    static {
        for (int i = 0; i < 20; i++) {
            users.add(new User(i, "yejiang" + i, "e" + i, "男", "四川", "ok", "java", "1.1.1.1", "3" + i, "20210505212422"));
        }
    }

    @Autowired
    private CityDao cityDao;

    @RequestMapping(value = "/data/table/user", method = RequestMethod.GET)
    public JSONObject getGoodsList(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize) {
        JSONObject result = new JSONObject();
        try {
            result.put("code", "0");
            result.put("msg", "操作成功！");
            result.put("data", users);
            result.put("count", users.size());
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "查询异常！");
        }
        return result;
    }


    @RequestMapping(value = "/data/city/get", method = RequestMethod.GET)
    public Map<String, Object> getCityList(@RequestParam("page") int pageIndex, @RequestParam("limit") int pageSize) {
        PageRequest pageReq = PageRequest.of(pageIndex - 1, pageSize);
        Page<City> all = cityDao.findAll(pageReq);
        List<City> cityList = all.getContent();

        Map<String,Object> resultMap=new HashMap<>();
        // 设置layui要求的格式，以下都是
        resultMap.put("code", 0);
        resultMap.put("count", all.getTotalElements());
        resultMap.put("data", cityList);
        return resultMap;
    }
}
