ALTER TABLE nmpl_base_station_info ADD `byte_network_id` blob COMMENT '设备入网码';
ALTER TABLE nmpl_device_info ADD `byte_network_id` blob COMMENT '设备入网码';
ALTER TABLE nmpl_business_route ADD `byte_network_id` blob COMMENT '设备入网码';
ALTER TABLE nmpl_internet_route ADD `byte_network_id` blob COMMENT '设备入网码';
ALTER TABLE nmpl_static_route ADD `byte_network_id` blob COMMENT '设备入网码';
ALTER TABLE nmpl_business_route modify column  `network_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备入网码';
ALTER TABLE nmpl_static_route   modify column  `network_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备入网码';
ALTER TABLE nmpl_internet_route modify column  `network_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备入网码';