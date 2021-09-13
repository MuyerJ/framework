package com.muyer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 
 * date: 2021/9/13 22:24
 * @author YeJiang
 * @version
 */
@Data
@Slf4j
public class Log4jApp {
    public static void main(String args[])
    {
        long startTime = System.currentTimeMillis();
        System.out.println("写日志完成-开始："+startTime);
        for(int i=0;i<30000;i++){
            log.info(i+"," +System.currentTimeMillis()+ ",备注字段内容2W-from-log4j-"+i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("写日志完成-结束："+endTime);
        // 2个shard，3万条日志写入用时约1.58秒
        System.out.println("写日志完成-用时："+(endTime-startTime));
    }
}
