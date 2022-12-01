package com.muyer.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 
 * date: 2021/3/15 14:21
 * @author YeJiang
 * @version
 */
public class Test {
    static Pattern p = Pattern.compile("(?<=\\{\").+?(?=\":)");
    static Pattern p2 = Pattern.compile("(?<=\\+).+?(?=小时)");

    public static void main(String[] args) {
        String str ="时间+0.11122小时";
        Matcher matcher = p2.matcher(str);
        if(matcher.find()){
            System.out.println(matcher.group().trim());
        }
    }

    private static void fun1() {
        String str = "[\n" +
                "    {\n" +
                "        \"商编\":\"700000007372051\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"注册名称\":\"刘忠红 \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"最后一次交易日期\":\"20201112 \"\n" +
                "    },\n" +
                "    {\n" +
                "        \"此前费率\":\"0.55000000000000004\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"奖励\":\"0.5+3\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"渠道\":\"03\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"回访的坐席姓名\":\"王彤彤\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"是否接通\":\"是\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"是否配合\":\"是\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"交易时间\":\"近半个月\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"不使用的原因\":\"没需求\"\n" +
                "    }\n" +
                "]";
        System.out.println(str);
        str = str.replace("\n","").replace(" ","");
        System.out.println(str);
        Matcher matcher = p.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group().trim());
        }
    }
}

