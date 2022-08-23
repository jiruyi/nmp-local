alter table nmpl_device_info modify column device_type char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '11' comment '设备类型 11:密钥中心 12:生成机 13:缓存机';
alter table nmpl_data_collect modify column device_type varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '00' comment '设备类别(00基站、11密钥中心、12生成机、13缓存机)';
alter table nmpl_data_collect_for_load modify column device_type varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '00' comment '设备类别(00基站、11密钥中心、12生成机、13缓存机)';
alter table nmpl_device_extra_info modify column device_type char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '00' COMMENT '设备类型 00:基站 11:密钥中心 12:生成机 13:缓存机';
