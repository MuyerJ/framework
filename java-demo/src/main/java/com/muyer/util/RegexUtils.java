package com.muyer.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 
 * date: 2021/5/15 19:33
 * @author YeJiang
 * @version
 */
public class RegexUtils {

    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String REGION = "region";
    private static final Pattern ADDRESS_PATTERN1 = Pattern.compile("(?<province>[^省]+省|.+自治区)(?<city>.+自治州|[^市]+市|[^区]+区|[^县]+县)(?<region>[^市]+市|[^区]+区|[^县]+县)?");
    private static final Pattern ADDRESS_PATTERN2 = Pattern.compile("(?<city>上海市|北京市|天津市|重庆市)(?<region>[^区]+区|[^县]+县)?");

    private static final Pattern LETTER_DIGIT_PATTERN = Pattern.compile("^[a-z0-9A-Z]+$");


    public static Map<String, String> addressPattern(String address) {
        if (StringUtils.isEmpty(address)) {
            return Maps.newHashMap();
        }
        Matcher m;
        String city, region, province;
        Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
        if (address.contains("上海市") || address.contains("北京市") || address.contains("天津市") || address.contains("重庆市")) {
            m = ADDRESS_PATTERN2.matcher(address);
            while (m.find()) {
                city = m.group(RegexUtils.CITY);
                map.put(RegexUtils.CITY, city == null ? "" : city.trim());
                region = m.group(RegexUtils.REGION);
                map.put(RegexUtils.REGION, region == null ? "" : region.trim());
            }
        } else {
            m = ADDRESS_PATTERN1.matcher(address);
            while (m.find()) {
                province = m.group(RegexUtils.PROVINCE);
                map.put(RegexUtils.PROVINCE, province == null ? "" : province.trim());
                city = m.group(RegexUtils.CITY);
                String cityStr = city == null ? "" : city.trim();
                if (cityStr.contains("县")) {
                    map.put(RegexUtils.REGION, cityStr);
                } else {
                    map.put(RegexUtils.CITY, cityStr);
                    region = m.group(RegexUtils.REGION);
                    map.put(RegexUtils.REGION, region == null ? "" : region.trim());
                }

            }
        }
        return map;
    }

    public static boolean isLetterDigit(String str) {
        return LETTER_DIGIT_PATTERN.matcher(str).matches();
    }

}
