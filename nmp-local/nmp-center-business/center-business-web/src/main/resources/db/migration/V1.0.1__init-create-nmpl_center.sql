/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.27 : Database - nmp_local
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `nmp_center` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  /*!80016 DEFAULT ENCRYPTION='N' */;

USE `nmp_center`;

CREATE TABLE IF NOT EXISTS `nmpl_report_business` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `business_name` varchar(100) DEFAULT NULL COMMENT '业务名称',
    `business_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务编码',
    `is_report` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '是否上报（0：不上报 1：上报）',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '状态 true:存在(1)  false:删除(0)',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '上报业务配置表';


CREATE TABLE IF NOT EXISTS `nmpl_dictionary` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `id_name` varchar(128) DEFAULT NULL COMMENT 'id名称',
    `id_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'id编码',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '状态 true:存在(1)  false:删除(0)',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '字典表';


CREATE TABLE IF NOT EXISTS `nmpl_menu` (
    `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
    `parent_menu_id` bigint DEFAULT '-1' COMMENT '父菜单ID',
    `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求地址',
    `is_frame` tinyint DEFAULT '0' COMMENT '是否为外链（1是 0否）',
    `menu_type` tinyint DEFAULT NULL COMMENT '菜单类型（1目录 2菜单 3按钮）',
    `menu_status` tinyint DEFAULT '1' COMMENT '菜单状态（1正常 0停用）',
    `perms_code` varchar(100) DEFAULT NULL,
    `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
    `is_exist` tinyint DEFAULT '1' COMMENT '1正常 0删除',
    `icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '前端按钮',
    `permission` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '权限标识',
    `component` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '前端组件信息',
    PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单权限表';

CREATE TABLE IF NOT EXISTS `nmpl_role` (
    `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name` varchar(30) NOT NULL COMMENT '角色名称',
    `role_code` varchar(100) DEFAULT NULL COMMENT '角色编码',
    `menu_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '2' COMMENT '1:全部菜单权限 2：自定义',
    `status` tinyint DEFAULT '1' COMMENT '角色状态（1正常 0停用）',
    `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `remark` varchar(200) DEFAULT NULL COMMENT '备注',
    `is_exist` tinyint DEFAULT '1' COMMENT '1正常 0删除',
    PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';

CREATE TABLE IF NOT EXISTS `nmpl_role_menu_relation` (
    `role_id` bigint NOT NULL COMMENT '角色id',
    `menu_id` bigint NOT NULL COMMENT '菜单权限id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `nmpl_user` (
    `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `login_account` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录账号',
    `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户昵称',
    `user_type` char(2) DEFAULT '01' COMMENT '用户类型（00系统用户 01注册用户）',
    `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户邮箱',
    `phone_number` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号码',
    `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `role_id` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色id',
    `status` tinyint(1) DEFAULT '1' COMMENT '帐号状态（1正常 0停用）',
    `is_exist` tinyint(1) DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
    `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';




INSERT INTO `nmpl_role` (`role_id`, `role_name`, `role_code`, `menu_scope`, `status`, `create_user`,  `remark`, `is_exist`)
select '1', '超级管理员', 'admin', '1', '1', '-1', NULL, '1'
    WHERE NOT EXISTS (SELECT * FROM `nmpl_role` WHERE role_id='1');

INSERT INTO `nmpl_role` (`role_id`, `role_name`, `role_code`, `menu_scope`, `status`, `create_user`,  `remark`, `is_exist`)
select '2', '业务管理员', 'common_admin', '1', '1', '-1', NULL, '1'
    WHERE NOT EXISTS (SELECT * FROM `nmpl_role` WHERE role_id='2');

-- ----------------------------
-- Records of nmpl_user
-- ----------------------------
INSERT INTO `nmpl_user` (`user_id`,`login_account`,`nick_name`,`user_type`,`email`,`phone_number`,`password`,`role_id`,`status`,`is_exist`,`remark`,`create_user`)
SELECT'1','superAdmin','superAdmin','00',NULL,'00000000000','j2sivmjjihBLggve6ed5lw==','1','1','1',NULL,'-1'
    WHERE NOT EXISTS (SELECT * FROM `nmpl_user` WHERE `user_id` = '1');

INSERT INTO `nmpl_user` (`user_id`,`login_account`,`nick_name`,`user_type`,`email`,`phone_number`,`password`,`role_id`,`status`,`is_exist`,`remark`,`create_user`)
SELECT'2','admin','admin','00',NULL,'00000000000','j2sivmjjihBLggve6ed5lw==','2','1','1',NULL,'-1'
    WHERE NOT EXISTS (SELECT * FROM `nmpl_user` WHERE `user_id` = '2');

truncate  `nmp_center`.`nmpl_menu`;

INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('1', '用户权限管理', '-1', '/userCenter', '0', '0', '1', NULL, NULL, '1', 'user', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('2', '角色管理', '1', '/role', '0', '1', '1', 'sys:role:query', NULL, '1', '', '0', 'user/role');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('3', '用户管理', '1', '/user', '0', '1', '1', 'sys:user:query',  NULL, '1', '', '0', 'user');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('6', '指控中心管理', '-1', '/monitor', '0', '0', '1', NULL,  NULL, '1', 'cpu', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('8', '日志管理', '-1', '/log', '0', '0', '1', NULL,  NULL, '1', 'notification', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('10', '系统设置', '-1', '/setting', '0', '0', '1', NULL,  NULL, '1', 'setting', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('11', '新建角色', '2', NULL, '0', '2', '1', 'sys:role:save', NULL, '1', '', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('12', '删除角色', '2', NULL, '0', '2', '1', 'sys:role:delete',  NULL, '1', '', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('13', '编辑角色', '2', NULL, '0', '2', '1', 'sys:role:update',  NULL, '1', '', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('14', '设置权限', '2', NULL, '0', '2', '1', 'sys:role:permersion',  NULL, '1', '', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('15', '新建用户', '3', NULL, '0', '2', '1', 'sys:user:save',  NULL, '1', '', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('16', '删除用户', '3', NULL, '0', '2', '1', 'sys:user:delete',  NULL, '1', '', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('17', '编辑用户', '3', NULL, '0', '2', '1', 'sys:user:update',  NULL, '1', '', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('18', '指控中心', '6', '/charge', '0', '1', '1', 'sys:accusation:query',  NULL, '1', '', '0', 'monitor');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('19', '小区详情', '18', NULL, '0', '2', '1', 'sys:communityDetail:query',  NULL, '1', '', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('20', '登录日志', '8', '/log/login', '0', '1', '1', 'sys:loginLog:query',  NULL, '1', '', '0', 'log/login');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('21', '操作日志', '8', '/log/operate', '0', '1', '1', 'sys:operateLog:query',  NULL, '1', '', '0', 'log/operate');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('22', '基础设置', '10', '/setting/base', '0', '1', '1', 'sys:basicSetting:query',  NULL, '1', '', '0', 'setting/base');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('23', '字典表查询', '10', '/setting/dictionary', '0', '1', '1', 'sys:dictionary:query',  NULL, '1', '', '0', 'setting/dictionary');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('24', '编辑设置', '22', NULL, '0', '2', '1', 'sys:basicSetting:update',  NULL, '1', '', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('25', '导入字典表', '23', NULL, '0', '2', '1', 'sys:dictionary:export',  NULL, '1', '', '0', '');
INSERT INTO `nmp_center`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('26', '设置密码', '3', NULL, '0', '2', '1', 'sys:user:changePasswd',  NULL, '1', '', '0', '');


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
