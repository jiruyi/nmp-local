CREATE TABLE IF NOT EXISTS `nmpl_pc_data` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                `station_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '基站设备id',
                                `device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '一体机设备id',
                                `pc_network_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '一体机设备入网码',
                                `status` tinyint NOT NULL COMMENT '设备状态 1:接入  2:未接入',
                                `key_num` int unsigned NOT NULL COMMENT '消耗密钥量(单位byte)',
                                `report_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '上报时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='基站下一体机信息上报表';