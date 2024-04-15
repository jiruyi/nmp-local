USE `nmp_proxy`;

CREATE TABLE `nmpl_task_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `task_id` varchar(16) DEFAULT NULL COMMENT '任务ID',
    `task_name` varchar(16) DEFAULT NULL COMMENT '业务名称',
    `task_type` char(2) DEFAULT NULL COMMENT '任务类型 01:定时任务 02:周期任务',
    `device_id` varchar(128) DEFAULT NULL COMMENT '设备id',
    `current_value` bigint DEFAULT NULL COMMENT '当前值',
    `status` tinyint(1) DEFAULT '1' COMMENT '启停 1启动 0 停止',
    `remark` varchar(64) DEFAULT NULL COMMENT '备注',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '状态 true:存在(1)  false:删除(0)',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务配置表';


drop table nmpl_config;
CREATE TABLE `nmpl_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `device_id` varchar(128) DEFAULT NULL COMMENT '设备id',
    `config_name` varchar(100) DEFAULT NULL COMMENT '配置项名称',
    `config_code` varchar(32) DEFAULT NULL COMMENT '配置编码',
    `config_value` varchar(2000) DEFAULT NULL COMMENT '配置值',
    `unit` varchar(32) DEFAULT NULL COMMENT '单位',
    `status` tinyint DEFAULT '1' COMMENT '状态 1同步 0 未同步',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '状态 true:存在(1)  false:删除(0)',
    `remark` varchar(100) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY(`config_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统配置表';


