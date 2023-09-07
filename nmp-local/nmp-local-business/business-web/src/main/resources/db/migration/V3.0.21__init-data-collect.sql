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
    `status` tinyint DEFAULT '1' COMMENT '状态 1同步 0 未同步',
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
DELETE FROM nmpl_menu WHERE `menu_id`>=177 and `menu_id`<=197;
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
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('196', '同步基础配置', '175', '', '0', '2', '1', 'sys:parmDataCollect:syncBase', '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `is_exist`, `icon`, `permission`, `component`) VALUES ('197', '同步业务配置', '175', '', '0', '2', '1', 'sys:parmDataCollect:syncBusiness', '1', '', '1', '');
update `nmp_local`.`nmpl_menu` set menu_name = '终端设备认证放号' where menu_id in (126,127);

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

ALTER TABLE `nmpl_company_info` ADD COLUMN `position` varchar(30) DEFAULT NULL COMMENT '经纬度位置';

alter table nmpl_terminal_data modify `terminal_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '一体机ip';


delete from `nmp_local`.`nmpl_menu` where menu_id in ('148','30','31','32','91','92','93','94','199','200','201');
UPDATE `nmp_local`.`nmpl_menu` SET `parent_menu_id`='92' WHERE (`parent_menu_id`='30');
UPDATE `nmp_local`.`nmpl_menu` SET `parent_menu_id`='93' WHERE (`parent_menu_id`='31');
UPDATE `nmp_local`.`nmpl_menu` SET `parent_menu_id`='94' WHERE (`parent_menu_id`='32');
UPDATE `nmp_local`.`nmpl_menu` SET  `menu_name`='接入基站管理',`url`='/node/instation',`component`='node/instation' WHERE (`menu_id`='29');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('91', '边界基站管理', '28', '/node/boundarystation', '0', '1', '1', 'sys:boundarystation:query',  NULL, '1', '', '0', 'node/boundarystation');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('92', '密钥中心管理', '28', '/node/dispenser', '0', '1', '1', 'sys:dispenser:query',  NULL, '1', '', '0', 'node/dispenser');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('93', '密钥生成管理', '28', '/node/generate', '0', '1', '1', 'sys:generator:query',  NULL, '1', '', '0', 'node/generate');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('94', '缓存机管理', '28', '/node/cache', '0', '1', '1', 'sys:cache:query', NULL, '1', '', '0', 'node/cache');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('199', '新建基站', '91', NULL, '0', '2', '1', 'sys:boundarystation:save',  NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('200', '删除基站', '91', NULL, '0', '2', '1', 'sys:boundarystation:delete', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`,  `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('201', '编辑基站', '91', NULL, '0', '2', '1', 'sys:boundarystation:update',  NULL, '1', '', '0', '');
-- zyj



--wq


ALTER TABLE `nmpl_terminal_user` add column `user_type` char(2) DEFAULT '21' COMMENT '用户类型 21:一体机  22:安全服务器';



CREATE TABLE IF NOT EXISTS `nmpl_station_connect_count` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `station_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备Id',
    `current_connect_count` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '当前用户数',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `upload_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '上传时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='当前用户在线表';


CREATE TABLE IF NOT EXISTS `nmpl_company_heartbeat` (
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小区业务心跳';

ALTER TABLE nmpl_base_station_info MODIFY `public_network_port` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公网端口';

DROP TABLE IF EXISTS `nmpl_link_relation`;
CREATE TABLE IF NOT EXISTS `nmpl_link` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `link_name` varchar(32) DEFAULT NULL COMMENT '链路名称',
    `link_type` smallint DEFAULT NULL COMMENT '链路类型: 1:单向,2:双向',
    `link_relation` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '链路关系: 01:边界基站-边界基站,02:接入基站-密钥中心,03:边界基站-密钥中心,04:采集系统-边界基站',
    `main_device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '主设备id',
    `follow_device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备id',
    `follow_network_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备入网码',
    `follow_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备ip',
    `follow_port` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备端口',
    `active_auth` tinyint(1) DEFAULT NULL COMMENT '主动发起认证 1:开启 0:关闭',
    `is_on` tinyint(1) DEFAULT NULL COMMENT '是否启用 1:启动 0:禁止',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '修改时间',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '链路信息表';



CREATE TABLE IF NOT EXISTS `nmpl_data_push_record` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
     `table_name` varchar(50) NOT NULL COMMENT '表名',
     `data_id` bigint DEFAULT NULL COMMENT '数据表id',
     `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
     `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=494 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '数据推送记录表';

ALTER TABLE `nmpl_link` add column `follow_device_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '从设备名称';