package com.muyer.security.controller;

import com.muyer.security.model.AuthenticationReq;
import com.muyer.security.model.User;
import com.muyer.security.service.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 
 * date: 2021/6/16 0:55
 * @author YeJiang
 * @version
 */

@RestController
public class LoginController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @RequestMapping(value = "/login", produces = "text/plain;charset=utf-8")
    public String loginRequest(AuthenticationReq req) {
        User authentication = authenticationService.authentication(req);
        return authentication.getFullname() + "登录成功";
    }
}
