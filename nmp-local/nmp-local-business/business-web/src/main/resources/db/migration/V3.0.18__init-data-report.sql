-- jry
CREATE TABLE IF NOT EXISTS `nmpl_device_alarm` (
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
    `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `device_ip` varchar(16) NOT NULL COMMENT '物理设备ip',
    `resource_type` varchar(2) NOT NULL COMMENT '资源类型 1: cpu 2 内存 3 磁盘 4流量 5 其他',
    `resource_value` varchar(64) NOT NULL COMMENT '资源value',
    `resource_unit` varchar(10) DEFAULT NULL COMMENT '资源单位',
    `resource_percent` varchar(8) DEFAULT NULL COMMENT '资源占比',
    `upload_time` datetime(2) NOT NULL COMMENT '上报时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci comment '物理设备资源情况信息表';


CREATE TABLE IF NOT EXISTS `nmpl_system_resource` (
    `system_id`  varchar(64) NOT NULL COMMENT '主键（系统id，关联基站和设备表）',
    `system_type` varchar(50) DEFAULT NULL COMMENT '系统类别',
    `start_time` datetime(2) DEFAULT NULL COMMENT '启动时间',
    `run_time` bigint DEFAULT 0 COMMENT '运行时长，以秒为单位',
    `cpu_percent` varchar(8) DEFAULT '0' COMMENT 'CPU占比，百分比形式',
    `memory_percent` varchar(8) DEFAULT '0' COMMENT '内存占比，百分比形式',
    `upload_time` datetime(2) NOT NULL COMMENT '上报时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`system_id`)
    )ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci comment '运行系统资源信息表';

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

-- zyj

-- wq


-- wq