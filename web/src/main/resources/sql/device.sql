CREATE DATABASE IF NOT EXISTS `iot`;

USE `iot`;

# CREATE TABLE IF NOT EXISTS `iotrfid_device`(
#     `id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
#     `mac_id` VARCHAR(32) NOT NULL COMMENT '设备MAC地址',
#     `created_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
#     `updated_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
#     PRIMARY KEY (`id`)
# )ENGINE = InnoDB DEFAULT CHARSET = utf8;
#
# INSERT INTO `iotrfid_device` VALUES (1, "192.168.0.1", NOW(), NOW());
# INSERT INTO `iotrfid_device` VALUES (2, "192.168.0.2", NULL, NULL);


CREATE TABLE IF NOT EXISTS `device`(
           `id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
           `mac_id` VARCHAR(32) NOT NULL COMMENT '设备MAC地址',
           `created_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
           `updated_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
           PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;


INSERT INTO `device` VALUES (NULL, "192.168.0.1", NOW(), NOW());
INSERT INTO `device` VALUES (NULL, "192.168.0.2", default, default);
