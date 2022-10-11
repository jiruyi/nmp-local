UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:staticRoute:select' WHERE (`menu_id`='137');
UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:businessRoute:select' WHERE (`menu_id`='138');
UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:internetRoute:select'  WHERE (`menu_id`='139');
UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:staticRoute:insert'  WHERE (`menu_id`='140');
UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:staticRoute:delete' WHERE (`menu_id`='141');
UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:staticRoute:update'  WHERE (`menu_id`='142');
UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:businessRoute:insert'  WHERE (`menu_id`='143');
UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:businessRoute:update'  WHERE (`menu_id`='144');
UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:internetRoute:insert' WHERE (`menu_id`='145');
UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:internetRoute:update'  WHERE (`menu_id`='146');
UPDATE `nmp_local`.`nmpl_menu` SET `perms_code`='sys:internetRoute:delete'  WHERE (`menu_id`='147');


delete from nmpl_menu where menu_id = '148';
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`, `icon`, `permission`, `component`)
VALUES ('148', '详情', '29', NULL, '0', '2', '1', 'sys:station:detail',NULL, '1', '', '0', '');