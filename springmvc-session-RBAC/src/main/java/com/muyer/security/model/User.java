package com.muyer.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Description: 
 * date: 2021/6/16 0:44
 * @author YeJiang
 * @version
 */
@Data
@AllArgsConstructor
public class User {

    private String id;

    private String username;

    private String password;

    private String phone;

    private String fullname;

    private List<String> rbacSet;
}
