-- ----------------------------
-- Table structure for nmpl_data_collect_for_load
-- ----------------------------
CREATE TABLE IF NOT EXISTS `nmpl_data_collect_for_load` (
                                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                   `device_id` varchar(32) DEFAULT NULL COMMENT '设备id',
    `device_name` varchar(30) DEFAULT NULL COMMENT '设备名字',
    `device_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '设备类别(01基站、02分发机、03生成机、04缓存机)',
    `data_item_name` varchar(50) DEFAULT NULL COMMENT '统计项名',
    `data_item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收集项编号',
    `data_item_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' COMMENT '值',
    `unit` varchar(32) DEFAULT NULL COMMENT '单位',
    `upload_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create
definer = root@`%` procedure truncateTmpLoadData()
BEGIN
truncate table nmpl_data_collect_for_load;
END;

create definer = root@`%` event event_data_collect_for_load on schedule
    every '1' day
        starts '2022-06-01 00:00:00'
    enable
    do
    call truncateTmpLoadData();