ALTER TABLE `nmpl_base_station_info` DROP `version_id`;
ALTER TABLE `nmpl_base_station_info` DROP `version_no`;
ALTER TABLE `nmpl_base_station_info` DROP `file_name`;
ALTER TABLE `nmpl_base_station_info` DROP `version_status`;
ALTER TABLE `nmpl_base_station_info` DROP `version_oper_time`;

ALTER TABLE `nmpl_base_station_info` add column `run_version_id` bigint  COMMENT '运行版本文件id';
ALTER TABLE `nmpl_base_station_info` add column `run_version_no` varchar(16) DEFAULT NULL COMMENT '运行版本号';
ALTER TABLE `nmpl_base_station_info` add column `run_file_name` varchar(32) DEFAULT NULL COMMENT '运行版本文件名称';
ALTER TABLE `nmpl_base_station_info` add column `run_version_status` varchar(2) DEFAULT '1' COMMENT '运行状态 1:未运行 2:运行中 3:已停止';
ALTER TABLE `nmpl_base_station_info` add column `run_version_oper_time` TIMESTAMP(2) NULL COMMENT '运行版本操作时间';
ALTER TABLE `nmpl_base_station_info` add column `load_version_no` varchar(16) DEFAULT NULL COMMENT '加载版本号';
ALTER TABLE `nmpl_base_station_info` add column `load_version_id` bigint  COMMENT '加载版本文件id';
ALTER TABLE `nmpl_base_station_info` add column `load_version_oper_time` TIMESTAMP(2) NULL COMMENT '加载版本操作时间';

ALTER TABLE `nmpl_device_info` DROP `version_id`;
ALTER TABLE `nmpl_device_info` DROP `version_no`;
ALTER TABLE `nmpl_device_info` DROP `file_name`;
ALTER TABLE `nmpl_device_info` DROP `version_status`;
ALTER TABLE `nmpl_device_info` DROP `version_oper_time`;

ALTER TABLE `nmpl_device_info` add column `run_version_id` bigint  COMMENT '运行版本文件id';
ALTER TABLE `nmpl_device_info` add column `run_version_no` varchar(16) DEFAULT NULL COMMENT '运行版本号';
ALTER TABLE `nmpl_device_info` add column `run_file_name` varchar(32) DEFAULT NULL COMMENT '运行版本文件名称';
ALTER TABLE `nmpl_device_info` add column `run_version_status` varchar(2) DEFAULT '1' COMMENT '运行状态 1:未运行 2:运行中 3:已停止';
ALTER TABLE `nmpl_device_info` add column `run_version_oper_time` TIMESTAMP(2) NULL COMMENT '运行版本操作时间';
ALTER TABLE `nmpl_device_info` add column `load_version_no` varchar(16) DEFAULT NULL COMMENT '加载版本号';
ALTER TABLE `nmpl_device_info` add column `load_version_id` bigint  COMMENT '加载版本文件id';
ALTER TABLE `nmpl_device_info` add column `load_version_oper_time` TIMESTAMP(2) NULL COMMENT '加载版本操作时间';