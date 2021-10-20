CREATE DATABASE IF NOT EXISTS `iot`;

USE `iot`;

# CREATE TABLE IF NOT EXISTS `iotrfid_user`(
#    `id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
#    `username` VARCHAR(11) NOT NULL COMMENT '用户名',
#    `password` VARCHAR(11) NOT NULL COMMENT '密码',
#    PRIMARY KEY (`id`)
# )ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `user`
(
    `id`       BIGINT(11)  NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `username` VARCHAR(32) NOT NULL COMMENT '用户名',
    `password` VARCHAR(32) NOT NULL COMMENT '密码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `user`
VALUES (NULL, 'Mrli', "password");
