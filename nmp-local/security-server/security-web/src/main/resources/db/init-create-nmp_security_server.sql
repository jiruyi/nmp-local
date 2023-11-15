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

-- wq  ip管理 配置管理
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='安全服务器配置值';

CREATE TABLE `nmps_default_config` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `config_code` char(2) NOT NULL COMMENT '配置名称 50:加密比例 51:扩展算法 52:加密方式 53:加密算法 54:上行密钥最大值 55:上行密钥预警值 56:上行密钥最小值 57:下行密钥最大值 58:下行密钥预警值 59:下行密钥最小值',
                                       `default_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '默认值 加密方式: 1 全加密 加密算法: 1 量子加密',
                                       `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                       `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                       `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                       `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                       `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='安全服务器配置默认表';

INSERT INTO nmps_default_config (id,config_code,default_value,is_exist) VALUES('1','50','1','1');

INSERT INTO nmps_default_config (id,config_code,default_value,is_exist) VALUES('2','51','AES','1');

INSERT INTO nmps_default_config (id,config_code,default_value,is_exist) VALUES('3','52','1','1');

INSERT INTO nmps_default_config (id,config_code,default_value,is_exist) VALUES('4','53','1','1');

INSERT INTO nmps_default_config (id,config_code,default_value,is_exist) VALUES('5','54','1024','1');

INSERT INTO nmps_default_config (id,config_code,default_value,is_exist) VALUES('6','55','512','1');

INSERT INTO nmps_default_config (id,config_code,default_value,is_exist) VALUES('7','56','128','1');

INSERT INTO nmps_default_config (id,config_code,default_value,is_exist) VALUES('8','57','1024','1');

INSERT INTO nmps_default_config (id,config_code,default_value,is_exist) VALUES('9','58','512','1');

INSERT INTO nmps_default_config (id,config_code,default_value,is_exist) VALUES('10','59','128','1');

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='基站管理';


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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ca管理';

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='dns管理';


-- jry  告警信息表
CREATE TABLE `nmps_alarm_info` (
           `alarm_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
           `network_id` VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '入网id',
           `alarm_content` VARCHAR(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '告警内容',
           `alarm_level` CHAR(2) DEFAULT NULL COMMENT '级别 1严重 2 紧急 3 一般',
           `alarm_upload_time` DATETIME(2) NOT NULL COMMENT '操作时间',
           `alarm_source_type` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '01' COMMENT '来源类型 00资源告警 01安全服务器',
           `alarm_content_type` CHAR(2) DEFAULT '5' COMMENT '告警内容类型  1: cpu 2 内存 3 磁盘 4流量 5 其他',
           `create_time` DATETIME(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
           `update_time` DATETIME(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
           PRIMARY KEY (`alarm_id`),
           KEY `idx_network_id` (`network_id`),
           KEY `upload_time_key` (`alarm_upload_time`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- zyj
CREATE TABLE `nmps_user` (
           `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
           `login_account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录账号',
           `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
           `is_exist` tinyint(1) DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
           `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
           `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
           `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
           `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
           `remark` varchar(500) DEFAULT NULL COMMENT '备注',
           PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';

INSERT INTO `nmp_security_server`.`nmps_user` (`user_id`, `login_account`, `password`, `is_exist`) VALUES ('1', 'admin', 'j2sivmjjihBLggve6ed5lw==', '1');


CREATE TABLE `nmps_data_info` (
           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
           `network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '入网id',
           `data_value` bigint NOT NULL COMMENT '数据值（单位byte）',
           `data_type` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据类型（1000：剩余上行密钥量 1001：已使用上行密钥量 2000：剩余下行密钥量 2001：已使用下行密钥量）',
           `upload_time` datetime(2) NOT NULL COMMENT '上报时间',
           `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
           PRIMARY KEY (`id`),
           KEY `index_time` (`upload_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='密钥信息数据上报表';
