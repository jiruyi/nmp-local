/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.27 : Database - nmp_proxy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `nmp_proxy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  /*!80016 DEFAULT ENCRYPTION='N' */;

USE `nmp_proxy`;


create table nmpl_alarm_info
(
    alarm_id           bigint auto_increment comment '主键'
        primary key,
    alarm_source_id    varchar(128)                             null comment '业务系统id  物理设备无',
    alarm_source_ip    varchar(15)                              null comment '设备ip',
    alarm_content      varchar(1024)                            null comment '告警内容',
    alarm_level        char(2)                                  null comment '级别 1严重 2 紧急 3 一般',
    alarm_upload_time  datetime(2)                              not null comment '操作时间',
    alarm_source_type  char(2)                                  null comment '来源类型： 00资源告警 01接入告警  02边界  11 密钥中心',
    alarm_content_type char(2)     default '5'                  null comment '告警内容类型  1: cpu 2 内存 3 磁盘 4流量 5 其他',
    create_time        datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time        datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
);

create index idx_device_id
    on nmpl_alarm_info (alarm_source_id);

create index upload_time_key
    on nmpl_alarm_info (alarm_upload_time);

create table nmpl_base_station_info
(
    id                   bigint auto_increment comment '主键'
        primary key,
    station_id           varchar(128)                             not null comment '设备id',
    station_name         varchar(16)                              null comment '设备名称',
    station_type         char(2)     default '01'                 null comment '设备类型 01:小区内基站 02:小区边界基站',
    enter_network_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '接入网时间',
    station_admain       varchar(20)                              null comment '设备管理员',
    remark               varchar(256)                             null comment '设备备注',
    public_network_ip    varchar(16)                              null comment '公网Ip',
    public_network_port  varchar(128)                             null comment '公网端口',
    lan_ip               varchar(16)                              null comment '局域网ip',
    lan_port             varchar(8)                               null comment '局域网port',
    station_status       char(2)     default '01'                 null comment '设备状态 01:静态  02:激活  03:禁用 04:下线',
    station_network_id   varchar(32)                              null comment '设备入网码',
    station_random_seed  varchar(64)                              null comment '加密随机数',
    relation_operator_id varchar(50)                              null comment '关联小区',
    create_user          varchar(20)                              null comment '创建人',
    create_time          datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user          varchar(20)                              null comment '修改人',
    update_time          datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist             tinyint(1)  default 1                    null comment '1:存在 0:删除',
    byte_network_id      blob                                     null comment '设备入网码',
    prefix_network_id    bigint                                   null comment '入网码前缀',
    suffix_network_id    bigint                                   null comment '入网码后缀'
)
    comment '基站信息表';

create table nmpl_bill
(
    id            bigint auto_increment comment '主键'
        primary key,
    owner_id      varchar(32)                              null comment '账单用户id',
    stream_id     varchar(32)                              null comment '流id',
    flow_number   varchar(32)                              null comment '消耗流量',
    time_length   varchar(32)                              null comment '时长',
    key_num       varchar(32)                              null comment '消耗密钥量',
    hybrid_factor varchar(32)                              null comment '杂糅因子',
    upload_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    start_time    varchar(30)                              null comment '开始时间',
    end_time      varchar(30)                              null comment '结束时间'
)
    comment '话单上报表';

create table nmpl_business_route
(
    id              bigint                                   not null comment '主键'
        primary key,
    route_id        varchar(128)                             not null comment '路由Id',
    business_type   varchar(90)                              not null comment '业务类型',
    network_id      varchar(50)                              not null comment '设备入网码',
    ip              varchar(32)                              not null comment 'ip',
    create_user     varchar(64)                              null comment '创建者',
    create_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user     varchar(64)                              null comment '更新者',
    update_time     datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    is_exist        tinyint(1)  default 1                    null comment '1:存在 0:删除',
    ip_v6           varchar(64) charset utf8mb4              null comment 'ip_v6',
    byte_network_id blob                                     null comment '设备入网码(字节存储)'
)
    comment '业务服务路由' charset = utf8;

create table nmpl_config
(
    id            bigint                                   not null comment '主键'
        primary key,
    device_type   char(2)                                  null comment '01:接入基站 02:边界基站 11:密钥中心 20:数据采集',
    config_name   varchar(100)                             null comment '配置项名称',
    config_code   varchar(32)                              null comment '配置编码',
    config_value  varchar(2000)                            null comment '配置值',
    default_value varchar(2000)                            null comment '默认值',
    unit          varchar(32)                              null comment '单位',
    create_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time   datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    is_exist      tinyint(1)  default 1                    null comment '状态 true:存在(1)  false:删除(0)',
    remark        varchar(100)                             null comment '备注'
);

create table nmpl_data_collect
(
    id              bigint auto_increment comment '主键'
        primary key,
    device_id       varchar(128)                             not null comment '设备id',
    device_name     varchar(30)                              null comment '设备名字',
    device_type     varchar(30)                              not null comment '设备类别(00基站、11密钥中心、12生成机、13缓存机)',
    device_ip       varchar(16)                              NOT NULL COMMENT '设备ip',
    data_item_name  varchar(50)                              not null comment '统计项名(剩余秘钥量;使用秘钥量)',
    data_item_code  varchar(32) charset utf8                 not null comment '收集项编号(10003;10001)',
    data_item_value varchar(32)                              not null comment '值',
    unit            varchar(32)                              null comment '单位',
    upload_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    create_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time     datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
);

create table nmpl_device_info
(
    id                   bigint auto_increment comment '主键'
        primary key,
    device_id            varchar(128)                             not null comment '设备编号',
    device_name          varchar(16)                              null comment '设备名称',
    device_type          char(2)     default '01'                 null comment '设备类型 11:密钥中心 12:生成机 13:缓存机',
    other_type           char(2)                                  null comment '备用类型 ',
    enter_network_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '接入网时间',
    device_admain        varchar(20)                              null comment '设备管理员',
    remark               varchar(256)                             null comment '设备备注',
    public_network_ip    varchar(16)                              null comment '公网Ip',
    public_network_port  varchar(8)                               null comment '公网端口',
    lan_ip               varchar(16)                              null comment '内网Ip',
    lan_port             varchar(8)                               null comment '内网端口',
    station_status       char(2)     default '01'                 null comment '设备状态 01:静态  02:激活  03:禁用 04:下线',
    station_network_id   varchar(32)                              null comment '设备入网码',
    station_random_seed  varchar(64)                              null comment '加密随机数',
    relation_operator_id varchar(50)                              null comment '关联区域',
    create_user          varchar(20)                              null comment '创建人',
    create_time          datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user          varchar(20)                              null comment '修改人',
    update_time          datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist             tinyint(1)  default 1                    null comment '1:存在 0:删除',
    byte_network_id      blob                                     null comment '设备入网码'
)
    comment '密钥分发和U盘';

create table nmpl_device_log
(
    id          bigint auto_increment comment '主键'
        primary key,
    device_id   varchar(128)                             null comment '设备id',
    devcie_name varchar(128)                             null comment '设备名称',
    device_type char        default '1'                  null comment '设备类型 1 基站',
    device_ip   varchar(15)                              null comment '设备ip',
    oper_type   varchar(12)                              null comment '设备操作 关机 初始化。。。',
    oper_desc   varchar(64)                              null comment '操作描述',
    oper_user   varchar(32)                              null comment '操作人',
    oper_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '操作时间',
    create_time datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
);

create index idx_device_id
    on nmpl_device_log (device_id);

create index idx_device_name
    on nmpl_device_log (devcie_name);

create table nmpl_error_push_log
(
    id        bigint auto_increment comment '自增主键'
        primary key,
    url       varchar(128) not null comment '推送url',
    data      longtext     not null comment '推送信息',
    error_msg varchar(255) null comment '异常信息'
)
    comment '代理异常推送日志';

create table nmpl_internet_route
(
    id                  bigint                                   not null comment '主键'
        primary key,
    route_id            varchar(128)                             not null comment '路由Id',
    network_id          varchar(50)                              not null comment '设备入网码',
    boundary_station_ip varchar(32)                              not null comment '边界基站ip',
    create_user         varchar(64)                              null comment '创建者',
    create_time         datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user         varchar(64)                              null comment '更新者',
    update_time         datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    is_exist            tinyint(1)  default 1                    null comment '1:存在 0:删除',
    ip_v6               varchar(64) charset utf8mb4              null comment '边界基站ip_v6',
    byte_network_id     blob                                     not null comment '设备入网码(字节存储)'
)
    comment '出网路由' charset = utf8;

create table nmpl_keycenter_heart_info
(
    device_id          varchar(128)                             not null comment '设备id',
    remark             char(2)                                  null comment '备注 0：内外网正常 1：外网异常',
    station_network_id varchar(32)                              null comment '设备入网码',
    create_time        datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间'
)
    comment '密钥中心心跳上报信息表';

create index device_id
    on nmpl_keycenter_heart_info (device_id);

create table nmpl_link
(
    id                bigint       not null comment '主键'
        primary key,
    link_name         varchar(32)  null comment '链路名称',
    link_type         smallint     null comment '链路类型: 1:单向,2:双向',
    main_device_id    varchar(20)  null comment '本端设备id',
    main_device_type  char(2)      null comment '设备类型 01:小区内基站 02:小区边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心',
    follow_device_id  varchar(20)  null comment '对端设备id',
    follow_network_id varchar(32)  null comment '对端设备入网ID',
    follow_ip         varchar(32)  null comment '对端设备IP',
    follow_port       varchar(64)  null comment '对端设备端口',
    active_auth       tinyint(1)   null comment '主动发起认证 1:开启 0:关闭',
    is_on             tinyint(1)   null comment '是否启用 1:启动 0:禁止',
    create_user       varchar(100) null comment '创建人',
    create_time       datetime(2)  null comment '创建时间',
    update_user       varchar(100) null comment '修改人',
    update_time       datetime(2)  null comment '修改时间',
    is_exist          tinyint(1)   null comment '1:存在 0:删除'
)
    comment '链路信息表';

create table nmpl_local_base_station_info
(
    id                   bigint auto_increment comment '主键'
        primary key,
    station_id           varchar(128)                             not null comment '设备id',
    station_name         varchar(16)                              null comment '设备名称',
    station_type         char(2)     default '01'                 null comment '设备类型 01:小区内基站 02:小区边界基站',
    enter_network_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '接入网时间',
    station_admain       varchar(20)                              null comment '设备管理员',
    remark               varchar(256)                             null comment '设备备注',
    public_network_ip    varchar(16)                              null comment '公网Ip',
    public_network_port  varchar(128)                             null comment '公网端口',
    lan_ip               varchar(16)                              null comment '局域网ip',
    lan_port             varchar(8)                               null comment '局域网port',
    station_status       char(2)     default '01'                 null comment '设备状态 01:静态  02:激活  03:禁用 04:下线',
    station_network_id   varchar(32)                              null comment '设备入网码',
    station_random_seed  varchar(64)                              null comment '加密随机数',
    relation_operator_id varchar(50)                              null comment '关联小区',
    create_user          varchar(20)                              null comment '创建人',
    create_time          datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user          varchar(20)                              null comment '修改人',
    update_time          datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist             tinyint(1)  default 1                    null comment '1:存在 0:删除',
    byte_network_id      blob                                     null comment '设备入网码',
    prefix_network_id    bigint                                   null comment '入网码前缀',
    suffix_network_id    bigint                                   null comment '入网码后缀'
)
    comment '本机基站信息表';

create table nmpl_local_device_info
(
    id                   bigint auto_increment comment '主键'
        primary key,
    device_id            varchar(128)                             not null comment '设备编号',
    device_name          varchar(16)                              null comment '设备名称',
    device_type          char(2)     default '01'                 null comment '设备类型 11:密钥中心 12:生成机 13:缓存机',
    other_type           char(2)                                  null comment '备用类型 ',
    enter_network_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '接入网时间',
    device_admain        varchar(20)                              null comment '设备管理员',
    remark               varchar(256)                             null comment '设备备注',
    public_network_ip    varchar(16)                              null comment '公网Ip',
    public_network_port  varchar(8)                               null comment '公网端口',
    lan_ip               varchar(16)                              null comment '内网Ip',
    lan_port             varchar(8)                               null comment '内网端口',
    station_status       char(2)     default '01'                 null comment '设备状态 01:静态  02:激活  03:禁用 04:下线',
    station_network_id   varchar(32)                              null comment '设备入网码',
    station_random_seed  varchar(64)                              null comment '加密随机数',
    relation_operator_id varchar(50)                              null comment '关联区域',
    create_user          varchar(20)                              null comment '创建人',
    create_time          datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user          varchar(20)                              null comment '修改人',
    update_time          datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist             tinyint(1)  default 1                    null comment '1:存在 0:删除',
    byte_network_id      blob                                     null comment '设备入网码'
)
    comment '本机密钥分发和U盘';

create table nmpl_outline_pc_info
(
    id                 bigint                                   not null comment '主键'
        primary key,
    device_id          varchar(128)                             not null comment '设备编号',
    device_name        varchar(16)                              null comment '设备名称',
    station_network_id varchar(32)                              null comment '设备入网码',
    remark             varchar(256)                             null comment '设备备注',
    create_user        varchar(20)                              null comment '创建人',
    create_time        datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user        varchar(20)                              null comment '修改人',
    update_time        datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist           tinyint(1)  default 1                    null comment '1:存在 0:删除',
    swing_in           tinyint(1)  default 1                    null comment '是否摆入',
    swing_out          tinyint(1)  default 1                    null comment '是否摆出'
)
    comment '一体机信息表';

create table nmpl_pc_data
(
    id            bigint auto_increment comment '自增主键'
        primary key,
    station_id    varchar(128)                             not null comment '基站设备id',
    device_id     varchar(128)                             not null comment '一体机设备id',
    pc_network_id varchar(32)                              not null comment '一体机设备入网码',
    status        tinyint                                  not null comment '设备状态 1:接入  2:未接入',
    up_key_num    int unsigned                             not null comment '上行消耗密钥量(单位byte)',
    down_key_num  int unsigned                             not null comment '下行消耗密钥量(单位byte)',
    report_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '上报时间'
)
    comment '基站下一体机信息上报表';

create table nmpl_static_route
(
    id              bigint                                   not null comment '主键'
        primary key,
    route_id        varchar(128)                             not null comment '路由Id',
    network_id      varchar(50)                              not null comment '设备入网码',
    server_ip       varchar(32)                              not null comment '服务器ip',
    is_exist        tinyint(1)  default 1                    not null comment '删除标志（1代表存在 0代表删除）',
    create_user     varchar(64)                              null comment '创建者',
    create_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user     varchar(64)                              null comment '更新者',
    update_time     datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    station_id      varchar(128)                             not null comment '基站id',
    ip_v6           varchar(64) charset utf8mb4              null comment '服务器ip_v6',
    byte_network_id blob                                     null comment '设备入网码(字节存储)'
)
    comment '静态路由' charset = utf8;

create table nmpl_station_connect_count
(
    id                    bigint auto_increment comment '主键'
        primary key,
    station_id            varchar(128)                             not null comment '设备Id',
    current_connect_count varchar(10)                              null comment '当前用户数',
    create_time           datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time           datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    upload_time           datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '上传时间'
)
    comment '当前用户在线表';

create table nmpl_station_heart_info
(
    station_id         varchar(128)                             not null comment '设备id',
    remark             char(2)                                  null comment '备注 0：内外网正常 1：外网异常',
    station_network_id varchar(32)                              null comment '设备入网码',
    create_time        datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间'
)
    comment '基站心跳上报信息表';

create index station_id
    on nmpl_station_heart_info (station_id);

create table nmpl_update_info_base
(
    id             bigint auto_increment comment '主键'
        primary key,
    table_name     varchar(128)                             not null comment '更新表名',
    operation_type varchar(8)                               null comment '操作类型：新增:1 修改:2',
    create_user    varchar(20)                              null comment '创建人',
    create_time    datetime(2)                              not null comment '创建时间',
    update_user    varchar(20)                              null comment '修改人',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist       tinyint(1)  default 1                    null comment '1:存在 0:删除'
)
    comment '数据更新信息表(基站专用)';

create table nmpl_update_info_boundary
(
    id             bigint auto_increment comment '主键'
        primary key,
    table_name     varchar(128)                             not null comment '更新表名',
    operation_type varchar(8)                               null comment '操作类型：新增:1 修改:2',
    create_user    varchar(20)                              null comment '创建人',
    create_time    datetime(2)                              not null comment '创建时间',
    update_user    varchar(20)                              null comment '修改人',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist       tinyint(1)  default 1                    null comment '1:存在 0:删除'
)
    comment '数据更新信息表(边界基站专用)';

create table nmpl_update_info_cache
(
    id             bigint auto_increment comment '主键'
        primary key,
    table_name     varchar(128)                             not null comment '更新表名',
    operation_type varchar(8)                               null comment '操作类型：新增:1 修改:2',
    create_user    varchar(20)                              null comment '创建人',
    create_time    datetime(2)                              not null comment '创建时间',
    update_user    varchar(20)                              null comment '修改人',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist       tinyint(1)  default 1                    null comment '1:存在 0:删除'
)
    comment '数据更新信息表(缓存机专用)';

create table nmpl_update_info_generator
(
    id             bigint auto_increment comment '主键'
        primary key,
    table_name     varchar(128)                             not null comment '更新表名',
    operation_type varchar(8)                               null comment '操作类型：新增:1 修改:2',
    create_user    varchar(20)                              null comment '创建人',
    create_time    datetime(2)                              not null comment '创建时间',
    update_user    varchar(20)                              null comment '修改人',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist       tinyint(1)  default 1                    null comment '1:存在 0:删除'
)
    comment '数据更新信息表(生成机专用)';

create table nmpl_update_info_keycenter
(
    id             bigint auto_increment comment '主键'
        primary key,
    table_name     varchar(128)                             not null comment '更新表名',
    operation_type varchar(8)                               null comment '操作类型：新增:1 修改:2',
    create_user    varchar(20)                              null comment '创建人',
    create_time    datetime(2)                              not null comment '创建时间',
    update_user    varchar(20)                              null comment '修改人',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist       tinyint(1)  default 1                    null comment '1:存在 0:删除'
)
    comment '数据更新信息表(秘钥中心专用)';


CREATE TABLE `nmpl_system_heartbeat` (
                                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                         `source_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '来源Id',
                                         `target_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目标Id',
                                         `status` char(2) DEFAULT '01' COMMENT '连接状态 01:通  02:不通',
                                         `upload_time` datetime(2) DEFAULT NULL COMMENT '上报时间',
                                         `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                         `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='业务心跳';

CREATE TABLE `nmpl_company_heartbeat` (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                          `source_network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '来源Id',
                                          `target_network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目标Id',
                                          `status` char(2) DEFAULT '01' COMMENT '连接状态 01:通  02:不通',
                                          `up_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '上行流量',
                                          `down_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '下行流量',
                                          `upload_time` datetime(2) DEFAULT NULL COMMENT '上报时间',
                                          `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                          `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小区业务心跳';

CREATE TABLE `nmpl_terminal_data` (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                      `terminal_network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '一体机设备id',
                                      `parent_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '基站设备id',
                                      `data_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据类型 01:剩余 02:补充 03:使用',
                                      `up_value` bigint NOT NULL COMMENT '上行密钥量',
                                      `down_value` bigint NOT NULL COMMENT '下行密钥量',
                                      `terminal_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '一体机ip',
                                      `upload_time` datetime(2) DEFAULT NULL COMMENT '上报时间',
                                      `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                      `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='基站下一体机信息上报表';

CREATE TABLE `nmpl_terminal_user` (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `terminal_network_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '终端设备Id',
                                      `parent_device_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属设备Id',
                                      `terminal_status` char(2) DEFAULT '01' COMMENT '用户状态 01:密钥匹配  02:注册  03:上线 04:下线 05:注销',
                                      `upload_time` datetime(2) DEFAULT NULL COMMENT '上报时间',
                                      `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                      `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                      `user_type` char(2) DEFAULT '01' COMMENT '用户类型 01:一体机  02:安全服务器',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='终端用户表';





/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
