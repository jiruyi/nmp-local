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

-- zyj
DELETE FROM nmpl_menu WHERE `menu_id` IN (170,172,173,174,175,62);
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('170', '数据采集管理', '28', '/node/datacollect', '0', '1', '1', 'sys:dataCollect:query',  NULL, '1', '', '0', 'node/datacollect');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('172', '接入基站参数配置', '61', '/setting/instation', '0', '1', '1', 'sys:parmInStation:query',  NULL, '1', '', '1', 'setting/instation');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('173', '边界基站参数配置', '61', '/setting/boundarystation', '0', '1', '1', 'sys:parmBoundaryStation:query', NULL, '1', '', '1', 'setting/boundarystation');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('174', '密钥中心参数配置', '61', '/setting/secret', '0', '1', '1', 'sys:parmSecret:query',  NULL, '1', '', '1', 'setting/secret');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('175', '数据采集参数配置', '61', '/setting/datacollect', '0', '1', '1', 'sys:parmDataCollect:query', NULL, '1', '', '1', 'setting/datacollect');
DELETE FROM nmpl_menu WHERE `menu_id`>=177 and `menu_id`<=196;
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('177', '新建采集系统', '170', '', '0', '2', '1', 'sys:dataCollect:insert',  '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('178', '修改采集系统', '170', '', '0', '2', '1', 'sys:dataCollect:modify',  '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('179', '删除采集系统', '170', '', '0', '2', '1', 'sys:dataCollect:delete',  '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('180', '编辑', '172', '', '0', '2', '1', 'sys:parmInStation:modify',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('181', '同步', '172', '', '0', '2', '1', 'sys:parmInStation:synchronous',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('182', '恢复默认', '172', '', '0', '2', '1', 'sys:parmInStation:default',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('183', '编辑', '173', '', '0', '2', '1', 'sys:parmBoundaryStation:modify',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('184', '同步', '173', '', '0', '2', '1', 'sys:parmBoundaryStation:synchronous',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('185', '恢复默认', '173', '', '0', '2', '1', 'sys:parmBoundaryStation:default',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('186', '编辑', '174', '', '0', '2', '1', 'sys:parmSecret:modify',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('187', '同步', '174', '', '0', '2', '1', 'sys:parmSecret:synchronous',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('188', '恢复默认', '174', '', '0', '2', '1', 'sys:parmSecret:default',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('189', '数据采集启用/禁用', '175', '', '0', '2', '1', 'sys:parmDataCollect:enable',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('190', '修改基础配置', '175', '', '0', '2', '1', 'sys:parmDataCollect:modify',  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('191', '恢复默认基础配置', '175', '', '0', '2', '1', 'sys:parmDataCollect:default', '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('193', '手动上报业务数据', '175', '', '0', '2', '1', 'sys:parmDataCollect:report', '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('194', '修改上报信息', '175', '', '0', '2', '1', 'sys:parmDataCollect:modifyConfig', '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('195', '恢复默认业务配置', '175', '', '0', '2', '1', 'sys:parmDataCollect:defaultConfig', '1', '', '1', '');

CREATE PROCEDURE `add_col_homework`()-- 新增一个存储过程
BEGIN
	IF not EXISTS (SELECT column_name FROM information_schema.columns WHERE table_name = 'nmpl_outline_pc_info' and column_name = 'swing_in'and TABLE_SCHEMA ='nmp_local')
	-- 判断是否存在字段
	THEN
	-- 不存在则新增字段
ALTER TABLE `nmpl_outline_pc_info` ADD COLUMN`swing_in`  tinyint(1) DEFAULT '1' COMMENT '是否摆入';
END IF;
IF not EXISTS (SELECT column_name FROM information_schema.columns WHERE table_name = 'nmpl_outline_pc_info' and column_name = 'swing_out'and TABLE_SCHEMA ='nmp_local')
	-- 判断是否存在字段
	THEN
	-- 不存在则新增字段
ALTER TABLE `nmpl_outline_pc_info` ADD COLUMN `swing_out` tinyint(1) DEFAULT '1' COMMENT '是否摆出';
END IF;
END;
call add_col_homework();-- 运行该存储过程
drop PROCEDURE add_col_homework; -- 删除该存储过程
update nmpl_outline_pc_info set swing_in = '1',`swing_out`='1';

-- zyj