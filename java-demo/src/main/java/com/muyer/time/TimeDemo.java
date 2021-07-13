package com.muyer.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: 
 * date: 2021/7/13 20:44
 * @author YeJiang
 * @version
 */
public class TimeDemo {

    public static void main(String[] args) throws ParseException {
        Calendar now = Calendar.getInstance();
        System.out.println("年: " + now.get(Calendar.YEAR));
        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));
        System.out.println("分: " + now.get(Calendar.MINUTE));
        System.out.println("秒: " + now.get(Calendar.SECOND));
        System.out.println("当前时间毫秒数：" + now.getTimeInMillis());
        System.out.println(now.getTime());

        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        System.out.println("格式化后的日期：" + dateNowStr);
        //要跟上面sdf定义的格式一样
        String str = "2012-1-13 17:26:33";
        Date today = sdf.parse(str);
        System.out.println("字符串转成日期：" + today);


        /*======*/
        System.out.println(TimeHelper.getDay());
        System.out.println(TimeHelper.getMonth());
        System.out.println(TimeHelper.getYear());
        System.out.println(TimeHelper.parseDate("2021-01-01 01:00:00"));
        System.out.println(TimeHelper.parseDateStr(new Date()));
    }
}
