DROP TABLE IF EXISTS `nmpl_version`;
DROP TABLE IF EXISTS `nmpl_version_file`;
CREATE TABLE IF NOT EXISTS `nmpl_version` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `system_type` varchar(8) NOT NULL COMMENT 'QIBS:基站 QEBS:边界基站 QKC:密钥中心 QNMP:网管代理',
    `version_no` varchar(16) NOT NULL COMMENT '版本号',
    `file_path` varchar(32) NOT NULL COMMENT '文件路径',
    `file_name` varchar(32) NOT NULL COMMENT '文件名称',
    `file_size` varchar(32) NOT NULL COMMENT '文件大小（mb）',
    `version_desc` varchar(128) NOT NULL COMMENT '版本描述',
    `is_delete` tinyint(1) DEFAULT '1' COMMENT '1存在 0删除',
    `upload_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '上传时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `create_user` varchar(50) DEFAULT NULL,
    `update_user` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='版本文件表';

ALTER TABLE `nmpl_base_station_info` add column `version_id` bigint  COMMENT '版本文件id';
ALTER TABLE `nmpl_base_station_info` add column `version_no` varchar(16) DEFAULT NULL COMMENT '版本号';
ALTER TABLE `nmpl_base_station_info` add column `file_name` varchar(32) DEFAULT NULL COMMENT '文件名称';
ALTER TABLE `nmpl_base_station_info` add column `version_status` varchar(2) DEFAULT '1' COMMENT '1:未加载 2:已加载 3:运行中 4:已停止';
ALTER TABLE `nmpl_base_station_info` add column `version_oper_time` TIMESTAMP(2) NULL COMMENT '版本修改时间';

ALTER TABLE `nmpl_device_info` add column `version_id` bigint  COMMENT '版本文件id';
ALTER TABLE `nmpl_device_info` add column `version_no` varchar(16) DEFAULT NULL COMMENT '版本号';
ALTER TABLE `nmpl_device_info` add column `file_name` varchar(32) DEFAULT NULL COMMENT '文件名称';
ALTER TABLE `nmpl_device_info` add column `version_status` varchar(2) DEFAULT '1' COMMENT '1:未加载 2:已加载 3:运行中 4:已停止';
ALTER TABLE `nmpl_device_info` add column `version_oper_time` TIMESTAMP(2) NULL COMMENT '版本修改时间';
