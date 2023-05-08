-- jry
CREATE TABLE IF NOT EXISTS `nmpl_alarm_info` (
    `alarm_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `alarm_source_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务系统id  物理设备无',
    `alarm_source_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '设备ip',
    `alarm_content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '告警内容',
    `alarm_level` char(2) DEFAULT NULL COMMENT '级别 1严重 2 紧急 3 一般',
    `alarm_upload_time` datetime(2) NOT NULL COMMENT '操作时间',
    `alarm_source_type` char(2) DEFAULT NULL COMMENT '来源类型： 1资源告警 2接入告警  3 边界  4 密钥中心',
    `alarm_content_type` char(2) DEFAULT '5' COMMENT '告警内容类型  1: cpu 2 内存 3 磁盘 4流量 5 其他',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`alarm_id`),
    KEY `idx_device_id` (`alarm_source_id`),
    KEY `idx_source_ip` (`alarm_source_ip`),
    KEY `upload_time_key` (`alarm_upload_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- jry

-- hx
CREATE TABLE IF NOT EXISTS `nmpl_physical_device_heartbeat` (
    `ip1_ip2` varchar(34) NOT NULL  COMMENT '主键(标记两台设备的关联关系，小的ip在前)',
    `status` varchar(2) DEFAULT '1' COMMENT '1：不通 2：通',
    `upload_time` datetime(2) NOT NULL COMMENT '上报时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`ip1_ip2`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci comment '物理设备心跳表';


CREATE TABLE IF NOT EXISTS `nmpl_physical_device_resource` (
    `device_ip` varchar(16) NOT NULL COMMENT '物理设备ip',
    `resource_type` varchar(2) NOT NULL COMMENT '资源类型 1: cpu 2 内存 3 磁盘 4流量 5 其他',
    `resource_value` varchar(64) NOT NULL COMMENT '资源value',
    `resource_unit` varchar(10) DEFAULT NULL COMMENT '资源单位',
    `resource_percent` varchar(8) DEFAULT NULL COMMENT '资源占比',
    `upload_time` datetime(2) NOT NULL COMMENT '上报时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`device_ip`,`resource_type`)
    ) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci comment '物理设备资源情况信息表';


CREATE TABLE IF NOT EXISTS `nmpl_system_resource` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `system_id`  varchar(64) NOT NULL COMMENT '系统id:关联基站和设备表',
    `system_type` varchar(50) DEFAULT NULL COMMENT '系统类别',
    `start_time` datetime(2) DEFAULT NULL COMMENT '启动时间',
    `run_time` bigint DEFAULT 0 COMMENT '运行时长，以秒为单位',
    `cpu_percent` varchar(8) DEFAULT '0' COMMENT 'CPU占比，百分比形式',
    `memory_percent` varchar(8) DEFAULT '0' COMMENT '内存占比，百分比形式',
    `upload_time` datetime(2) NOT NULL COMMENT '上报时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`)
    )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci comment '运行系统资源信息表';

-- hx

-- zyj

DROP TABLE `nmpl_data_collect`;
CREATE TABLE IF NOT EXISTS `nmpl_data_collect` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `device_id` varchar(32) DEFAULT NULL COMMENT '设备id',
    `device_name` varchar(30) DEFAULT NULL COMMENT '设备名字',
    `device_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备ip',
    `device_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备类别(01接入基站、02边界基站、11密钥中心、12生成机、13缓存机)',
    `data_item_name` varchar(50) DEFAULT NULL COMMENT '统计项名',
    `data_item_code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '收集项编号',
    `data_item_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '值',
    `unit` varchar(32) DEFAULT NULL COMMENT '单位',
    `upload_time` datetime(2) NOT NULL COMMENT '创建时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3737590 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `nmp_local`.`nmpl_menu` where `menu_id` in('163','164','165','166');
INSERT INTO `nmp_local`.`nmpl_menu` ( `menu_id`,`menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ( '163','资源监控', '70', '/resource', '0', '1', '1', 'sys:monitor:resource',  NULL, '1', '', '0', 'monitor/resource');
INSERT INTO `nmp_local`.`nmpl_menu` ( `menu_id`,`menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ( '164','设备流量监控', '70', '/device', '0', '1', '1', 'sys:monitor:device', NULL,'1', '', '0', 'monitor/device');
INSERT INTO `nmp_local`.`nmpl_menu` ( `menu_id`,`menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ( '165','系统终端监控', '70', '/system', '0', '1', '1', 'sys:monitor:system',  NULL, '1', '', '0', 'monitor/system');
INSERT INTO `nmp_local`.`nmpl_menu` ( `menu_id`,`menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ( '166','告警监控', '70', '/warning', '0', '1', '1', 'sys:monitor:warning', NULL, '1', '', '0', 'monitor/warning');
UPDATE `nmp_local`.`nmpl_menu` SET `url`='/monitor' WHERE (`menu_id`='70');
UPDATE `nmp_local`.`nmpl_menu` SET `url`='/status',`perms_code`='sys:monitor:status',`component`='monitor/status' WHERE (`menu_id`='71');

-- zyj

-- wq
CREATE TABLE IF NOT EXISTS `nmpl_system_heartbeat` (
    `source_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '来源Id',
    `target_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目标Id',
    `status` char(2) DEFAULT '01' COMMENT '连接状态 01:通  02:不通',
    `upload_time` datetime(2) DEFAULT NULL COMMENT '上报时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
     PRIMARY KEY (`source_id`,`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='业务心跳';

CREATE TABLE IF NOT EXISTS `nmpl_terminal_data` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `terminal_network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '一体机设备id',
    `parent_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '基站设备id',
    `data_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据类型 01:剩余 02:补充 03:使用',
    `up_value` int unsigned NOT NULL COMMENT '上行密钥量',
    `down_value` int unsigned NOT NULL COMMENT '下行密钥量',
    `terminal_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '一体机ip',
    `upload_time` datetime(2) DEFAULT NULL COMMENT '上报时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='基站下一体机信息上报表';

CREATE TABLE IF NOT EXISTS `nmpl_terminal_user` (
    `terminal_network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '终端设备Id',
    `parent_device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属设备Id',
    `terminal_status` char(2) DEFAULT '01' COMMENT '用户状态 01:密钥匹配  02:注册  03:上线 04:下线 05:注销',
    `upload_time` datetime(2) DEFAULT NULL COMMENT '上报时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`terminal_network_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='终端用户表';

ALTER TABLE `nmpl_base_station_info` add column `current_connect_count` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '当前用户数';

ALTER TABLE `nmpl_device_info` add column `current_connect_count` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '当前用户数';
-- wq