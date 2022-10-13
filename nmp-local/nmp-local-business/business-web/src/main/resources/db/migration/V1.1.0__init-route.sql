CREATE TABLE IF NOT EXISTS nmpl_business_route
(
    id            bigint auto_increment comment '主键'
        primary key,
    route_id      varchar(128)                             not null comment '路由Id',
    business_type varchar(90)                              not null comment '业务类型',
    network_id    varchar(32)                              not null comment '设备入网码',
    ip            varchar(32)                              not null comment 'ip',
    create_user   varchar(64)                              null comment '创建者',
    create_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user   varchar(64)                              null comment '更新者',
    update_time   datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    is_exist      tinyint(1)  default 1                    null comment '1:存在 0:删除'
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci comment '业务服务路由';

CREATE TABLE IF NOT EXISTS nmpl_internet_route
(
    id                  bigint auto_increment comment '主键'
        primary key,
    route_id            varchar(128)                             not null comment '路由Id',
    network_id          varchar(32)                              not null comment '设备入网码',
    boundary_station_ip varchar(32)                              not null comment '边界基站ip',
    create_user         varchar(64)                              null comment '创建者',
    create_time         datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user         varchar(64)                              null comment '更新者',
    update_time         datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    is_exist            tinyint(1)  default 1                    null comment '1:存在 0:删除'
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci comment '出网路由';

CREATE TABLE IF NOT EXISTS nmpl_static_route
(
    id          bigint auto_increment comment '主键'
        primary key,
    route_id    varchar(128)                             not null comment '路由Id',
    network_id  varchar(32)                              not null comment '设备入网码',
    server_ip   varchar(32)                              not null comment '服务器ip',
    is_exist    tinyint(1)  default 1                    not null comment '删除标志（1代表存在 0代表删除）',
    create_user varchar(64)                              null comment '创建者',
    create_time datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user varchar(64)                              null comment '更新者',
    update_time datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    station_id  varchar(128)                             not null comment '基站id'
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci comment '静态路由';