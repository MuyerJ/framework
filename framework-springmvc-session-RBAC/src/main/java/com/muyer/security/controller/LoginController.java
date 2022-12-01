package com.muyer.security.controller;

import com.muyer.security.model.AuthenticationReq;
import com.muyer.security.model.User;
import com.muyer.security.service.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Description: 
 * date: 2021/6/16 0:55
 * @author YeJiang
 * @version
 */

@RestController
public class LoginController {

    public static final String USER_SESSION_KEY = "userSessionKey";

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @RequestMapping(value = "/login", produces = "text/plain;charset=utf-8")
    public String loginRequest(AuthenticationReq req, HttpSession httpSession) {
        User authentication = authenticationService.authentication(req);
        //将登陆人信息存入到session
        httpSession.setAttribute(USER_SESSION_KEY, authentication);
        return authentication.getFullname() + "登录成功";
    }

    @GetMapping(value = "/logout", produces = "text/plain;charset=utf-8")
    public String logout(HttpSession session) {
        session.invalidate();
        return "退出登录";
    }

    @GetMapping(value = "/r/r1", produces = "text/plain;charset=utf-8")
    public String r1(HttpSession session) {
        Object sessionObj = session.getAttribute(USER_SESSION_KEY);
        if (Objects.isNull(sessionObj)) {
            return "匿名访问资源r1";
        } else {
            User u = (User) sessionObj;
            return u.getFullname() + "访问资源r1";
        }
    }

    @GetMapping(value = "/r/r2", produces = "text/plain;charset=utf-8")
    public String r2(HttpSession session) {
        Object sessionObj = session.getAttribute(USER_SESSION_KEY);
        if (Objects.nonNull(sessionObj)) {
            return "匿名访问资源r2";
        } else {
            User u = (User) sessionObj;
            return u.getFullname() + "访问资源r2";
        }
    }


}
