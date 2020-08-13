CREATE DATABASE `springcloud1`;
USE `springcloud1`;
CREATE TABLE `springcloud1`.`book`( `id` INT(10) NOT NULL, `name` VARCHAR(10), `dbName` VARCHAR(10), PRIMARY KEY (`id`) );
INSERT INTO `springcloud1`.`book` (`id`, `name`, `dbName`) VALUES ('1', 'math1', 'springcloud1');
INSERT INTO `springcloud1`.`book` (`id`, `name`, `dbName`) VALUES ('2', 'math1', 'springcloud1');
INSERT INTO `springcloud1`.`book` (`id`, `name`, `dbName`) VALUES ('3', 'math1', 'springcloud1');