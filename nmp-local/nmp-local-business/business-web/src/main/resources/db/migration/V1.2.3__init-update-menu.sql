INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('155', '设备服务管理', '98', '/deviceservice', '0', '1', '1', 'sys:versionControl:queryRunVersion', NULL,  '1', '', '1', 'version/deviceservice');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('156', '版本管理', '98', '/deviceversion', '0', '1', '1', 'sys:versionControl:queryLoadVersion', NULL,  '1', '', '1', 'version/deviceversion');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('157', '启动', '155', NULL, '0', '2', '1', 'sys:versionControl:run', NULL,  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('158', '停止', '155', NULL, '0', '2', '1', 'sys:versionControl:stop', NULL,  '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('159', '卸载', '155', NULL, '0', '2', '1', 'sys:versionControl:uninstall', NULL,'1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('160', '加载', '156', NULL, '0', '2', '1', 'sys:versionControl:load', NULL,'1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('161', '加载运行', '156', NULL, '0', '2', '1', 'sys:versionControl:loadAndRun', NULL,'1', '', '1', '');
