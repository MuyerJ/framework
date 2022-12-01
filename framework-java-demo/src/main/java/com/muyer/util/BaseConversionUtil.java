package com.muyer.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Description: 
 * date: 2021/3/23 15:55
 * @author YeJiang
 * @version
 */
public class BaseConversionUtil {

    public static void main(String[] args) throws Exception {
        String s = "\\xAC\\xED\\x00\\x05";
        System.out.println(decodeUTF8Str(s));
    }


    /**
     * 转化十六进制编码为字符串
     * @param xStr
     * @return
     */
    public static String decodeUTF8Str(String xStr) throws UnsupportedEncodingException {
        return URLDecoder.decode(xStr.replaceAll("\\\\x", "%"), "utf-8");

    }

}
