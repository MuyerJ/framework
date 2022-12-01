package com.muyer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description: 
 * date: 2021/6/21 21:57
 * @author YeJiang
 * @version
 */

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String email;
    private String sex;
    private String city;
    private String sign;
    private String experience;
    private String ip;
    private String logins;
    private String joinTime;

}
