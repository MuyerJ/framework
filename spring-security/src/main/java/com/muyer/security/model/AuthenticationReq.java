package com.muyer.security.model;

import lombok.Data;

/**
 * Description: 
 * date: 2021/6/16 0:44
 * @author YeJiang
 * @version
 */

@Data
public class AuthenticationReq {

    private String username;
    private String password;
}
