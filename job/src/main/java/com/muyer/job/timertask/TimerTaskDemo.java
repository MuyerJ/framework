package com.muyer.job.timertask;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:
 *  TimerTask方式实现定时任务
 * date: 2021/7/11 13:51
 * @author YeJiang
 * @version
 */
public class TimerTaskDemo {

    static long count = 0;

    public static void main(String[] args) {
        //任务,天数,秒数
        new Timer().scheduleAtFixedRate(getTask(),0,1000);
    }

    public static TimerTask getTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                count++;
                System.out.println(count);
            }
        };

        return timerTask;
    }
}
