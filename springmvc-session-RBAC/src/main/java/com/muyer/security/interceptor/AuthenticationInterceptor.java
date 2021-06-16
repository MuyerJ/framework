package com.muyer.security.interceptor;

import com.muyer.security.controller.LoginController;
import com.muyer.security.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Description: 
 * date: 2021/6/16 18:22
 * @author YeJiang
 * @version
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Object userObj = request.getSession().getAttribute(LoginController.USER_SESSION_KEY);
        //校验权限
        if (Objects.isNull(userObj)) {
            System.out.println("===========please login");
            return false;
        }
        User user = (User) userObj;
        if (user.getRbacSet().contains("r1") && request.getRequestURI().contains("r1")) {
            return true;
        }

        if (user.getRbacSet().contains("r2") && request.getRequestURI().contains("r2")) {
            return true;
        }
        System.out.println("===========No permission denied access");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
