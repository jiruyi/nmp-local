ALTER TABLE nmpl_base_station_info MODIFY `run_file_name` varchar(64) DEFAULT NULL COMMENT '运行版本文件名称';
ALTER TABLE nmpl_base_station_info MODIFY `load_file_name` varchar(64) DEFAULT NULL COMMENT '加载版本文件名称';
ALTER TABLE nmpl_device_info MODIFY `run_file_name` varchar(64) DEFAULT NULL COMMENT '运行版本文件名称';
ALTER TABLE nmpl_device_info MODIFY `load_file_name` varchar(64) DEFAULT NULL COMMENT '加载版本文件名称';