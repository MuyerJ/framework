package com.muyer.time_test;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * Description: 
 * date: 2021/9/8 22:33
 * @author YeJiang
 * @version
 */
@Mapper
public interface UsersMapper {
    @Insert("insert into users(time_date, time_timestamp, time_long) value(#{timeDate}, #{timeTimestamp}, #{timeLong})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveUsers(Users users);
}
