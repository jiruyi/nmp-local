CREATE TABLE IF NOT EXISTS `nmpl_report_business` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `business_name` varchar(100) DEFAULT NULL COMMENT '业务名称',
    `business_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务编码',
    `business_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务配置值',
    `default_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '默认值',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '状态 true:存在(1)  false:删除(0)',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '数据采集业务上报配置表';

DROP TABLE IF EXISTS `nmpl_config`;

CREATE TABLE IF NOT EXISTS `nmpl_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `device_type` char(2) DEFAULT NULL COMMENT '设备类型 01:接入基站 02:边界基站 11:密钥中心 20:数据采集',
    `config_name` varchar(100) DEFAULT NULL COMMENT '配置项名称',
    `config_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置编码',
    `config_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置值',
    `default_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '默认值',
    `unit` varchar(32) DEFAULT NULL COMMENT '单位',
    `status` tinyint DEFAULT '0' COMMENT '状态 1同步 0 未同步',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '状态 true:存在(1)  false:删除(0)',
    `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '系统配置表';

INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status) VALUES
    ('01', '加强密度配置（128bit-128k）', '配置项编码', '1：1', '1：1', null, 0),
    ('01', '加强密度配置（>128k ）', '配置项编码', '1：32', '1：32', null, 0),
    ('01', '话单上报', '配置项编码', '60', '60', 's', 0),
    ('01', '基站状态上报', '配置项编码', '1', '1', 's', 0),
    ('01', '通信状态上报', '配置项编码', '60', '60', 's', 0),
    ('11', '生成密钥量', '配置项编码', '10', '10', 'T', 0),
    ('11', '分发密钥量', '配置项编码', '1', '1', 'T', 0),
    ('20', '采集周期', '配置项编码', '1800', '1800', 's', 0),
    ('20', '上报周期', '配置项编码', '1800', '1800', 's', 0),
    ('20', '数据采集开关', 'dataSwitch', '0', '0', null, 0);

INSERT INTO nmp_local.nmpl_report_business (business_name, business_code, business_value, default_value) VALUES
    ('业务名称1', 'code1', '1', '1'),
    ('业务名称2', 'code2', '1', '1'),
    ('业务名称3', 'code3', '1', '1'),
    ('业务名称4', 'code4', '1', '1'),
    ('业务名称5', 'code5', '1', '1'),
    ('业务名称6', 'code6', '1', '1'),
    ('业务名称7', 'code7', '1', '1'),
    ('业务名称8', 'code8', '1', '1'),
    ('业务名称9', 'code9', '1', '1'),
    ('业务名称10', 'code10', '1', '1')
;