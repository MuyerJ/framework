package com.muyer.controller;

import com.muyer.util.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 
 * date: 2021/6/30 22:06
 * @author YeJiang
 * @version
 */
@Controller
public class BackController {
    /**
     * 跳转登录页面
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String login(Model model) {
        //此项可作为配置项
        model.addAttribute("isCaptcha", true);
        return "/login";
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
