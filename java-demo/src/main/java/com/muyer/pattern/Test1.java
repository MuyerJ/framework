package com.muyer.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 
 * date: 2021/3/15 14:18
 * @author YeJiang
 * @version
 */
public class Test1 {
    static Pattern p = Pattern.compile("(?<=\\d：).*");

    public static void main(String[] args) {
        // 创建 Matcher 对象
        Matcher m = p.matcher("1：我\n2：是\n3：中\n4：国\n5：人");
        while (m.find()) {
            System.out.println(m.group().trim());
        }
    }
}
