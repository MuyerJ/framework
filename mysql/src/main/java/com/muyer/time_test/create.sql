CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time_date` datetime NOT NULL,
  `time_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `time_long` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `time_long` (`time_long`),
  KEY `time_timestamp` (`time_timestamp`),
  KEY `time_date` (`time_date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1