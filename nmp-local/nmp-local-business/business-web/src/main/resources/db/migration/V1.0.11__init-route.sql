CREATE TABLE IF NOT EXISTS `nmpl_business_route` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `route_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由Id',
                                       `business_type` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务类型',
                                       `network_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备入网码',
                                       `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'ip_v4',
                                       `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                       `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                       `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                       `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                       `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
                                       `ip_v6` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'ip_v6',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='业务服务路由';

CREATE TABLE IF NOT EXISTS `nmpl_internet_route` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `route_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由Id',
                                       `network_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备入网码',
                                       `boundary_station_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '边界基站ip_v4',
                                       `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                       `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                       `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                       `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                       `is_exist` tinyint(1) DEFAULT '1' COMMENT '1:存在 0:删除',
                                       `ip_v6` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '边界基站ip_v6',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出网路由';

CREATE TABLE IF NOT EXISTS `nmpl_static_route` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                     `route_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由Id',
                                     `network_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备入网码',
                                     `server_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务器ip_v6',
                                     `is_exist` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
                                     `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                     `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                     `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                     `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                     `station_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '基站id',
                                     `ip_v6` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务器ip_v6',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='静态路由';

INSERT INTO nmp_local.nmpl_business_route (route_id, business_type, network_id, ip) VALUES ('1', '根密钥服务', '', '');
INSERT INTO nmp_local.nmpl_business_route (route_id, business_type, network_id, ip) VALUES ('2', '计费服务', '', '');
