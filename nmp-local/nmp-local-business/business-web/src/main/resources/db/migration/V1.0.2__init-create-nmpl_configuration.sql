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

TRUNCATE TABLE `nmpl_configuration`;

INSERT INTO nmp_local.nmpl_configuration (id, device_id, real_ip, real_port, url, type, device_type) VALUES (1, null, null, '20055', 'nc-local-business/paramSync', '1', '1');
INSERT INTO nmp_local.nmpl_configuration (id, device_id, real_ip, real_port, url, type, device_type) VALUES (2, null, null, '20055', 'nc-local-business/signalSwitch', '2', '1');
INSERT INTO nmp_local.nmpl_configuration (id, device_id, real_ip, real_port, url, type, device_type) VALUES (3, null, null, '20055', 'nc-local-business/newVer', '3', '1');
INSERT INTO nmp_local.nmpl_configuration (id, device_id, real_ip, real_port, url, type, device_type) VALUES (4, null, null, '20055', 'nc-local-business/updateVer', '4', '1');
INSERT INTO nmp_local.nmpl_configuration (id, device_id, real_ip, real_port, url, type, device_type) VALUES (5, null, null, '20055', 'nc-local-business/routerSync', '5', '1');
INSERT INTO nmp_local.nmpl_configuration (id, device_id, real_ip, real_port, url, type, device_type) VALUES (6, null, null, '20055', 'nc-local-business/linkSync', '6', '1');
INSERT INTO nmp_local.nmpl_configuration (id, device_id, real_ip, real_port, url, type, device_type) VALUES (7, null, null, '8805', 'v2/config/update', '1', '2');
