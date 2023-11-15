/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.27 : Database - nmp_security_server
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `nmp_security_server` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  /*!80016 DEFAULT ENCRYPTION='N' */;

USE `nmp_security_server`;

create table nmps_security_server_info
(
    id            bigint auto_increment comment '主键ID'    primary key,
    server_name   varchar(32)                              null comment '安全服务器名称',
    com_ip        varchar(128)                             null comment '通信ip',
    network_id    varchar(128)                             null comment '入网id',
    signal_port   varchar(8)                               null comment '信令（中继）端口',
    key_port      varchar(8)                               null comment '下载密钥端口',
    connect_type  char(2)                                  null comment '连接方式 1:14 0:外接',
    server_status char(2)     default '02'                 null comment '状态 01:上线  02:下线',
    is_exist      tinyint(1)  default 1                    null comment '删除标志（1代表存在 0代表删除）',
    create_user   varchar(64)                              null comment '创建者',
    create_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user   varchar(64)                              null comment '更新者',
    update_time   datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    remark        varchar(500)                             null comment '备注'
)
    comment '安全服务器信息表';

create table nmps_network_card
(
    id            bigint auto_increment comment '主键ID'    primary key,
    network_id    varchar(128)                             null comment '入网id',
    net_card_type char(2)                                  null comment '网卡类型（1：物理网卡 2：虚拟网卡）',
    adapter_name  varchar(64)                              null comment '适配器名称',
    ipv4          varchar(16)                              null comment 'ipv4',
    ipv6          varchar(128)                             null comment 'ipv6',
    is_exist      tinyint(1)  default 1                    null comment '删除标志（1代表存在 0代表删除）',
    create_user   varchar(64)                              null comment '创建者',
    create_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user   varchar(64)                              null comment '更新者',
    update_time   datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    remark        varchar(500)                             null comment '备注'
)
    comment '安全服务器关联网卡信息表';

-------wq---------------------------------------------------------------
CREATE TABLE `nmps_server_config` (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `network_id` varchar(32) DEFAULT NULL COMMENT '设备入网码',
                                      `config_code` char(2) NOT NULL COMMENT '配置名称 50:加密比例 51:扩展算法 52:加密方式 53:加密算法 54:上行密钥最大值 55:上行密钥预警值 56:上行密钥最小值 57:下行密钥最大值 58:下行密钥预警值 59:下行密钥最小值',
                                      `config_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置值',
                                      `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                      `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                      `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                      `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                      `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='安全服务器配置值';

CREATE TABLE `nmps_default_config` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `config_code` char(2) NOT NULL COMMENT '配置名称 50:加密比例 51:扩展算法 52:加密方式 53:加密算法 54:上行密钥最大值 55:上行密钥预警值 56:上行密钥最小值 57:下行密钥最大值 58:下行密钥预警值 59:下行密钥最小值',
                                       `default_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '默认值',
                                       `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                       `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                       `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                       `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                       `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='安全服务器配置默认表';

INSERT INTO `nmps_default_config` VALUES ('1', '50', '1：1', '1', '2023-10-27 15:11:58.01', '1', '2023-10-27 15:11:58.01', '1');
INSERT INTO `nmps_default_config` VALUES ('2', '51', 'AES', '1', '2023-10-27 15:11:58.01', '1', '2023-10-27 15:11:58.01', '1');
INSERT INTO `nmps_default_config` VALUES ('3', '52', '全加密', '1', '2023-10-27 15:11:58.01', '1', '2023-10-27 15:11:58.01', '1');
INSERT INTO `nmps_default_config` VALUES ('4', '53', 'AES', '1', '2023-10-27 15:11:58.01', '1', '2023-10-27 15:11:58.01', '1');
INSERT INTO `nmps_default_config` VALUES ('5', '54', '1024', '1', '2023-10-27 15:11:58.01', '1', '2023-10-27 15:11:58.01', '1');
INSERT INTO `nmps_default_config` VALUES ('6', '55', '512', '1', '2023-10-27 15:11:58.01', '1', '2023-10-27 15:11:58.01', '1');
INSERT INTO `nmps_default_config` VALUES ('7', '56', '128', '1', '2023-10-27 15:11:58.01', '1', '2023-10-27 15:11:58.01', '1');
INSERT INTO `nmps_default_config` VALUES ('8', '57', '1024', '1', '2023-10-27 15:11:58.01', '1', '2023-10-27 15:11:58.01', '1');
INSERT INTO `nmps_default_config` VALUES ('9', '58', '512', '1', '2023-10-27 15:11:58.01', '1', '2023-10-27 15:11:58.01', '1');
INSERT INTO `nmps_default_config` VALUES ('10', '59', '128', '1', '2023-10-27 15:11:58.01', '1', '2023-10-27 15:11:58.01', '1');

CREATE TABLE `nmps_station_manage` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '入网id',
                                       `station_type` char(2) DEFAULT NULL COMMENT '基站类型 00:基站 30:备用基站',
                                       `access_method` char(2) DEFAULT NULL COMMENT '接入方式 01:固定接入 02:动态接入',
                                       `domain_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '域名',
                                       `station_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '通信ip',
                                       `station_port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '通信port',
                                       `key_port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '下载密钥端口',
                                       `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                       `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                       `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                       `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                       `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='基站管理';


CREATE TABLE `nmps_ca_manage` (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '入网id',
                                  `access_method` char(2) DEFAULT NULL COMMENT '接入方式 01:固定接入 02:动态接入',
                                  `domain_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '域名',
                                  `public_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '通信ip',
                                  `public_port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '通信port',
                                  `lan_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '隐私ip',
                                  `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                  `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                  `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                  `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                  `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ca管理';

CREATE TABLE `nmps_dns_manage` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '入网id',
                                   `lan_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '隐私ip',
                                   `receive_port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '接收端口',
                                   `send_port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发送端口',
                                   `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                   `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                   `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                   `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                   `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='dns管理';



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
