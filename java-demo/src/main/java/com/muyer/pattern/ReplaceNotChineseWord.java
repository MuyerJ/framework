package com.muyer.pattern;

/**
 * Description:
 *  去除所有非中文字符
 * date: 2021/6/30 21:48
 * @author YeJiang
 * @version
 */
public class ReplaceNotChineseWord {

    public static void main(String[] args) {
        String name = "叶123Ye***江++--Jiang".replaceAll("[^\u4E00-\u9FA5]", "");
        System.out.println(name);
    }
}
