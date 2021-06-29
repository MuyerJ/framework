package com.muyer.util;

import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Description:
 * date: 2021/6/29 23:08
 * @author YeJiang
 * @version
 */
public class SmsTools {
    //todo 待提供
    private final static String url = "";
    private final static Integer TIMEOUT = 5000;


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            send("两周年","15844528058","旭宝,江粑爱你哦，两周年快乐！");
        }
    }

    public static void send(String sense, String phone, String msg) {
        StringBuilder sb = new StringBuilder("发送短信|");
        sb.append(sense).append("|");
        sb.append(phone).append("|");
        sb.append(msg).append("|");
        String response;
        HttpURLConnection con = null;
        try {
            Map<String, String> parameters = Maps.newLinkedHashMap();
            parameters.put("sn", "SDK-WKS-010-00640");
            parameters.put("pwd", "855712");
            parameters.put("mobile", phone);
            parameters.put("content", msg + "【两周年纪念】");
            con = (HttpURLConnection) new URL(url + "?" + ParameterStringBuilder.getParamsString(parameters)).openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(TIMEOUT);
            con.setReadTimeout(TIMEOUT);
            response = con.getResponseMessage();
        } catch (Exception e) {
            response = "Error:" + e.getMessage();
        }finally{
            if(con!=null){
                con.disconnect();
            }
        }
        sb.append(response).append("|");
        System.out.println(sb);
    }

    private static class ParameterStringBuilder {
        public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            String enc = "GBK";

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), enc));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), enc));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
        }
    }
}
