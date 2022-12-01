package com.muyer.time_test;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Description: 
 * date: 2021/9/8 22:34
 * @author YeJiang
 * @version
 */
@Builder
@Data
public class Users {
    /**
     * 自增唯一id
     */
    private Long id;

    /**
     * date类型的时间
     */
    private Date timeDate;

    /**
     * timestamp类型的时间
     */
    private Timestamp timeTimestamp;

    /**
     * long类型的时间
     */
    private long timeLong;
}
