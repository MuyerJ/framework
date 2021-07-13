package com.muyer.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: 
 * date: 2021/7/13 20:47
 * @author YeJiang
 * @version
 */
public class TimeHelper {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static int getYear(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    public static int getMonth(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MONTH) + 1;
    }

    public static int getDay(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    public static int getMinute(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MINUTE);
    }


    public static int getSeconds(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.SECOND);
    }

    public static Date parseDate(String dateStr){
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseDateStr(Date date){
        return sdf.format(date);
    }
}
