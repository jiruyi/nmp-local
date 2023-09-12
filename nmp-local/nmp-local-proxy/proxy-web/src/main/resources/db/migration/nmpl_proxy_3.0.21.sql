USE `nmp_proxy`;

CREATE TABLE IF NOT EXISTS `nmpl_config` (
    `id` bigint NOT NULL COMMENT '主键',
    `device_type` char(2) DEFAULT NULL COMMENT '01:接入基站 02:边界基站 11:密钥中心 20:数据采集',
    `config_name` varchar(100) DEFAULT NULL COMMENT '配置项名称',
    `config_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置编码',
    `config_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置值',
    `default_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '默认值',
    `unit` varchar(32) DEFAULT NULL COMMENT '单位',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '状态 true:存在(1)  false:删除(0)',
    `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `nmpl_update_info_boundary` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `table_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新表名',
    `operation_type` varchar(8) DEFAULT NULL COMMENT '操作类型：新增:1 修改:2',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) NOT NULL COMMENT '创建时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='数据更新信息表(边界基站专用)';

ALTER TABLE `nmpl_terminal_user` add column `user_type` char(2) DEFAULT '21' COMMENT '用户类型 21:一体机  22:安全服务器';

DROP TABLE IF EXISTS `nmpl_link_relation`;
CREATE TABLE IF NOT EXISTS `nmpl_link` (
    `id` bigint NOT NULL COMMENT '主键',
    `link_name` varchar(32) DEFAULT NULL COMMENT '链路名称',
    `link_type` smallint DEFAULT NULL COMMENT '链路类型: 1:单向,2:双向',
    `link_relation` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '链路关系: 01:边界基站-边界基站,02:接入基站-密钥中心,03:边界基站-密钥中心,04:采集系统-边界基站',
    `main_device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '主设备id',
    `follow_device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备id',
    `follow_network_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备入网码',
    `follow_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备ip',
    `follow_port` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备端口',
    `active_auth` tinyint(1) DEFAULT NULL COMMENT '主动发起认证 1:开启 0:关闭',
    `is_on` tinyint(1) DEFAULT NULL COMMENT '是否启用 1:启动 0:禁止',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '链路信息表';

ALTER TABLE `nmpl_link` add column `follow_device_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备名称';

alter table nmpl_terminal_data modify `up_value` bigint NOT NULL COMMENT '上行密钥量';

alter table nmpl_terminal_data modify `down_value` bigint NOT NULL COMMENT '下行密钥量';


ALTER TABLE `nmpl_pc_data` add column `data_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据类型 01:剩余 02:补充 02:使用';

ALTER TABLE `nmpl_terminal_user` DROP PRIMARY KEY;

ALTER TABLE `nmpl_terminal_user` ADD COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id' PRIMARY KEY;

ALTER TABLE `nmpl_system_heartbeat` DROP PRIMARY KEY;

ALTER TABLE `nmpl_system_heartbeat` ADD COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id' PRIMARY KEY;

ALTER TABLE `nmpl_base_station_info` MODIFY `public_network_port` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公网端口';

CREATE TABLE IF NOT EXISTS `nmpl_station_connect_count` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `station_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备Id',
    `current_connect_count` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '当前用户数',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `upload_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '上传时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='当前用户在线表';

CREATE TABLE `nmpl_company_heartbeat` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `source_network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '来源Id',
    `target_network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目标Id',
    `status` char(2) DEFAULT '01' COMMENT '连接状态 01:通  02:不通',
    `up_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '上行流量',
    `down_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '下行流量',
    `upload_time` datetime(2) DEFAULT NULL COMMENT '上报时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小区业务心跳';
