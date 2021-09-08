create TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time_date` datetime NOT NULL,
  `time_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
  `time_long` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `time_long` (`time_long`),
  KEY `time_timestamp` (`time_timestamp`),
  KEY `time_date` (`time_date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1


---
select count(*) from users ;
select time_date, count(*) from users group by time_date;
-- 在 InnoDB 存储引擎下，通过时间范围查找，性能 bigint > datetime > timestamp
select count(*) from users where time_date >="2018-10-21 23:32:44" and time_date <="2018-10-21 23:41:22";
select count(*) from users where time_timestamp >= "2018-10-21 23:32:44" and time_timestamp <="2018-10-21 23:41:22";
select count(*) from users where time_long >=1540135964091 and time_long <=1540136482372;
-- 结论 在 InnoDB 存储引擎下，通过时间排序，性能 bigint > timestamp > datetime
select time_timestamp, count(*) from users group by time_timestamp;
select * from users order by time_date;
select * from users order by time_timestamp;
select * from users order by time_long;