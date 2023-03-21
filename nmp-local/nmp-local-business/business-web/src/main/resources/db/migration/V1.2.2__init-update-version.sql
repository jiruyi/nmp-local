ALTER TABLE nmpl_version MODIFY file_name varchar(64);

ALTER TABLE nmpl_version MODIFY file_path varchar(128);

ALTER TABLE nmpl_version MODIFY version_desc varchar(1024);

DELETE FROM nmpl_menu WHERE menu_id = '103';

DELETE FROM nmpl_menu WHERE menu_id = '104';

DELETE FROM nmpl_menu WHERE menu_id = '105';

DELETE FROM nmpl_menu WHERE menu_id = '101';


UPDATE `nmp_local`.`nmpl_menu` SET `menu_id`='99', `menu_name`='版本维护', `parent_menu_id`='98', `url`='/versionmanage', `is_frame`='0', `menu_type`='1', `menu_status`='1', `perms_code`='sys:version:fileList,sys:version:list', `create_user`='', `create_time`='2023-01-10 10:24:40.26', `update_user`='', `update_time`='2023-03-15 09:42:25.35', `remark`='', `is_exist`='1', `icon`='', `permission`='1', `component`='version/versionmanage' WHERE (`menu_id`='99');


INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('151', '更新版本文件', '99', '', '0', '2', '1', 'sys:version:updateFile', '', '2023-02-16 16:43:46.59', '', '2023-02-16 16:43:46.59', '', '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('152', '删除版本文件', '99', '', '0', '2', '1', 'sys:version:deleteFile', '', '2023-02-16 16:43:46.59', '', '2023-02-16 16:43:46.59', '', '1', '', '1', '');
