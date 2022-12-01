package com.muyer.security.service;

import com.muyer.security.model.AuthenticationReq;
import com.muyer.security.model.User;

/**
 * Description: 
 * date: 2021/6/16 0:42
 * @author YeJiang
 * @version
 */
public interface AuthenticationService {

    User authentication(AuthenticationReq req);
}
