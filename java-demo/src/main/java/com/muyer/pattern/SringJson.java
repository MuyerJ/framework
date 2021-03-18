package com.muyer.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 
 * date: 2021/3/6 18:17
 * @author YeJiang
 * @version
 */

/**
 *
 * Pattern
 *  public static Pattern compile(String regex)
 *
 * Matcher
 *  public Matcher matcher(CharSequence input)
 *  public boolean find()尝试找到匹配模式的输入序列的下一个子序列
 *  public String group()返回与上一个匹配匹配的输入子序列
 *
 *
 *
 */
public class SringJson {
    public static void main(String[] args) {
        String josn = "{\"access_token\": \"IGQVJXekVYR1ZAiNU5EdHdmWDZATVm1yeldlTUxLZA2tqQ1B5c01wSjBWQy1rSWhjckZAvTzZAHcHh5S29ieTF0VmJ4b1lfQVlablFuMXJ4SGdTOHlETVNtbDE3TmJpNnZAEME1URTBZAOS1UNmw4dnliMjI3UkpQc0I3aWFPUWpN\", \"user_id\": 17841401135016050}";
        System.out.println(findJosnValue("access_token", josn));
    }

    /*
     * 根据key和josn字符串取出特定的value
     */
    public static String findJosnValue(String key, String josn) {
        String regex = "\"" + key + "\": (\"(.*?)\"|(\\d*))";
        Matcher matcher = Pattern.compile(regex).matcher(josn);
        String value = null;
        if (matcher.find()) {
            value = matcher.group().split("\\:")[1].replace("\"", "").trim();
            System.out.println(value);
        }
        return value;
    }
}
