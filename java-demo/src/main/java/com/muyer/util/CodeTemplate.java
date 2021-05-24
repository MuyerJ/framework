package com.muyer.util;

/**
 * Description: 
 * date: 2021/4/17 11:20
 * @author YeJiang
 * @version
 */
public class CodeTemplate {
    
//        String lockKey = "createPreAssignLockKey_" + activityBatchNo;
//        String lockValue = redisLocker.lock(lockKey, 10000L);
//        if (null == lockValue) {
//            return null;
//        }
//        try {
//            String key = ASSIGNING_DRIVER_QUEUE + activityBatchNo;
//            List<Object> objects = redisTemplateWarpper.lRange(key, 0, count - 1);
//            if (CollectionUtils.isEmpty(objects)) {
//                return null;
//            }
//            //删除下标为0到count-1的数据
//            redisTemplateWarpper.lTrim(key, count, -1);
//            return objects.stream().map(obj -> Objects.isNull(obj) ? "" : String.valueOf(obj)).collect(Collectors.toList());
//        } finally {
//            redisLocker.unlock(lockKey, lockValue);
//        }
}
