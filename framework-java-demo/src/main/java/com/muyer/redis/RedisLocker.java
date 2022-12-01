package com.muyer.redis;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Description: 
 * date: 2021/3/23 14:24
 * @author YeJiang
 * @version
 */
public class RedisLocker {
//
//
//    /**
//     * 锁的key前缀,整个lock key是applicationName:lock:key
//     */
//    public static final String LOCK_KEY_PREFIX = "lock" + RedisConfig.KEY_DELIMITER;
//    /**
//     * 默认请求锁成功后的有效期,60秒
//     */
//    public static final long DEFAULT_LOCK_EXPIRE = 60 * 1000;
//    @Autowired
//    private RedisTemplateWarpper redisTemplateWarpper;
//
//    /**
//     * 加锁,使用默认的锁有效时间
//     *
//     * @param key - key名称
//     * @return return null is lock failed Otherwise return uuid value of lock-key
//     */
//    public String lock(String key) {
//        return this.lock(key, DEFAULT_LOCK_EXPIRE);
//    }
//
//    /**
//     * 加锁
//     *
//     * @param key               - key名称
//     * @param expireMillisecond - 锁成功后的有效期,毫秒
//     * @return return null or empty string is lock failed Otherwise return uuid value of lock-key
//     */
//    public String lock(String key, long expireMillisecond) {
//        Preconditions.checkArgument(StringUtils.isNotBlank(key));
//        Preconditions.checkArgument(expireMillisecond > 0L);
//
//        String lockKey = LOCK_KEY_PREFIX + key;
//        String lockValue = UUID.randomUUID().toString();
//        boolean keySet = redisTemplateWarpper.vSetIfAbsent(lockKey, lockValue, expireMillisecond);
//        if (keySet) {   //锁成功
//            return lockValue;
//        }
//        return null;
//    }
//
//    /**
//     * 解锁
//     *
//     * @param key
//     */
//    public void unlock(String key, String value) {
//        if (StringUtils.isBlank(value)) {
//            return;
//        }
//
//        String lockKey = LOCK_KEY_PREFIX + key;
//        String lockValueRedis = redisTemplateWarpper.vGet(lockKey);
//        if (StringUtils.equals(lockValueRedis, value)) {
//            redisTemplateWarpper.kDelete(lockKey);
//        }
//    }
}
