package com.muyer.controller;

import com.google.common.collect.Maps;
import com.muyer.util.CaptchaUtil;
import com.muyer.util.HttpServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Description: 
 * date: 2021/6/30 22:06
 * @author YeJiang
 * @version
 */
@Controller
public class BackController {

    /** 是否需要验证码 ，可做成配置项 **/
    public static boolean isCaptcha = true;

    /**
     * 跳转登录页面
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        //此项可作为配置项
        model.addAttribute("isCaptcha", isCaptcha);
        return "/login";
    }


    /**
     * 用户登录接口 ， 验证登录
     * @param username
     * @param password
     * @param captcha
     * @param rememberMe
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String username, String password, String captcha, String rememberMe, HttpServletRequest request) {
        Map<String, Object> result = Maps.newHashMap();
        //用户名字和密码不能为空 ==> 用户名和密码不能为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            result.put("code", "400");
            result.put("msg", "注意：用户名和密码不能为空！");
            return result;
        }
        //判断验证码是否正确 ==> 验证码错误
        String captchaCode = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(captchaCode) ||
                StringUtils.isEmpty(captcha) ||
                !StringUtils.equals(captcha.toLowerCase(), captchaCode.toLowerCase())) {
            result.put("code", "400");
            result.put("msg", "注意：验证码错误！");
            return result;
        }
        //验证密码和权限
        if (!(StringUtils.equals(username, "admin") && StringUtils.equals(password, "123456"))) {
            result.put("code", "400");
            result.put("msg", "注意：登录账号秘密错误！");
            return result;
        }
        result.put("code", "200");
        result.put("msg", "登录成功");
        result.put("data","/");
        return result;

    }


    /**
     * 验证码图片
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置响应头信息，通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");
        response.setContentType("image/jpeg");

        // 获取验证码
        String code = CaptchaUtil.getRandomCode();
        // 将验证码输入到session中，用来验证
        request.getSession().setAttribute("captcha", code);
        // 输出到web页面
        ImageIO.write(CaptchaUtil.genCaptcha(code), "jpg", response.getOutputStream());
    }
}
