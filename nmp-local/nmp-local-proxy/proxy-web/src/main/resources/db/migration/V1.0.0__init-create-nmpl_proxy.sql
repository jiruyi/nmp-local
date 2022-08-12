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
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='基站信息表';

-- ----------------------------
-- Table structure for nmpl_bill
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_bill` (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                           `owner_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '账单用户id',
    `stream_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '流id',
    `flow_number` varchar(32) DEFAULT NULL COMMENT '消耗流量',
    `time_length` varchar(32) DEFAULT NULL COMMENT '时长',
    `key_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '消耗密钥量',
    `hybrid_factor` varchar(32) DEFAULT NULL,
    `upload_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `start_time` varchar(30) DEFAULT NULL COMMENT '开始时间',
    `end_time` varchar(30) DEFAULT NULL COMMENT '结束时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for nmpl_company_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_company_info` (
                                                   `company_id` bigint NOT NULL AUTO_INCREMENT COMMENT '单位id',
                                                   `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '单位名称',
    `unit_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '单位名称',
    `country_code` varchar(50) DEFAULT NULL COMMENT '国家码',
    `company_code` varchar(50) DEFAULT NULL COMMENT '单位编码',
    `company_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '00:运营商 01:大区 02：小区',
    `telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系电话',
    `email` varchar(30) DEFAULT NULL COMMENT '联系邮箱',
    `status` tinyint(1) DEFAULT '1' COMMENT '1:正常 0停用',
    `addr` varchar(50) DEFAULT NULL COMMENT '联系地址',
    `parent_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '父单位编码',
    `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(50) DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`company_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for nmpl_config
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_config` (
                                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                             `device_type` char(2) DEFAULT NULL COMMENT '1:基站 2 分发机 3 生成机 4 缓存机',
    `config_name` varchar(100) DEFAULT NULL COMMENT '配置项名称',
    `config_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置编码',
    `config_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置值',
    `default_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '默认值',
    `unit` varchar(32) DEFAULT NULL COMMENT '单位',
    `status` tinyint DEFAULT '1' COMMENT '状态 1同步 0 未同步',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '状态 true:存在(1)  false:删除(0)',
    `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for nmpl_data_collect
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_data_collect` (
                                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                   `device_id` varchar(32) DEFAULT NULL COMMENT '设备id',
    `device_name` varchar(30) DEFAULT NULL COMMENT '设备名字',
    `device_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '设备类别(01基站、02分发机、03生成机、04缓存机)',
    `data_item_name` varchar(50) DEFAULT NULL COMMENT '统计项名',
    `data_item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收集项编号',
    `data_item_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '值',
    `unit` varchar(32) DEFAULT NULL COMMENT '单位',
    `upload_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for nmpl_device_extra_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_device_extra_info` (
                                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                        `device_id` varchar(128) DEFAULT NULL COMMENT '备用设备id',
    `rel_device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关联主设备id',
    `device_name` varchar(16) DEFAULT NULL COMMENT '设备名称',
    `device_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '01' COMMENT '设备类型 00:基站 01:密钥分发机 02:生成机 03:缓存机',
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
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='备用设备';

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
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='密钥分发和U盘';

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
-- Table structure for nmpl_file_device_rel
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_file_device_rel` (
                                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                      `file_id` bigint NOT NULL COMMENT '文件主键',
                                                      `device_id` varchar(128) NOT NULL COMMENT '设备id',
    `is_delete` tinyint(1) DEFAULT '1' COMMENT '1存在 0删除',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `create_user` varchar(50) DEFAULT NULL,
    `update_user` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for nmpl_key_poll_detail
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_key_poll_detail` (
                                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                      `device_id` varchar(32) DEFAULT NULL COMMENT '设备id',
    `device_network_id` varchar(32) DEFAULT NULL COMMENT '设备入网编号',
    `key_pool_name` varchar(32) DEFAULT '1' COMMENT '秘钥池名字',
    `call_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '上报时间',
    `key_down_number` bigint DEFAULT NULL COMMENT '总下载量',
    `key_remain_number` bigint DEFAULT NULL COMMENT '总剩余量',
    `key_consum_number` bigint DEFAULT NULL COMMENT '总消耗量',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
-- Table structure for nmpl_operate_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_operate_log` (
                                                  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                                  `channel_type` tinyint DEFAULT NULL COMMENT '1:网管pc 2：基站',
                                                  `oper_modul` varchar(64) DEFAULT NULL COMMENT '操作模块',
    `oper_url` varchar(64) DEFAULT NULL COMMENT '请求的url',
    `oper_type` varchar(32) DEFAULT NULL COMMENT '操作类型(新增 修改 编辑等)',
    `oper_desc` varchar(128) DEFAULT NULL COMMENT '操作描述',
    `oper_requ_param` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求参数',
    `oper_resp_param` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务名字',
    `oper_method` varchar(128) DEFAULT NULL COMMENT '操作方法',
    `oper_user_id` varchar(64) DEFAULT NULL COMMENT '操作人id',
    `oper_user_name` varchar(32) DEFAULT NULL COMMENT '操作人姓名',
    `is_success` tinyint(1) DEFAULT NULL COMMENT '1 成功  2 失败',
    `source_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作ip',
    `oper_level` varchar(15) DEFAULT NULL COMMENT '操作级别(1:高危 2 危险 3 警告 4 常规)',
    `oper_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '操作时间',
    `remark` varchar(256) DEFAULT NULL COMMENT '备注',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for nmpl_outline_pc_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_outline_pc_info` (
                                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
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
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='离线一体机';

-- ----------------------------
-- Table structure for nmpl_outline_sorter_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_outline_sorter_info` (
                                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                          `device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备编号',
    `device_name` varchar(16) DEFAULT NULL COMMENT '设备名称',
    `station_network_id` varchar(32) DEFAULT NULL COMMENT '设备入网码',
    `remark` varchar(256) DEFAULT NULL COMMENT '设备备注',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='离线分发机';

-- ----------------------------
-- Table structure for nmpl_route
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_route` (
                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                            `access_device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接入基站id',
    `boundary_device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '边界基站id',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='密钥生成机和密钥缓存机器';

-- ----------------------------
-- Table structure for nmpl_signal
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_signal` (
                                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                             `device_id` varchar(50) DEFAULT NULL COMMENT '设备编号',
    `signal_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '信令名称',
    `send_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发送方ip',
    `receive_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '接收方ip',
    `signal_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '信令内容',
    `business_module` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务模块',
    `upload_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '上报时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '状态true:存在(1)  false:删除(0)',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for nmpl_signal_io
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_signal_io` (
                                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                `device_id` varchar(50) DEFAULT NULL COMMENT '设备编号',
    `status` char(2) DEFAULT NULL COMMENT '状态0：停止追踪 1：开启追踪',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '状态true:存在(1)  false:删除(0)',
    PRIMARY KEY (`id`),
    KEY `nmpl_signal_io_device_id_is_exist_status_index` (`device_id`,`is_exist`,`status`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='信令追踪状态表';


-- ----------------------------
-- Table structure for nmpl_version
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_version` (
                                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                              `system_id` varchar(32) NOT NULL COMMENT '系统标识',
    `version_no` varchar(32) DEFAULT NULL COMMENT '版本号',
    `version_name` varchar(50) DEFAULT NULL COMMENT '版本名称',
    `version_desc` varchar(50) DEFAULT NULL COMMENT '版本描述',
    `remark` varchar(32) DEFAULT NULL COMMENT '描述',
    `is_delete` tinyint(1) DEFAULT '1' COMMENT '1存在 0删除',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `create_user` varchar(50) DEFAULT NULL,
    `update_user` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for nmpl_version_file
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_version_file` (
                                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                   `version_id` bigint NOT NULL COMMENT '版本主键',
                                                   `file_path` varchar(32) DEFAULT NULL COMMENT '文件路径',
    `file_name` varchar(32) DEFAULT NULL COMMENT '文件名称',
    `is_push` tinyint(1) DEFAULT '0' COMMENT '1 已推送 0未推送',
    `status` tinyint(1) DEFAULT '1' COMMENT '1 正常 0失效',
    `is_started` tinyint(1) DEFAULT '0' COMMENT '1 启动 0未启动',
    `remark` varchar(32) DEFAULT NULL COMMENT '描述',
    `is_delete` tinyint(1) DEFAULT '1' COMMENT '1存在 0删除',
    `upload_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '上传时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `create_user` varchar(50) DEFAULT NULL,
    `update_user` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
