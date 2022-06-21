CREATE TABLE IF NOT EXISTS `nmpl_configuration` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '设备编号',
    `real_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内网Ip',
    `real_port` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内网端口',
    `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '路径',
    `type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '路径类型 1：配置同步 2：信令启停 3：推送版本文件 4：启动版本文件',
    `device_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '设备类型 1:基站 2 分发机 3 生成机 4 缓存机',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;