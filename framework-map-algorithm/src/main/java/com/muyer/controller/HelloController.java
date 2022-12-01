package com.muyer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Description: 
 * date: 2022/4/13 18:08
 * @author YeJiang
 * @version
 */
@RestController
public class HelloController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/driverList")
    public List<Map<String, Object>> userList() {
        String sql = "select * from t_driver limit 1";
        List<Map<String, Object>> list_maps = jdbcTemplate.queryForList(sql);
        return list_maps;
    }

}
