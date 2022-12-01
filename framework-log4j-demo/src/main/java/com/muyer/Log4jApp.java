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
    public static void main(String args[]) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            log.trace("log4j trace log" + i);
            log.debug("log4j debug log" + i);
            log.info("log4j info log" + i);
            log.warn("log4j warn log" + i);
            log.error("log4j error log" + i, new RuntimeException("Runtime Exception"));
            if (i > 1000) {
                Thread.sleep(i);
            }
        }
    }

    private static void test30000logs() {
        long startTime = System.currentTimeMillis();
        System.out.println("写日志完成-开始：" + startTime);
        for (int i = 0; i < 30000; i++) {
            log.info(i + "," + System.currentTimeMillis() + ",yejiangtest2,备注字段内容2W-from-log4j-" + i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("写日志完成-结束：" + endTime);
        // 2个shard，3万条日志写入用时约1.58秒
        System.out.println("写日志完成-用时：" + (endTime - startTime));
    }
}
