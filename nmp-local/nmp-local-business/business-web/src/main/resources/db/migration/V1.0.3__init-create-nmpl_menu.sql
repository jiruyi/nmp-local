DROP TABLE IF EXISTS `nmpl_menu`;
CREATE TABLE `nmpl_menu` (
    `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
    `parent_menu_id` bigint DEFAULT '-1' COMMENT '父菜单ID',
    `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求地址',
    `is_frame` tinyint DEFAULT '0' COMMENT '是否为外链（1是 0否）',
    `menu_type` tinyint DEFAULT NULL COMMENT '菜单类型（1目录 2菜单 3按钮）',
    `menu_status` tinyint DEFAULT '1' COMMENT '菜单状态（1正常 0停用）',
    `perms_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '权限标识',
    `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
    `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
    `is_exist` tinyint DEFAULT '1' COMMENT '1正常 0删除',
    PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单权限表';

-- ----------------------------
-- Records of nmpl_menu
-- ----------------------------
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (1, '运营商管理', -1, NULL, 0, 0, 1, '', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (2, '运营商管理', 1, NULL, 0, 1, 1, 'sys:operator:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (3, '新增运营商', 2, NULL, 0, 2, 1, 'sys:operator:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (4, '修改运营商', 2, NULL, 0, 2, 1, 'sys:operator:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (5, '删除运营商', 2, NULL, 0, 2, 1, 'sys:operator:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (6, '用户权限管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (7, '角色管理', 6, NULL, 0, 1, 1, 'sys:role:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (8, '新增角色', 7, NULL, 0, 2, 1, 'sys:role:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (9, '删除角色', 7, NULL, 0, 2, 1, 'sys:role:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (10, '查询角色', 7, NULL, 0, 2, 1, 'sys:role:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (11, '编辑角色', 7, NULL, 0, 2, 1, 'sys:role:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (12, '用户管理', 6, NULL, 0, 1, 1, 'sys:user:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (13, '新增用户', 12, NULL, 0, 2, 1, 'sys:user:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (14, '删除用户', 12, NULL, 0, 2, 1, 'sys:user:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (15, '查询用户', 12, NULL, 0, 2, 1, 'sys:user:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (16, '编辑用户', 12, NULL, 0, 2, 1, 'sys:user:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (17, '区域管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (18, '大区管理', 17, NULL, 0, 1, 1, 'sys:region:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (19, '小区管理', 17, NULL, 0, 1, 1, 'sys:village:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (20, '新增大区', 18, NULL, 0, 2, 1, 'sys:region:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (21, '删除大区', 18, NULL, 0, 2, 1, 'sys:region:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (22, '查询大区', 18, NULL, 0, 2, 1, 'sys:region:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (23, '编辑大区', 18, NULL, 0, 2, 1, 'sys:region:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (24, '新增小区', 19, NULL, 0, 2, 1, 'sys:village:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (25, '删除小区', 19, NULL, 0, 2, 1, 'sys:village:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (26, '查询小区', 19, NULL, 0, 2, 1, 'sys:village:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (27, '编辑小区', 19, NULL, 0, 2, 1, 'sys:village:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (28, '节点管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (29, '基站管理', 28, NULL, 0, 1, 1, 'sys:station:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (30, '分发机管理', 28, NULL, 0, 1, 1, 'sys:dispenser:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (31, '生成机管理', 28, NULL, 0, 1, 1, 'sys:generator:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (32, '缓存机管理', 28, NULL, 0, 1, 1, 'sys:cache:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (33, '新增基站', 29, NULL, 0, 2, 1, 'sys:station:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (34, '删除基站', 29, NULL, 0, 2, 1, 'sys:station:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (35, '查询基站', 29, NULL, 0, 2, 1, 'sys:station:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (36, '编辑基站', 29, NULL, 0, 2, 1, 'sys:station:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (37, '新增分发机', 30, NULL, 0, 2, 1, 'sys:dispenser:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (38, '删除分发机', 30, NULL, 0, 2, 1, 'sys:dispenser:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (39, '查询分发机', 30, NULL, 0, 2, 1, 'sys:dispenser:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (40, '编辑分发机', 30, NULL, 0, 2, 1, 'sys:dispenser:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (41, '新增生成机', 31, NULL, 0, 2, 1, 'sys:generator:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (42, '删除生成机', 31, NULL, 0, 2, 1, 'sys:generator:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (43, '查询生成机', 31, NULL, 0, 2, 1, 'sys:generator:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (44, '编辑生成机', 31, NULL, 0, 2, 1, 'sys:generator:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (45, '新增缓存机', 32, NULL, 0, 2, 1, 'sys:cache:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (46, '删除缓存机', 32, NULL, 0, 2, 1, 'sys:cache:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (47, '查询缓存机', 32, NULL, 0, 2, 1, 'sys:cache:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (48, '编辑缓存机', 32, NULL, 0, 2, 1, 'sys:cache:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (49, '链路管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (50, '链路管理', 49, NULL, 0, 1, 1, 'sys:link:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (51, '新增链路', 50, NULL, 0, 2, 1, 'sys:link:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (52, '删除链路', 50, NULL, 0, 2, 1, 'sys:link:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (53, '查询链路', 50, NULL, 0, 2, 1, 'sys:link:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (54, '编辑链路', 50, NULL, 0, 2, 1, 'sys:link:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (55, '路由管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (56, '路由管理', 55, NULL, 0, 1, 1, 'sys:route:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (57, '新增路由', 56, NULL, 0, 2, 1, 'sys:route:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (58, '删除路由', 56, NULL, 0, 2, 1, 'sys:route:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (59, '查询路由', 56, NULL, 0, 2, 1, 'sys:route:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (60, '编辑路由', 56, NULL, 0, 2, 1, 'sys:route:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (61, '配置管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (62, '参数配置', 61, NULL, 0, 1, 1, 'sys:parm:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (63, '权限菜单', 61, NULL, 0, 1, 1, 'sys:power:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (64, '恢复默认参数', 62, NULL, 0, 2, 1, 'sys:parm:reset', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (65, '删除参数', 62, NULL, 0, 2, 1, 'sys:parm:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (66, '查询参数', 62, NULL, 0, 2, 1, 'sys:parm:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (67, '编辑参数', 62, NULL, 0, 2, 1, 'sys:parm:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (68, '同步参数', 62, NULL, 0, 2, 1, 'sys:parm:synchro', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (69, '查询权限', 63, NULL, 0, 2, 1, 'sys:power:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (70, '状态监控', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (71, '设备数据', 70, NULL, 0, 1, 1, 'sys:sign:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (72, '设备查询', 71, NULL, 0, 2, 1, 'sys:sign:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (73, '信令管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (74, '信令上报', 73, NULL, 0, 1, 1, 'sys:sign:query,sys:sign:selectDeviceIds,sys:sign:deviceIds', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (75, '开启追踪', 74, NULL, 0, 2, 1, '', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (76, '停止追踪', 74, NULL, 0, 2, 1, 'sys:sign:untrack', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (77, '清空', 74, NULL, 0, 2, 1, 'sys:sign:clear', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (78, '导出文件', 74, NULL, 0, 2, 1, 'sys:sign:export', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (79, '日志管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (80, '登录日志', 79, NULL, 0, 1, 1, 'sys:loginLog:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (81, '操作日志', 79, NULL, 0, 1, 1, 'sys:operateLog:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (82, '系统日志', 79, NULL, 0, 1, 1, 'sys:sysLog:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (83, '查询登录日志', 80, NULL, 0, 2, 1, 'sys:loginLog:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (84, '查询操作日志', 81, NULL, 0, 2, 1, 'sys:operateLog:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (85, '查询系统日志', 82, NULL, 0, 2, 1, 'sys:sysLog:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (86, '统计管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (87, '基站统计数据', 86, NULL, 0, 1, 1, 'sys:stationData:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (88, '分发机统计数据', 86, NULL, 0, 1, 1, 'sys:dispenserData:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (89, '生成机统计数据', 86, NULL, 0, 1, 1, 'sys:generatorData:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (90, '缓存机统计数据', 86, NULL, 0, 1, 1, 'sys:cacheData:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (91, '查询基站统计数据', 87, NULL, 0, 2, 1, 'sys:stationData:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (92, '查询分发机统计数据', 88, NULL, 0, 2, 1, 'sys:dispenserData:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (93, '查询生成机统计数据', 89, NULL, 0, 2, 1, 'sys:generatorData:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (94, '查询缓存机统计数据', 90, NULL, 0, 2, 1, 'sys:cacheData:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (95, '话单管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (96, '话单上报', 95, NULL, 0, 1, 1, 'sys:bill:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (97, '查询话单', 96, NULL, 0, 2, 1, 'sys:bill:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (98, '版本管理', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (99, '版本列表', 98, NULL, 0, 1, 1, 'sys:version:fileList', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (100, '新建版本', 99, NULL, 0, 2, 1, 'sys:version:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (101, '上传版本文件', 99, NULL, 0, 2, 1, 'sys:version:import', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (102, '删除版本文件', 99, NULL, 0, 2, 1, 'sys:version:deleteFile', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (103, '推送设备', 99, NULL, 0, 2, 1, 'sys:version:push', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (104, '启动', 99, NULL, 0, 2, 1, 'sys:version:start', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (105, '详情', 99, NULL, 0, 2, 1, 'sys:version:detail', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (106, '运营商查询', 2, NULL, 0, 2, 1, 'sys:operator:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (108, '监控轮询展示查询', 71, NULL, 0, 2, 1, 'sys:monitor:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (109, '总带宽负载变化查询', 71, NULL, 0, 2, 1, 'sys:monitor:totalload', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (110, '信令轮询分页查询', 74, NULL, 0, 2, 1, 'sys:sign:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (112, '信令查询已选设备列表', 74, NULL, 0, 2, 1, 'sys:sign:selectDeviceIds', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (113, '查询信令设备列表', 74, NULL, 0, 2, 1, 'sys:sign:deviceIds', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (114, '查询版本文件列表', 99, NULL, 0, 2, 1, 'sys:version:fileList', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (116, '修改用户密码', 12, NULL, 0, 2, 1, 'sys:user:changePasswd', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (117, '版本列表查询', 99, NULL, 0, 2, 1, 'sys:version:list', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (118, '信令上报', 74, NULL, 0, 2, 1, 'sys:sign:track', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (119, '离线分发机', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (120, '离线分发机管理', 119, NULL, 0, 1, 1, 'sys:sorter:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (121, '新增离线分发机', 120, NULL, 0, 2, 1, 'sys:sorter:insert', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (122, '修改离线分发机', 120, NULL, 0, 2, 1, 'sys:sorter:modify', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (123, '查询离线分发机', 120, NULL, 0, 2, 1, 'sys:sorter:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (124, '删除离线分发机', 120, NULL, 0, 2, 1, 'sys:sorter:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (125, '上传离线分发机', 120, NULL, 0, 2, 1, 'sys:sorter:upload', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (126, '一体机', -1, NULL, 0, 0, 1, NULL, NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (127, '一体机管理', 126, NULL, 0, 1, 1, 'sys:pc:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (128, '新增一体机', 127, NULL, 0, 2, 1, 'sys:pc:insert', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (129, '删除一体机', 127, NULL, 0, 2, 1, 'sys:pc:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (130, '查询一体机', 127, NULL, 0, 2, 1, 'sys:pc:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (131, '修改一体机', 127, NULL, 0, 2, 1, 'sys:pc:modify', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (132, '上传一体机', 127, NULL, 0, 2, 1, 'sys:pc:upload', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (133, '主备管理', 28, NULL, 0, 1, 1, 'sys:deviceExtra:query', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (134, '新增备用机', 133, NULL, 0, 2, 1, 'sys:deviceExtra:save', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (135, '修改备用机', 133, NULL, 0, 2, 1, 'sys:deviceExtra:update', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (136, '删除备用机', 133, NULL, 0, 2, 1, 'sys:deviceExtra:delete', NULL, 1);
INSERT INTO `nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `remark`, `is_exist`)values (137, '查询备用机', 133, NULL, 0, 2, 1, 'sys:deviceExtra:query', NULL, 1);