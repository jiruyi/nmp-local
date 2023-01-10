/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.27 : Database - nmp_proxy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `nmp_proxy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  /*!80016 DEFAULT ENCRYPTION='N' */;

USE `nmp_proxy`;

/*Table structure for table `nmpl_base_station_info` */

-- ----------------------------
-- Table structure for nmpl_base_station_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_base_station_info` (
                                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                        `station_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备id',
    `station_name` varchar(16) DEFAULT NULL COMMENT '设备名称',
    `station_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '01' COMMENT '设备类型 01:小区内基站 02:小区边界基站',
    `enter_network_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '接入网时间',
    `station_admain` varchar(20) DEFAULT NULL COMMENT '设备管理员',
    `remark` varchar(256) DEFAULT NULL COMMENT '设备备注',
    `public_network_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公网Ip',
    `public_network_port` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公网端口',
    `lan_ip` varchar(16) DEFAULT NULL COMMENT '局域网ip',
    `lan_port` varchar(8) DEFAULT NULL COMMENT '局域网port',
    `station_status` char(2) DEFAULT '01' COMMENT '设备状态 01:静态  02:激活  03:禁用 04:下线',
    `station_network_id` varchar(32) DEFAULT NULL COMMENT '设备入网码',
    `station_random_seed` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '加密随机数',
    `relation_operator_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关联小区',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    `byte_network_id` blob COMMENT '设备入网码',
    `prefix_network_id` bigint DEFAULT NULL COMMENT '入网码前缀',
    `suffix_network_id` bigint DEFAULT NULL COMMENT '入网码后缀',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='基站信息表';

-- ----------------------------
-- Table structure for nmpl_local_base_station_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_local_base_station_info` (
                                                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                              `station_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备id',
    `station_name` varchar(16) DEFAULT NULL COMMENT '设备名称',
    `station_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '01' COMMENT '设备类型 01:小区内基站 02:小区边界基站',
    `enter_network_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '接入网时间',
    `station_admain` varchar(20) DEFAULT NULL COMMENT '设备管理员',
    `remark` varchar(256) DEFAULT NULL COMMENT '设备备注',
    `public_network_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公网Ip',
    `public_network_port` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公网端口',
    `lan_ip` varchar(16) DEFAULT NULL COMMENT '局域网ip',
    `lan_port` varchar(8) DEFAULT NULL COMMENT '局域网port',
    `station_status` char(2) DEFAULT '01' COMMENT '设备状态 01:静态  02:激活  03:禁用 04:下线',
    `station_network_id` varchar(32) DEFAULT NULL COMMENT '设备入网码',
    `station_random_seed` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '加密随机数',
    `relation_operator_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关联小区',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    `byte_network_id` blob COMMENT '设备入网码',
    `prefix_network_id` bigint DEFAULT NULL COMMENT '入网码前缀',
    `suffix_network_id` bigint DEFAULT NULL COMMENT '入网码后缀',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='本机基站信息表';


CREATE TABLE IF NOT EXISTS `nmpl_station_heart_info` (
    `station_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备id',
    `remark` char(2) DEFAULT NULL COMMENT '备注 0：内外网正常 1：外网异常',
    `station_network_id` varchar(32) DEFAULT NULL COMMENT '设备入网码',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    KEY (`station_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='基站心跳上报信息表';

CREATE TABLE IF NOT EXISTS `nmpl_keycenter_heart_info` (
    `device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备id',
    `remark` char(2) DEFAULT NULL COMMENT '备注 0：内外网正常 1：外网异常',
    `station_network_id` varchar(32) DEFAULT NULL COMMENT '设备入网码',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    KEY (`device_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='密钥中心心跳上报信息表';


-- ----------------------------
-- Table structure for nmpl_data_collect
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_data_collect` (
                                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                   `device_id` varchar(128) NOT NULL COMMENT '设备id',
    `device_name` varchar(30) DEFAULT NULL COMMENT '设备名字',
    `device_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备类别(00基站、11密钥中心、12生成机、13缓存机)',
    `data_item_name` varchar(50) NOT NULL COMMENT '统计项名(剩余秘钥量;使用秘钥量)',
    `data_item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收集项编号(10003;10001)',
    `data_item_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '值',
    `unit` varchar(32) DEFAULT NULL COMMENT '单位',
    `upload_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ----------------------------
-- Table structure for nmpl_device_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_device_info` (
                                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                  `device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备编号',
    `device_name` varchar(16) DEFAULT NULL COMMENT '设备名称',
    `device_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '01' COMMENT '设备类型 11:密钥中心 12:生成机 13:缓存机',
    `other_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备用类型 ',
    `enter_network_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '接入网时间',
    `device_admain` varchar(20) DEFAULT NULL COMMENT '设备管理员',
    `remark` varchar(256) DEFAULT NULL COMMENT '设备备注',
    `public_network_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公网Ip',
    `public_network_port` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公网端口',
    `lan_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内网Ip',
    `lan_port` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内网端口',
    `station_status` char(2) DEFAULT '01' COMMENT '设备状态 01:静态  02:激活  03:禁用 04:下线',
    `station_network_id` varchar(32) DEFAULT NULL COMMENT '设备入网码',
    `station_random_seed` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '加密随机数',
    `relation_operator_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关联区域',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    `byte_network_id` blob COMMENT '设备入网码',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='密钥分发和U盘';



-- ----------------------------
-- Table structure for nmpl_local_device_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_local_device_info` (
                                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                        `device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备编号',
    `device_name` varchar(16) DEFAULT NULL COMMENT '设备名称',
    `device_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '01' COMMENT '设备类型 11:密钥中心 12:生成机 13:缓存机',
    `other_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备用类型 ',
    `enter_network_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '接入网时间',
    `device_admain` varchar(20) DEFAULT NULL COMMENT '设备管理员',
    `remark` varchar(256) DEFAULT NULL COMMENT '设备备注',
    `public_network_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公网Ip',
    `public_network_port` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公网端口',
    `lan_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内网Ip',
    `lan_port` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内网端口',
    `station_status` char(2) DEFAULT '01' COMMENT '设备状态 01:静态  02:激活  03:禁用 04:下线',
    `station_network_id` varchar(32) DEFAULT NULL COMMENT '设备入网码',
    `station_random_seed` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '加密随机数',
    `relation_operator_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关联区域',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    `byte_network_id` blob COMMENT '设备入网码',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='本机密钥分发和U盘';

-- ----------------------------
-- Table structure for nmpl_device_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_device_log` (
                                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                 `device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '设备id',
    `devcie_name` varchar(128) DEFAULT NULL COMMENT '设备名称',
    `device_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '设备类型 1 基站',
    `device_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '设备ip',
    `oper_type` varchar(12) DEFAULT NULL COMMENT '设备操作 关机 初始化。。。',
    `oper_desc` varchar(64) DEFAULT NULL COMMENT '操作描述',
    `oper_user` varchar(32) DEFAULT NULL COMMENT '操作人',
    `oper_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '操作时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_device_id` (`device_id`),
    KEY `idx_device_name` (`devcie_name`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ----------------------------
-- Table structure for nmpl_link_relation
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_link_relation` (
                                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                    `link_name` varchar(100) DEFAULT NULL COMMENT '链路名称',
    `link_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '链路类型: 01:边界基站-边界基站,02:基站-秘钥中心,03:基站-缓存机,04:秘钥中心-生成机',
    `main_device_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '主设备id',
    `follow_device_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备id',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '1:存在  0删除',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for nmpl_outline_pc_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_outline_pc_info` (
                                                      `id` bigint NOT NULL COMMENT '主键',
                                                      `device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备编号',
    `device_name` varchar(16) DEFAULT NULL COMMENT '设备名称',
    `station_network_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '设备入网码',
    `remark` varchar(256) DEFAULT NULL COMMENT '设备备注',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='一体机信息表';


CREATE TABLE IF NOT EXISTS `nmpl_update_info_base` (
                                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                       `table_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新表名',
    `operation_type` varchar(8) DEFAULT NULL COMMENT '操作类型：新增:1 修改:2',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) NOT NULL COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='数据更新信息表(基站专用)';

CREATE TABLE IF NOT EXISTS `nmpl_update_info_keycenter` (
                                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                            `table_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新表名',
    `operation_type` varchar(8) DEFAULT NULL COMMENT '操作类型：新增:1 修改:2',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) NOT NULL COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='数据更新信息表(秘钥中心专用)';

CREATE TABLE IF NOT EXISTS `nmpl_update_info_cache` (
                                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                        `table_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新表名',
    `operation_type` varchar(8) DEFAULT NULL COMMENT '操作类型：新增:1 修改:2',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) NOT NULL COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='数据更新信息表(缓存机专用)';

CREATE TABLE IF NOT EXISTS `nmpl_update_info_generator` (
                                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                            `table_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新表名',
    `operation_type` varchar(8) DEFAULT NULL COMMENT '操作类型：新增:1 修改:2',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) NOT NULL COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='数据更新信息表(生成机专用)';

CREATE TABLE IF NOT EXISTS `nmpl_business_route` (
                                                     `id` bigint NOT NULL COMMENT '主键',
                                                     `route_id` varchar(128) NOT NULL COMMENT '路由Id',
    `business_type` varchar(90) NOT NULL COMMENT '业务类型',
    `network_id` varchar(50) NOT NULL COMMENT '设备入网码',
    `ip` varchar(32) NOT NULL COMMENT 'ip',
    `create_user` varchar(64) DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(64) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    `ip_v6` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'ip_v6',
    `byte_network_id` blob NOT NULL comment '设备入网码(字节存储)',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='业务服务路由';


CREATE TABLE IF NOT EXISTS `nmpl_internet_route` (
                                                     `id` bigint NOT NULL COMMENT '主键',
                                                     `route_id` varchar(128) NOT NULL COMMENT '路由Id',
    `network_id` varchar(50) NOT NULL COMMENT '设备入网码',
    `boundary_station_ip` varchar(32) NOT NULL COMMENT '边界基站ip',
    `create_user` varchar(64) DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(64) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    `ip_v6` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '边界基站ip_v6',
    `byte_network_id` blob NOT NULL comment '设备入网码(字节存储)',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='出网路由';


CREATE TABLE IF NOT EXISTS `nmpl_pc_data` (
                                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                              `station_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '基站设备id',
    `device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '一体机设备id',
    `pc_network_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '一体机设备入网码',
    `status` tinyint NOT NULL COMMENT '设备状态 1:接入  2:未接入',
    `up_key_num` int unsigned NOT NULL COMMENT '上行消耗密钥量(单位byte)',
    `down_key_num` int unsigned NOT NULL COMMENT '下行消耗密钥量(单位byte)',
    `report_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '上报时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='基站下一体机信息上报表';

CREATE TABLE IF NOT EXISTS `nmpl_static_route` (
                                                   `id` bigint NOT NULL COMMENT '主键',
                                                   `route_id` varchar(128) NOT NULL COMMENT '路由Id',
    `network_id` varchar(50) NOT NULL COMMENT '设备入网码',
    `server_ip` varchar(32) NOT NULL COMMENT '服务器ip',
    `is_exist` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
    `create_user` varchar(64) DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(64) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `station_id` varchar(128) NOT NULL COMMENT '基站id',
    `ip_v6` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务器ip_v6',
    `byte_network_id` blob NOT NULL comment '设备入网码(字节存储)',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='静态路由';

CREATE TABLE IF NOT EXISTS `nmpl_bill` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `owner_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '账单用户id',
    `stream_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '流id',
    `flow_number` varchar(32) DEFAULT NULL COMMENT '消耗流量',
    `time_length` varchar(32) DEFAULT NULL COMMENT '时长',
    `key_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '消耗密钥量',
    `hybrid_factor` varchar(32) DEFAULT NULL COMMENT '杂糅因子',
    `upload_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `start_time` varchar(30) DEFAULT NULL COMMENT '开始时间',
    `end_time` varchar(30) DEFAULT NULL COMMENT '结束时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='话单上报表';

CREATE TABLE IF NOT EXISTS `nmpl_error_push_log` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '推送url',
    `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '推送信息',
    `error_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '异常信息',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代理异常推送日志';


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
