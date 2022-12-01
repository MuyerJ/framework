package com.muyer.wechat;

import com.alibaba.fastjson.JSONObject;
import com.muyer.wechat.util.WXUtils;

import java.util.Map;
import java.util.Objects;

/**
 * Description: 
 * date: 2021/8/24 22:54
 * @author YeJiang
 * @version
 */
public class MiniApp {

    public static void main(String[] args) {
        String jsCode = "";//todo 待微信小程序提供
        String encryptedData = "";//todo 待微信小程序提供
        String iv = "";//todo 待微信小程序提供
        String appId = "";//todo 待微信小程序提供
        String appSecret = "";//todo 待微信小程序提供
        //1.getSession
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" +
                jsCode + "&grant_type=authorization_code";
//        String resWeiXin = httpClientUtils.httpCallGet(url);
        String resWeiXin = "";//todo 待调用Get请求
        Map map = JSONObject.parseObject(resWeiXin, Map.class);
        String phone = "";
        try {
            //2.parse
            Object sessionKey = map.get("session_key");
            JSONObject jsonObject = WXUtils.decode(
                    Objects.nonNull(sessionKey)?sessionKey.toString():"",
                    encryptedData,
                    iv);
            if(Objects.nonNull(jsonObject)){
                phone = jsonObject.getString("phoneNumber");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
