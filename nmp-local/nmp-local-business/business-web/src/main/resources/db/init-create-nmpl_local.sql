/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.27 : Database - nmp_local
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `nmp_local` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  /*!80016 DEFAULT ENCRYPTION='N' */;

USE `nmp_local`;

/*Table structure for table `nmpl_base_station_info` */

-- ----------------------------
-- Table structure for nmpl_base_station_info
-- ----------------------------
create table nmpl_alarm_info
(
    alarm_id           bigint auto_increment comment '主键'
        primary key,
    alarm_source_id    varchar(128)                             null comment '业务系统id  物理设备无',
    alarm_source_ip    varchar(15)                              null comment '设备ip',
    alarm_content      varchar(1024)                            null comment '告警内容',
    alarm_level        char(2)                                  null comment '级别 1严重 2 紧急 3 一般',
    alarm_upload_time  datetime(2)                              not null comment '操作时间',
    alarm_source_type  char(2)                                  null comment '来源类型： 1资源告警 2接入告警  3 边界  4 密钥中心',
    alarm_content_type char(2)     default '5'                  null comment '告警内容类型  1: cpu 2 内存 3 磁盘 4流量 5 其他',
    create_time        datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time        datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
);

create index idx_device_id
    on nmpl_alarm_info (alarm_source_id);

create index idx_source_ip
    on nmpl_alarm_info (alarm_source_ip);

create index upload_time_key
    on nmpl_alarm_info (alarm_upload_time);

create table nmpl_base_station_info
(
    id                     bigint auto_increment comment '主键'
        primary key,
    station_id             varchar(128)                             not null comment '设备id',
    station_name           varchar(16)                              null comment '设备名称',
    station_type           char(2)     default '01'                 null comment '设备类型 01:小区内基站 02:小区边界基站',
    enter_network_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '接入网时间',
    station_admain         varchar(20)                              null comment '设备管理员',
    remark                 varchar(256)                             null comment '设备备注',
    public_network_ip      varchar(16)                              null comment '公网Ip',
    public_network_port    varchar(128)                             null comment '公网端口',
    lan_ip                 varchar(16)                              null comment '局域网ip',
    lan_port               varchar(8)                               null comment '局域网port',
    station_status         char(2)     default '01'                 null comment '设备状态 01:静态  02:激活  03:禁用 04:下线',
    station_network_id     varchar(32)                              null comment '设备入网码',
    station_random_seed    varchar(64)                              null comment '加密随机数',
    relation_operator_id   varchar(50)                              null comment '关联小区',
    create_user            varchar(20)                              null comment '创建人',
    create_time            datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user            varchar(20)                              null comment '修改人',
    update_time            datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist               tinyint(1)  default 1                    null comment '1:存在 0:删除',
    byte_network_id        blob                                     null comment '设备入网码',
    prefix_network_id      bigint                                   null comment '入网码前缀',
    suffix_network_id      bigint                                   null comment '入网码后缀',
    run_version_id         bigint                                   null comment '运行版本文件id',
    run_version_no         varchar(16)                              null comment '运行版本号',
    run_file_name          varchar(64)                              null comment '运行版本文件名称',
    run_version_status     varchar(2)  default '1'                  null comment '运行状态 1:未运行 2:运行中 3:已停止',
    run_version_oper_time  timestamp(2)                             null comment '运行版本操作时间',
    load_version_no        varchar(16)                              null comment '加载版本号',
    load_version_id        bigint                                   null comment '加载版本文件id',
    load_version_oper_time timestamp(2)                             null comment '加载版本操作时间',
    load_file_name         varchar(64)                              null comment '加载版本文件名称',
    current_connect_count  varchar(10)                              null comment '当前用户数'
);

create table nmpl_bill
(
    id            bigint auto_increment comment '主键'
        primary key,
    owner_id      varchar(32)                              null comment '账单用户id',
    stream_id     varchar(32)                              null comment '流id',
    flow_number   varchar(32)                              null comment '消耗流量',
    time_length   varchar(32)                              null comment '时长',
    key_num       varchar(32)                              null comment '消耗密钥量',
    hybrid_factor varchar(32)                              null,
    upload_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    start_time    varchar(30)                              null comment '开始时间',
    end_time      varchar(30)                              null comment '结束时间'
);

create table nmpl_business_route
(
    id              bigint auto_increment comment '主键'
        primary key,
    route_id        varchar(128)                             not null comment '路由Id',
    business_type   char(2)     default '11'                 null comment '业务类型 11:根密钥中心 21:指控中心 41:计费中心',
    network_id      varchar(50)                              not null comment '设备入网码',
    ip              varchar(32)                              null comment 'ip_v4',
    create_user     varchar(64)                              null comment '创建者',
    create_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user     varchar(64)                              null comment '更新者',
    update_time     datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    is_exist        tinyint(1)  default 1                    null comment '1:存在 0:删除',
    ip_v6           varchar(64)                              null comment 'ip_v6',
    byte_network_id blob                                     null comment '设备入网码'
)
    comment '业务服务路由';

create table nmpl_company_heartbeat
(
    id                bigint auto_increment comment '主键'
        primary key,
    source_network_id varchar(128)                             not null comment '来源Id',
    target_network_id varchar(128)                             not null comment '目标Id',
    status            char(2)     default '01'                 null comment '连接状态 01:通  02:不通',
    up_value          varchar(128)                             not null comment '上行流量',
    down_value        varchar(128)                             not null comment '下行流量',
    upload_time       datetime(2)                              null comment '上报时间',
    create_time       datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time       datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
)
    comment '小区业务心跳';

create table nmpl_company_info
(
    company_id   bigint auto_increment comment '单位id'
        primary key,
    company_name varchar(50)                              null comment '单位名称',
    unit_name    varchar(50)                              null comment '单位名称',
    country_code varchar(50)                              null comment '国家码',
    company_code varchar(50)                              null comment '单位编码',
    company_type char(2)                                  null comment '00:运营商 01:大区 02：小区',
    telephone    varchar(20)                              null comment '联系电话',
    email        varchar(30)                              null comment '联系邮箱',
    status       tinyint(1)  default 1                    null comment '1:正常 0停用',
    addr         varchar(50)                              null comment '联系地址',
    parent_code  varchar(200)                             null comment '父单位编码',
    create_user  varchar(50)                              null comment '创建人',
    create_time  datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user  varchar(50)                              null comment '更新人',
    update_time  datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    is_exist     tinyint(1)  default 1                    null comment '1:存在 0:删除',
    position     varchar(30)                              null comment '经纬度位置'
);

create table nmpl_config
(
    id            bigint auto_increment comment '主键'
        primary key,
    device_type   char(2)                                  null comment '设备类型 01:接入基站 02:边界基站 11:密钥中心 20:数据采集',
    config_name   varchar(100)                             null comment '配置项名称',
    config_code   varchar(32)                              null comment '配置编码',
    config_value  varchar(2000)                            null comment '配置值',
    default_value varchar(2000)                            null comment '默认值',
    unit          varchar(32)                              null comment '单位',
    status        tinyint     default 1                    null comment '状态 1同步 0 未同步',
    create_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    create_user   varchar(20)                              null comment '创建人',
    update_time   datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    update_user   varchar(20)                              null comment '修改人',
    is_exist      tinyint(1)  default 1                    null comment '状态 true:存在(1)  false:删除(0)',
    remark        varchar(100)                             null comment '备注'
)
    comment '系统配置表';

create table nmpl_configuration
(
    id          bigint auto_increment comment '主键'
        primary key,
    device_id   varchar(128) null comment '设备编号',
    real_ip     varchar(16)  null comment '内网Ip',
    real_port   varchar(8)   null comment '内网端口',
    url         varchar(128) null comment '路径',
    type        char(2)      null comment '路径类型 1：配置同步 2：信令启停 3：推送版本文件 4：启动版本文件',
    device_type char(2)      null comment '设备类型 1:基站 2 分发机 3 生成机 4 缓存机'
);

create table nmpl_data_collect
(
    id              bigint auto_increment comment '主键'
        primary key,
    device_id       varchar(32)                              null comment '设备id',
    device_name     varchar(30)                              null comment '设备名字',
    device_ip       varchar(16)                              not null comment '设备ip',
    device_type     varchar(30)                              not null comment '设备类别(01接入基站、02边界基站、11密钥中心、12生成机、13缓存机)',
    data_item_name  varchar(50)                              null comment '统计项名',
    data_item_code  varchar(32) charset utf8                 null comment '收集项编号',
    data_item_value varchar(32) default '1'                  null comment '值',
    unit            varchar(32)                              null comment '单位',
    upload_time     datetime(2)                              not null comment '创建时间',
    create_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time     datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
);

create table nmpl_data_collect_for_load
(
    id              bigint auto_increment comment '主键'
        primary key,
    device_id       varchar(32)                              null comment '设备id',
    device_name     varchar(30)                              null comment '设备名字',
    device_type     varchar(30) default '00'                 null comment '设备类别(00基站、11密钥中心、12生成机、13缓存机)',
    data_item_name  varchar(50)                              null comment '统计项名',
    data_item_code  varchar(32) charset utf8                 null comment '收集项编号',
    data_item_value varchar(32) default '1'                  null comment '值',
    unit            varchar(32)                              null comment '单位',
    upload_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    create_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time     datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
);

create table nmpl_data_push_record
(
    id          bigint auto_increment comment '主键'
        primary key,
    table_name  varchar(50)                              not null comment '表名',
    data_id     bigint                                   null comment '数据表id',
    create_time datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
);

create table nmpl_device_extra_info
(
    id                   bigint auto_increment comment '主键'
        primary key,
    device_id            varchar(128)                             null comment '备用设备id',
    rel_device_id        varchar(128)                             null comment '关联主设备id',
    device_name          varchar(16)                              null comment '设备名称',
    device_type          char(2)     default '00'                 null comment '设备类型 00:基站 11:密钥中心 12:生成机 13:缓存机',
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
    is_exist             tinyint(1)  default 1                    null comment '1:存在 0:删除'
)
    comment '备用设备';

create table nmpl_device_info
(
    id                     bigint auto_increment comment '主键'
        primary key,
    device_id              varchar(128)                             not null comment '设备编号',
    device_name            varchar(16)                              null comment '设备名称',
    device_type            char(2)     default '11'                 null comment '设备类型 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心',
    other_type             char(2)                                  null comment '备用类型 ',
    enter_network_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '接入网时间',
    device_admain          varchar(20)                              null comment '设备管理员',
    remark                 varchar(256)                             null comment '设备备注',
    public_network_ip      varchar(16)                              null comment '公网Ip',
    public_network_port    varchar(8)                               null comment '公网端口',
    lan_ip                 varchar(16)                              null comment '内网Ip',
    lan_port               varchar(8)                               null comment '内网端口',
    station_status         char(2)     default '01'                 null comment '设备状态 01:静态  02:激活  03:禁用 04:下线',
    station_network_id     varchar(32)                              null comment '设备入网码',
    station_random_seed    varchar(64)                              null comment '加密随机数',
    relation_operator_id   varchar(50)                              null comment '关联区域',
    create_user            varchar(20)                              null comment '创建人',
    create_time            datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user            varchar(20)                              null comment '修改人',
    update_time            datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist               tinyint(1)  default 1                    null comment '1:存在 0:删除',
    byte_network_id        blob                                     null comment '设备入网码',
    run_version_id         bigint                                   null comment '运行版本文件id',
    run_version_no         varchar(16)                              null comment '运行版本号',
    run_file_name          varchar(64)                              null comment '运行版本文件名称',
    run_version_status     varchar(2)  default '1'                  null comment '运行状态 1:未运行 2:运行中 3:已停止',
    run_version_oper_time  timestamp(2)                             null comment '运行版本操作时间',
    load_version_no        varchar(16)                              null comment '加载版本号',
    load_version_id        bigint                                   null comment '加载版本文件id',
    load_version_oper_time timestamp(2)                             null comment '加载版本操作时间',
    load_file_name         varchar(64)                              null comment '加载版本文件名称',
    current_connect_count  varchar(10)                              null comment '当前用户数'
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

create table nmpl_file_device_rel
(
    id          bigint auto_increment comment '主键'
        primary key,
    file_id     bigint                                   not null comment '文件主键',
    device_id   varchar(128)                             not null comment '设备id',
    is_delete   tinyint(1)  default 1                    null comment '1存在 0删除',
    create_time datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    create_user varchar(50)                              null,
    update_user varchar(50)                              null
);

create table nmpl_internet_route
(
    id                  bigint auto_increment comment '主键'
        primary key,
    route_id            varchar(128)                             not null comment '路由Id',
    network_id          varchar(50)                              not null comment '设备入网码',
    boundary_station_ip varchar(32)                              null comment '边界基站ip_v4',
    create_user         varchar(64)                              null comment '创建者',
    create_time         datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user         varchar(64)                              null comment '更新者',
    update_time         datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    is_exist            tinyint(1)  default 1                    null comment '1:存在 0:删除',
    ip_v6               varchar(64)                              null comment '边界基站ip_v6',
    byte_network_id     blob                                     null comment '设备入网码',
    next_network_id     varchar(50)                              not null comment '下一条入网码',
    hop_count           varchar(3)                               not null comment '跳数',
    company_name        varchar(50)                              null comment '小区名称',
    company_id          varchar(50)                              null comment '小区id',
    device_type         varchar(2)                               null comment '设备类型',
    device_name         varchar(50)                              null comment '设备名称',
    device_id           varchar(128)                             null comment '设备ID'
)
    comment '出网路由';

create table nmpl_key_poll_detail
(
    id                bigint auto_increment comment '主键'
        primary key,
    device_id         varchar(32)                              null comment '设备id',
    device_network_id varchar(32)                              null comment '设备入网编号',
    key_pool_name     varchar(32) default '1'                  null comment '秘钥池名字',
    call_time         datetime(2) default CURRENT_TIMESTAMP(2) null comment '上报时间',
    key_down_number   bigint                                   null comment '总下载量',
    key_remain_number bigint                                   null comment '总剩余量',
    key_consum_number bigint                                   null comment '总消耗量',
    create_time       datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time       datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
);

create table nmpl_link
(
    id                bigint auto_increment comment '主键'
        primary key,
    link_name         varchar(32)                              null comment '链路名称',
    link_type         smallint                                 null comment '链路类型: 1:单向,2:双向',
    main_device_id    varchar(20)                              null comment '本端设备id',
    main_device_type  char(2)                                  null comment '设备类型 01:小区内基站 02:小区边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心',
    follow_device_id  varchar(20)                              null comment '对端设备id',
    follow_network_id varchar(32)                              null comment '对端设备入网ID',
    follow_ip         varchar(32)                              null comment '对端设备IP',
    follow_port       varchar(64)                              null comment '对端设备端口',
    active_auth       tinyint(1)                               null comment '主动发起认证 1:开启 0:关闭',
    is_on             tinyint(1)  default 1                    null comment '是否启用 1:启动 0:禁止',
    create_user       varchar(100)                             null comment '创建人',
    create_time       datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user       varchar(100)                             null comment '修改人',
    update_time       datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist          tinyint(1)  default 1                    null comment '1:存在 0:删除'
)
    comment '链路信息表';

create table nmpl_login_detail
(
    id            bigint auto_increment comment '主键'
        primary key,
    login_account varchar(32)                              null comment '用户名字',
    nick_name     varchar(30)                              null comment '昵称',
    login_ip      varchar(16)                              null comment '登录ip',
    login_addr    varchar(64)                              null comment '登录地址',
    login_type    tinyint                                  null comment '登录方式  1:密码登录 2：手机验证码',
    is_success    tinyint(1)                               null comment '1:成功 2:失败',
    fail_cause    varchar(64)                              null comment '失败原因',
    create_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time   datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
);

create table nmpl_menu
(
    menu_id        bigint auto_increment comment '菜单ID'
        primary key,
    menu_name      varchar(50)                              not null comment '菜单名称',
    parent_menu_id bigint      default -1                   null comment '父菜单ID',
    url            varchar(100)                             null comment '请求地址',
    is_frame       tinyint     default 0                    null comment '是否为外链（1是 0否）',
    menu_type      tinyint                                  null comment '菜单类型（1目录 2菜单 3按钮）',
    menu_status    tinyint     default 1                    null comment '菜单状态（1正常 0停用）',
    perms_code     varchar(100)                             null,
    create_user    varchar(64)                              null comment '创建者',
    create_time    datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user    varchar(64)                              null comment '更新者',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    remark         varchar(200)                             null comment '备注',
    is_exist       tinyint     default 1                    null comment '1正常 0删除',
    icon           varchar(30) default ''                   null comment '前端按钮',
    permission     char(2)     default '0'                  null comment '权限标识',
    component      varchar(30) default ''                   null comment '前端组件信息'
)
    comment '菜单权限表';

create table nmpl_notice_detail
(
    id             bigint auto_increment comment '主键'
        primary key,
    phone          char(11)                                 null comment '手机号',
    notice_type    char(2)                                  null comment '01:短信 02语音 03邮件',
    notice_content varchar(1500)                            null comment '通知内容',
    remak          varchar(8)                               null comment '备注',
    create_time    datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null comment '更新时间'
);

create index idx_phone
    on nmpl_notice_detail (phone);

create table nmpl_operate_log
(
    id              bigint unsigned auto_increment comment '主键id'
        primary key,
    channel_type    tinyint                                  null comment '1:网管pc 2：基站',
    oper_modul      varchar(64)                              null comment '操作模块',
    oper_url        varchar(64)                              null comment '请求的url',
    oper_type       varchar(32)                              null comment '操作类型(新增 修改 编辑等)',
    oper_desc       varchar(128)                             null comment '操作描述',
    oper_requ_param varchar(1000)                            null comment '请求参数',
    oper_resp_param varchar(1000)                            null comment '服务名字',
    oper_method     varchar(128)                             null comment '操作方法',
    oper_user_id    varchar(64)                              null comment '操作人id',
    oper_user_name  varchar(32)                              null comment '操作人姓名',
    is_success      tinyint(1)                               null comment '1 成功  2 失败',
    source_ip       varchar(15)                              null comment '操作ip',
    oper_level      varchar(15)                              null comment '操作级别(1:高危 2 危险 3 警告 4 常规)',
    oper_time       datetime(2) default CURRENT_TIMESTAMP(2) null comment '操作时间',
    remark          varchar(256)                             null comment '备注',
    create_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '更新时间'
);

create table nmpl_outline_pc_info
(
    id                 bigint auto_increment comment '主键'
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
    comment '离线一体机';

create table nmpl_outline_sorter_info
(
    id                 bigint auto_increment comment '主键'
        primary key,
    device_id          varchar(128)                             not null comment '设备编号',
    device_name        varchar(16)                              null comment '设备名称',
    station_network_id varchar(32)                              null comment '设备入网码',
    remark             varchar(256)                             null comment '设备备注',
    create_user        varchar(20)                              null comment '创建人',
    create_time        datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user        varchar(20)                              null comment '修改人',
    update_time        datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist           tinyint(1)  default 1                    null comment '1:存在 0:删除'
)
    comment '离线分发机';

create table nmpl_physical_device_heartbeat
(
    ip1_ip2     varchar(34)                              not null comment '主键(标记两台设备的关联关系，小的ip在前)'
        primary key,
    status      varchar(2)  default '1'                  null comment '1：不通 2：通',
    upload_time datetime(2)                              not null comment '上报时间',
    create_time datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
)
    comment '物理设备心跳表';

create table nmpl_physical_device_resource
(
    device_ip        varchar(16)                              not null comment '物理设备ip',
    resource_type    varchar(2)                               not null comment '资源类型 1: cpu 2 内存 3 磁盘 4流量 5 其他',
    resource_value   varchar(64)                              not null comment '资源value',
    resource_unit    varchar(10)                              null comment '资源单位',
    resource_percent varchar(8)                               null comment '资源占比',
    upload_time      datetime(2)                              not null comment '上报时间',
    create_time      datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time      datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    primary key (device_ip, resource_type)
)
    comment '物理设备资源情况信息表';

create table nmpl_report_business
(
    id             bigint auto_increment comment '主键'
        primary key,
    business_name  varchar(100)                             null comment '业务名称',
    business_code  varchar(32)                              null comment '业务编码',
    business_value varchar(128)                             null comment '业务配置值',
    default_value  varchar(128)                             null comment '默认值',
    create_time    datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    create_user    varchar(20)                              null comment '创建人',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    update_user    varchar(20)                              null comment '修改人',
    is_exist       tinyint(1)  default 1                    null comment '状态 true:存在(1)  false:删除(0)'
)
    comment '数据采集业务上报配置表';

create table nmpl_role
(
    role_id     bigint auto_increment comment '角色ID'
        primary key,
    role_name   varchar(30)                              not null comment '角色名称',
    role_code   varchar(100)                             not null comment '角色编码',
    menu_scope  char        default '2'                  null comment '1:全部菜单权限 2：自定义',
    status      tinyint     default 1                    null comment '角色状态（1正常 0停用）',
    create_user varchar(64)                              null comment '创建者',
    create_time datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user varchar(64)                              null comment '更新者',
    update_time datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    remark      varchar(200)                             null comment '备注',
    is_exist    tinyint     default 1                    null comment '1正常 0删除'
)
    comment '角色信息表';

create table nmpl_role_menu_relation
(
    role_id bigint not null comment '角色id',
    menu_id bigint not null comment '菜单权限id'
);

create table nmpl_route
(
    id                 bigint auto_increment comment '主键'
        primary key,
    access_device_id   varchar(128)                             not null comment '接入基站id',
    boundary_device_id varchar(128)                             null comment '边界基站id',
    create_user        varchar(20)                              null comment '创建人',
    create_time        datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user        varchar(20)                              null comment '修改人',
    update_time        datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '修改时间',
    is_exist           tinyint(1)  default 1                    null comment '1:存在 0:删除'
)
    comment '密钥生成机和密钥缓存机器';

create table nmpl_signal
(
    id              bigint auto_increment comment '主键'
        primary key,
    device_id       varchar(50)                              null comment '设备编号',
    signal_name     varchar(100)                             null comment '信令名称',
    send_ip         varchar(15)                              null comment '发送方ip',
    receive_ip      varchar(50)                              null comment '接收方ip',
    signal_content  varchar(500)                             null comment '信令内容',
    business_module varchar(100)                             null comment '业务模块',
    upload_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '上报时间',
    create_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time     datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    is_exist        tinyint(1)  default 1                    null comment '状态true:存在(1)  false:删除(0)'
);

create table nmpl_signal_io
(
    id          bigint auto_increment comment '主键'
        primary key,
    device_id   varchar(50)                              null comment '设备编号',
    status      char(2)                                  null comment '状态0：停止追踪 1：开启追踪',
    create_time datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user varchar(64)                              null comment '更新者',
    update_time datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    is_exist    tinyint(1)  default 1                    null comment '状态true:存在(1)  false:删除(0)'
)
    comment '信令追踪状态表';

create index nmpl_signal_io_device_id_is_exist_status_index
    on nmpl_signal_io (device_id, is_exist, status);

create table nmpl_sms_detail
(
    id                    bigint auto_increment comment '主键'
        primary key,
    phone                 char(11)                                 null comment '手机号',
    sms_verification_code varchar(8)                               null comment '短信验证码',
    create_time           datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time           datetime(2) default CURRENT_TIMESTAMP(2) null comment '更新时间'
);

create index idx_phone
    on nmpl_sms_detail (phone);

create table nmpl_static_route
(
    id              bigint auto_increment comment '主键'
        primary key,
    route_id        varchar(128)                             not null comment '路由Id',
    network_id      varchar(50)                              not null comment '设备入网码',
    server_ip       varchar(32)                              null comment '服务器ip_v6',
    is_exist        tinyint(1)  default 1                    not null comment '删除标志（1代表存在 0代表删除）',
    create_user     varchar(64)                              null comment '创建者',
    create_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user     varchar(64)                              null comment '更新者',
    update_time     datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    station_id      varchar(128)                             not null comment '基站id',
    ip_v6           varchar(64)                              null comment '服务器ip_v6',
    byte_network_id blob                                     null comment '设备入网码',
    company_name    varchar(50)                              null comment '小区名称',
    company_id      varchar(50)                              null comment '小区id',
    station_name    varchar(16)                              null comment '接入基站名称',
    server_name     varchar(50)                              null comment '服务名称'
)
    comment '静态路由';

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

create table nmpl_system_heartbeat
(
    source_id   varchar(128)                             not null comment '来源Id',
    target_id   varchar(128)                             not null comment '目标Id',
    status      char(2)     default '01'                 null comment '连接状态 01:通  02:不通',
    upload_time datetime(2)                              null comment '上报时间',
    create_time datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
)
    comment '业务心跳';

create table nmpl_system_resource
(
    id             int auto_increment comment '自增主键'
        primary key,
    system_id      varchar(64)                              not null comment '系统id:关联基站和设备表',
    system_type    varchar(50)                              null comment '系统类别',
    start_time     datetime(2)                              null comment '启动时间',
    run_time       bigint      default 0                    null comment '运行时长，以秒为单位',
    cpu_percent    varchar(8)  default '0'                  null comment 'CPU占比，百分比形式',
    memory_percent varchar(8)  default '0'                  null comment '内存占比，百分比形式',
    upload_time    datetime(2)                              not null comment '上报时间',
    create_time    datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
)
    comment '运行系统资源信息表';

create index nsr_systemId_uploadTime
    on nmpl_system_resource (system_id, upload_time);

create table nmpl_terminal_data
(
    id                  bigint auto_increment comment '自增主键'
        primary key,
    terminal_network_id varchar(128)                             not null comment '一体机设备id',
    parent_id           varchar(128)                             not null comment '基站设备id',
    data_type           char(2)                                  not null comment '数据类型 01:剩余 02:补充 03:使用',
    up_value            bigint                                   not null comment '上行密钥量',
    down_value          bigint                                   not null comment '下行密钥量',
    terminal_ip         varchar(64)                              not null comment '一体机ip',
    upload_time         datetime(2)                              null comment '上报时间',
    create_time         datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time         datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间'
)
    comment '基站下一体机信息上报表';

create table nmpl_terminal_user
(
    terminal_network_id varchar(128)                             not null comment '终端设备Id',
    parent_device_id    varchar(128)                             not null comment '所属设备Id',
    terminal_status     char(2)     default '01'                 null comment '用户状态 01:密钥匹配  02:注册  03:上线 04:下线 05:注销',
    upload_time         datetime(2)                              null comment '上报时间',
    create_time         datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time         datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    user_type           char(2)     default '21'                 null comment '用户类型 21:一体机  22:安全服务器',
    id                  bigint auto_increment comment '主键id'
        primary key
)
    comment '终端用户表';

create table nmpl_user
(
    user_id       bigint auto_increment comment '用户ID'
        primary key,
    village_id    bigint                                   null comment '小区ID',
    login_account varchar(128)                             not null comment '登录账号',
    nick_name     varchar(30)                              null comment '用户昵称',
    user_type     char(2)     default '01'                 null comment '用户类型（00系统用户 01注册用户）',
    email         varchar(128)                             null comment '用户邮箱',
    phone_number  varchar(128)                             not null comment '手机号码',
    password      varchar(128)                             not null comment '密码',
    role_id       varchar(300)                             not null comment '角色id',
    status        tinyint(1)  default 1                    null comment '帐号状态（1正常 0停用）',
    is_exist      tinyint(1)  default 1                    null comment '删除标志（1代表存在 0代表删除）',
    create_user   varchar(64)                              null comment '创建者',
    create_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user   varchar(64)                              null comment '更新者',
    update_time   datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    remark        varchar(500)                             null comment '备注'
)
    comment '用户信息表';

create table nmpl_version
(
    id           bigint auto_increment comment '主键'
        primary key,
    system_type  varchar(8)                               not null comment 'QIBS:基站 QEBS:边界基站 QKC:密钥中心 QNMP:网管代理',
    version_no   varchar(16)                              not null comment '版本号',
    file_path    varchar(128)                             null,
    file_name    varchar(64)                              null,
    file_size    varchar(32)                              not null comment '文件大小（mb）',
    version_desc varchar(1024)                            null,
    is_delete    tinyint(1)  default 1                    null comment '1存在 0删除',
    upload_time  datetime(2) default CURRENT_TIMESTAMP(2) null comment '上传时间',
    create_time  datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_time  datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    create_user  varchar(50)                              null,
    update_user  varchar(50)                              null
)
    comment '版本文件表';

create table sys_sequence
(
    seq_name        varchar(50) collate latin1_bin not null
        primary key,
    min_value       int                            not null,
    max_value       bigint                         not null,
    current_value   int                            not null,
    increment_value int                            not null
);

create
definer = root@`%` function _nextval(name varchar(50)) returns bigint
begin
declare _cur bigint;
declare _maxvalue bigint;  -- 接收最大值
declare _increment int; -- 接收增长步数
set _increment = (select increment_value from sys_sequence where seq_name = name);
set _maxvalue = (select max_value from sys_sequence where seq_name = name);
set _cur = (select current_value from sys_sequence where seq_name = name);
update sys_sequence                      -- 更新当前值
set current_value = _cur + increment_value
where seq_name = name ;
if(_cur + _increment >= _maxvalue) then  -- 判断是都达到最大值
update sys_sequence
set current_value = min_value
where seq_name = name ;
end if;
return _cur;

end;

INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, create_time, create_user, update_time, update_user, is_exist, remark) VALUES ('01', '加强密度配置（128bit-128k）', 'as0001', '1：1', '1：1', null, 0, '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, create_time, create_user, update_time, update_user, is_exist, remark) VALUES ('01', '加强密度配置（>128k ）', 'as0002', '1：32', '1：32', null, 0, '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, create_time, create_user, update_time, update_user, is_exist, remark) VALUES ('01', '话单上报', 'as0003', '60', '60', 's', 0, '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, create_time, create_user, update_time, update_user, is_exist, remark) VALUES ('01', '基站状态上报', 'as0004', '1', '1', 's', 0, '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, create_time, create_user, update_time, update_user, is_exist, remark) VALUES ('01', '通信状态上报', 'as0005', '60', '60', 's', 0, '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, create_time, create_user, update_time, update_user, is_exist, remark) VALUES ('11', '生成密钥量', 'kc0001', '10', '10', 'T', 0, '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, create_time, create_user, update_time, update_user, is_exist, remark) VALUES ('11', '分发密钥量', 'kc0002', '1', '1', 'T', 0, '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, create_time, create_user, update_time, update_user, is_exist, remark) VALUES ('20', '采集周期', 'dc0001', '10', '1800', 's', 0, '2023-09-19 09:53:21', null, '2023-10-11 16:20:57.64', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, create_time, create_user, update_time, update_user, is_exist, remark) VALUES ('20', '上报周期', 'dc0002', '10', '1800', 's', 0, '2023-09-19 09:53:21', null, '2023-10-11 16:21:02.64', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, create_time, create_user, update_time, update_user, is_exist, remark) VALUES ('20', '数据采集开关', 'dataSwitch', '0', '0', null, 0, '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1, null);


INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('用户权限', -1, '/userCenter', 0, 0, 1, null, null, '2023-06-02 09:46:53.09', null, '2023-09-21 17:16:58.66', null, 1, 'user', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('角色管理', 1, '/role', 0, 1, 1, 'sys:role:query', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', 'user/role');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建角色', 2, null, 0, 2, 1, 'sys:role:save', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除角色', 2, null, 0, 2, 1, 'sys:role:delete', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑角色', 2, null, 0, 2, 1, 'sys:role:update', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('用户管理', 1, '/user', 0, 1, 1, 'sys:user:query', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', 'user');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建用户', 6, null, 0, 2, 1, 'sys:user:save', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除用户', 6, null, 0, 2, 1, 'sys:user:delete', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑用户', 6, null, 0, 2, 1, 'sys:user:update', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('运营商管理', -1, '/guideindex', 0, 0, 1, '', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, 'guide', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('运营商', 10, '/guide', 0, 1, 1, 'sys:operator:query', null, '2023-06-02 09:46:53.09', null, '2023-09-21 17:19:04.23', null, 1, '', '0', 'guide');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新增运营商', 11, null, 0, 2, 1, 'sys:operator:save', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('修改运营商', 11, null, 0, 2, 1, 'sys:operator:update', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除运营商', 11, null, 0, 2, 1, 'sys:operator:delete', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('区域管理', -1, '/area', 0, 0, 1, null, null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, 'add-location', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('大区', 17, '/area/max', 0, 1, 1, 'sys:region:query', null, '2023-06-02 09:46:53.09', null, '2023-09-21 17:14:02.76', null, 1, '', '0', 'area/max');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('小区', 17, '/area/min', 0, 1, 1, 'sys:village:query', null, '2023-06-02 09:46:53.09', null, '2023-09-21 17:14:04.80', null, 1, '', '0', 'area/min');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建大区', 18, null, 0, 2, 1, 'sys:region:save', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除大区', 18, null, 0, 2, 1, 'sys:region:delete', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑大区', 18, null, 0, 2, 1, 'sys:region:update', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建小区', 19, null, 0, 2, 1, 'sys:village:save', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除小区', 19, null, 0, 2, 1, 'sys:village:delete', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑小区', 19, null, 0, 2, 1, 'sys:village:update', null, '2023-06-02 09:46:53.09', null, '2023-06-02 09:46:53.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('节点管理', -1, '/node', 0, 0, 1, null, null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, 'more-filled', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('接入基站', 28, '/node/instation', 0, 1, 1, 'sys:station:query', null, '2023-06-02 09:46:53.10', null, '2023-09-21 17:17:38.80', null, 1, '', '0', 'node/instation');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建基站', 29, null, 0, 2, 1, 'sys:station:save', null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除基站', 29, null, 0, 2, 1, 'sys:station:delete', null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑基站', 29, null, 0, 2, 1, 'sys:station:update', null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建密钥中心', 92, null, 0, 2, 1, 'sys:dispenser:save', null, '2023-06-02 09:46:53.10', null, '2023-09-01 10:19:05.91', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除密钥中心', 92, null, 0, 2, 1, 'sys:dispenser:delete', null, '2023-06-02 09:46:53.10', null, '2023-09-01 10:19:05.91', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑密钥中心', 92, null, 0, 2, 1, 'sys:dispenser:update', null, '2023-06-02 09:46:53.10', null, '2023-09-01 10:19:05.91', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建生成机', 93, null, 0, 2, 1, 'sys:generator:save', null, '2023-06-02 09:46:53.10', null, '2023-09-01 10:19:05.81', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除生成机', 93, null, 0, 2, 1, 'sys:generator:delete', null, '2023-06-02 09:46:53.10', null, '2023-09-01 10:19:05.81', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑生成机', 93, null, 0, 2, 1, 'sys:generator:update', null, '2023-06-02 09:46:53.10', null, '2023-09-01 10:19:05.81', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新增缓存机', 94, null, 0, 2, 1, 'sys:cache:save', null, '2023-06-02 09:46:53.10', null, '2023-09-01 10:19:05.72', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除缓存机', 94, null, 0, 2, 1, 'sys:cache:delete', null, '2023-06-02 09:46:53.10', null, '2023-09-01 10:19:05.72', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑缓存机', 94, null, 0, 2, 1, 'sys:cache:update', null, '2023-06-02 09:46:53.10', null, '2023-09-01 10:19:05.72', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('链路管理', -1, '/linkindex', 0, 0, 1, null, null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, 'operation', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('链路配置', 49, '/link', 0, 1, 1, 'sys:link:query', null, '2023-06-02 09:46:53.10', null, '2023-09-21 17:18:48.05', null, 1, '', '0', 'link');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建链路', 50, null, 0, 2, 1, 'sys:link:save', null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除链路', 50, null, 0, 2, 1, 'sys:link:delete', null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑链路', 50, null, 0, 2, 1, 'sys:link:update', null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('路由管理', -1, '/routeindex', 0, 0, 1, null, null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, 'help', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('系统设置', -1, '/setting', 0, 0, 1, null, null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, 'setting', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('权限菜单', 61, '/setting/auth', 0, 1, 1, 'sys:power:query', null, '2023-06-02 09:46:53.10', null, '2023-06-02 09:46:53.10', null, 1, '', '0', 'setting/auth');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('恢复默认参数', 62, null, 0, 2, 1, 'sys:parm:reset', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除参数', 62, null, 0, 2, 1, 'sys:parm:delete', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑参数', 62, null, 0, 2, 1, 'sys:parm:update', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('同步参数', 62, null, 0, 2, 1, 'sys:parm:synchro', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('状态监控', -1, '/monitor', 0, 0, 1, null, null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:47:27.26', null, 1, 'cpu', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('状态监控', 70, '/status', 0, 1, 1, 'sys:monitor:status', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:47:27.26', null, 1, '', '0', 'monitor/status');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('信令管理', -1, '/signalindex', 0, 0, 1, null, null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, 'data-analysis', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('信令上报', 73, '/signal', 0, 1, 1, 'sys:sign:query,sys:sign:selectDeviceIds,sys:sign:deviceIds', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '1', 'signal');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('清空', 74, null, 0, 2, 1, 'sys:sign:clear', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('导出文件', 74, null, 0, 2, 1, 'sys:sign:export', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('日志管理', -1, '/log', 0, 0, 1, null, null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, 'notification', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('登录日志', 79, '/log/login', 0, 1, 1, 'sys:loginLog:query', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '0', 'log/login');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('操作日志', 79, '/log/operate', 0, 1, 1, 'sys:operateLog:query', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '0', 'log/operate');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('系统日志', 79, '/log/system', 0, 1, 1, 'sys:sysLog:query', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '0', 'log/system');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('统计管理', -1, '/statistics', 0, 0, 1, null, null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, 'monitor', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('基站统计数据', 86, '/statistics/station', 0, 1, 1, 'sys:stationData:query', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '0', 'statistics/station');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('密钥中心统计数据', 86, '/statistics/dispenser', 0, 1, 1, 'sys:dispenserData:query', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '0', 'statistics/dispenser');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('生成机统计数据', 86, '/statistics/generate', 0, 1, 1, 'sys:generatorData:query', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '0', 'statistics/generate');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('缓存机统计数据', 86, '/statistics/cache', 0, 1, 1, 'sys:cacheData:query', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '0', 'statistics/cache');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('边界基站', 28, '/node/boundarystation', 0, 1, 1, 'sys:boundarystation:query', null, '2023-08-31 10:30:13.35', null, '2023-09-21 17:17:39.72', null, 1, '', '0', 'node/boundarystation');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('密钥中心', 28, '/node/dispenser', 0, 1, 1, 'sys:dispenser:query', null, '2023-06-02 09:46:53.10', null, '2023-09-21 17:17:40.90', null, 1, '', '0', 'node/dispenser');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('密钥生成', 28, '/node/generate', 0, 1, 1, 'sys:generator:query', null, '2023-06-02 09:46:53.10', null, '2023-09-21 17:17:46.91', null, 1, '', '0', 'node/generate');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('缓存机', 28, '/node/cache', 0, 1, 1, 'sys:cache:query', null, '2023-06-02 09:46:53.10', null, '2023-09-21 17:17:50.33', null, 1, '', '0', 'node/cache');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('话单管理', -1, '/ticketindex', 0, 0, 1, null, null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, 'iphone', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('话单上报', 95, '/ticket', 0, 1, 1, 'sys:bill:query', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '1', 'ticket');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('版本管理', -1, '/versionindex', 0, 0, 1, null, null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, 'warning', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('版本维护', 98, '/versionmanage', 0, 1, 1, 'sys:version:fileList,sys:version:list', '', '2023-01-10 10:24:40.26', '', '2023-03-15 09:42:25.35', '', 1, '', '1', 'version/versionmanage');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建版本', 99, null, 0, 2, 1, 'sys:version:save', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除版本文件', 99, null, 0, 2, 1, 'sys:version:deleteFile', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('修改用户密码(更多)', 6, null, 0, 2, 1, 'sys:user:changePasswd', null, '2023-06-02 09:46:53.11', null, '2023-06-02 09:46:53.11', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('信令启停', 74, null, 0, 2, 1, 'sys:sign:track', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('离线分发机认证放号', -1, '/outlinedispenserindex', 0, 0, 1, null, null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, 'collection', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('离线分发机认证放号', 119, '/outlinedispenser', 0, 1, 1, 'sys:sorter:query', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', 'outlinedispenser');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建设备', 120, null, 0, 2, 1, 'sys:sorter:insert', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑', 120, null, 0, 2, 1, 'sys:sorter:modify', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除', 120, null, 0, 2, 1, 'sys:sorter:delete', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('导入文件', 120, null, 0, 2, 1, 'sys:sorter:upload', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('终端设备认证放号', -1, '/allinoneindex', 0, 0, 1, null, null, '2023-06-02 09:46:53.12', null, '2023-07-20 10:01:53.46', null, 1, 'credit-card', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('终端设备认证放号', 126, '/allinone', 0, 1, 1, 'sys:pc:query', null, '2023-06-02 09:46:53.12', null, '2023-07-20 10:01:53.46', null, 1, '', '0', 'allinone');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建设备', 127, null, 0, 2, 1, 'sys:pc:insert', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除', 127, null, 0, 2, 1, 'sys:pc:delete', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑', 127, null, 0, 2, 1, 'sys:pc:modify', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('导入文件', 127, null, 0, 2, 1, 'sys:pc:upload', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('主备', 28, '/node/mainbackup', 0, 1, 1, 'sys:deviceExtra:query', null, '2023-06-02 09:46:53.12', null, '2023-09-21 17:17:53.59', null, 1, '', '0', 'node/mainbackup');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建主备', 133, null, 0, 2, 1, 'sys:deviceExtra:save', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑主备', 133, null, 0, 2, 1, 'sys:deviceExtra:update', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除主备', 133, null, 0, 2, 1, 'sys:deviceExtra:delete', null, '2023-06-02 09:46:53.12', null, '2023-06-02 09:46:53.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('ID分配', 55, '/routestatic', 0, 1, 1, 'sys:staticRoute:select', null, '2022-09-22 14:15:12.01', null, '2023-09-21 17:15:53.52', null, 1, '', '0', 'route/static');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('路由配置', 55, '/routeweb', 0, 1, 1, 'sys:internetRoute:select', null, '2022-09-22 14:16:10.70', null, '2023-09-27 09:56:50.54', null, 1, '', '0', 'route/web');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('业务服务', 55, '/routeservice', 0, 1, 1, 'sys:businessRoute:select', null, '2022-09-22 14:15:53.80', null, '2023-09-27 09:56:46.13', null, 1, '', '0', 'route/service');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建路由', 137, null, 0, 2, 1, 'sys:staticRoute:insert', null, '2022-09-22 14:17:12.15', null, '2023-06-02 09:46:55.62', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除', 137, null, 0, 2, 1, 'sys:staticRoute:delete', null, '2022-09-22 14:17:37.23', null, '2023-06-02 09:46:55.62', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑', 137, null, 0, 2, 1, 'sys:staticRoute:update', null, '2022-09-22 14:17:46.96', null, '2023-06-02 09:46:55.62', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建', 139, null, 0, 2, 1, 'sys:businessRoute:insert', null, '2022-09-22 14:18:49.52', null, '2023-10-09 10:05:23.65', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑', 139, null, 0, 2, 1, 'sys:businessRoute:update', null, '2022-09-22 14:18:59.12', null, '2023-09-27 09:56:57.75', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建路由', 138, null, 0, 2, 1, 'sys:internetRoute:insert', null, '2022-09-22 14:19:44.65', null, '2023-09-27 09:57:00.66', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑', 138, null, 0, 2, 1, 'sys:internetRoute:update', null, '2022-09-22 14:20:13.46', null, '2023-09-27 09:57:03.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除', 138, null, 0, 2, 1, 'sys:internetRoute:delete', null, '2022-09-22 14:20:17.36', null, '2023-09-27 09:57:06.09', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除', 139, null, 0, 2, 1, 'sys:businessRoute:delete', null, '2022-09-22 14:18:49.52', null, '2023-10-09 10:05:04.63', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('更新版本文件', 99, '', 0, 2, 1, 'sys:version:updateFile', '', '2023-02-16 16:43:46.59', '', '2023-02-16 16:43:46.59', '', 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除版本文件', 99, '', 0, 2, 1, 'sys:version:deleteFile', '', '2023-02-16 16:43:46.59', '', '2023-02-16 16:43:46.59', '', 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('加载管理', 98, '/deviceversion', 0, 1, 1, 'sys:versionControl:queryLoadVersion', null, '2023-06-02 09:47:19.30', null, '2023-06-02 09:47:24.01', null, 1, '', '1', 'version/deviceversion');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('设备服务管理', 98, '/deviceservice', 0, 1, 1, 'sys:versionControl:queryRunVersion', null, '2023-06-02 09:47:19.30', null, '2023-06-02 09:47:19.30', null, 1, '', '1', 'version/deviceservice');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('启动', 155, null, 0, 2, 1, 'sys:versionControl:run', null, '2023-06-02 09:47:19.30', null, '2023-06-02 09:47:19.30', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('停止', 155, null, 0, 2, 1, 'sys:versionControl:stop', null, '2023-06-02 09:47:19.30', null, '2023-06-02 09:47:19.30', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('卸载', 155, null, 0, 2, 1, 'sys:versionControl:uninstall', null, '2023-06-02 09:47:19.30', null, '2023-06-02 09:47:19.30', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('加载', 154, null, 0, 2, 1, 'sys:versionControl:load', null, '2023-06-02 09:47:19.30', null, '2023-06-02 09:47:24.01', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('加载运行', 154, null, 0, 2, 1, 'sys:versionControl:loadAndRun', null, '2023-06-02 09:47:19.30', null, '2023-06-02 09:47:24.01', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('资源监控', 70, '/resource', 0, 1, 1, 'sys:monitor:resource', null, '2023-06-02 09:47:27.25', null, '2023-06-02 09:47:27.25', null, 1, '', '0', 'monitor/resource');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('设备流量监控', 70, '/device', 0, 1, 1, 'sys:monitor:device', null, '2023-06-02 09:47:27.25', null, '2023-06-02 09:47:27.25', null, 1, '', '0', 'monitor/device');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('系统终端监控', 70, '/systemterminal', 0, 1, 1, 'sys:monitor:system', null, '2023-06-02 09:47:27.25', null, '2023-06-02 09:47:27.25', null, 1, '', '0', 'monitor/system');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('告警监控', 70, '/warning', 0, 1, 1, 'sys:monitor:warning', null, '2023-06-02 09:47:27.25', null, '2023-06-02 09:47:27.25', null, 1, '', '0', 'monitor/warning');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('指控中心', 28, '/node/center', 0, 1, 1, 'sys:center:query', null, '2023-07-18 10:24:30.12', null, '2023-09-26 17:57:04.14', null, 1, '', '0', 'node/center');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('数据采集', 28, '/node/datacollect', 0, 1, 1, 'sys:dataCollect:query', null, '2023-07-18 10:24:30.12', null, '2023-09-21 17:17:56.15', null, 1, '', '0', 'node/datacollect');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('接入基站参数配置', 61, '/setting/instation', 0, 1, 1, 'sys:parmInStation:query', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '1', 'setting/instation');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('边界基站参数配置', 61, '/setting/boundarystation', 0, 1, 1, 'sys:parmBoundaryStation:query', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '1', 'setting/boundarystation');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('密钥中心参数配置', 61, '/setting/secret', 0, 1, 1, 'sys:parmSecret:query', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '1', 'setting/secret');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('数据采集参数配置', 61, '/setting/datacollect', 0, 1, 1, 'sys:parmDataCollect:query', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '1', 'setting/datacollect');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建采集系统', 170, '', 0, 2, 1, 'sys:dataCollect:insert', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('修改采集系统', 170, '', 0, 2, 1, 'sys:dataCollect:modify', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除采集系统', 170, '', 0, 2, 1, 'sys:dataCollect:delete', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑', 172, '', 0, 2, 1, 'sys:parmInStation:modify', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('同步', 172, '', 0, 2, 1, 'sys:parmInStation:synchronous', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('恢复默认', 172, '', 0, 2, 1, 'sys:parmInStation:default', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑', 173, '', 0, 2, 1, 'sys:parmBoundaryStation:modify', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('同步', 173, '', 0, 2, 1, 'sys:parmBoundaryStation:synchronous', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('恢复默认', 173, '', 0, 2, 1, 'sys:parmBoundaryStation:default', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑', 174, '', 0, 2, 1, 'sys:parmSecret:modify', null, '2023-07-18 10:24:30.13', null, '2023-07-18 10:24:30.13', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('同步', 174, '', 0, 2, 1, 'sys:parmSecret:synchronous', null, '2023-07-18 10:24:30.13', null, '2023-07-18 10:24:30.13', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('恢复默认', 174, '', 0, 2, 1, 'sys:parmSecret:default', null, '2023-07-18 10:24:30.13', null, '2023-07-18 10:24:30.13', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('数据采集启用/禁用', 175, '', 0, 2, 1, 'sys:parmDataCollect:enable', null, '2023-07-18 10:24:30.13', null, '2023-07-18 10:24:30.13', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('修改基础配置', 175, '', 0, 2, 1, 'sys:parmDataCollect:modify', null, '2023-07-18 10:24:30.13', null, '2023-07-18 10:24:30.13', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('恢复默认基础配置', 175, '', 0, 2, 1, 'sys:parmDataCollect:default', null, '2023-07-18 10:24:30.13', null, '2023-07-18 10:24:30.13', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('手动上报业务数据', 175, '', 0, 2, 1, 'sys:parmDataCollect:report', null, '2023-07-18 10:24:30.13', null, '2023-07-18 10:24:30.13', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('修改上报信息', 175, '', 0, 2, 1, 'sys:parmDataCollect:modifyConfig', null, '2023-07-18 10:24:30.13', null, '2023-07-18 10:24:30.13', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('恢复默认业务配置', 175, '', 0, 2, 1, 'sys:parmDataCollect:defaultConfig', null, '2023-07-18 10:24:30.13', null, '2023-07-18 10:24:30.13', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('同步基础配置', 175, '', 0, 2, 1, 'sys:parmDataCollect:syncBase', null, '2023-10-11 16:29:25.88', null, '2023-10-11 16:29:25.88', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('同步业务配置', 175, '', 0, 2, 1, 'sys:parmDataCollect:syncBusiness', null, '2023-10-11 16:29:25.96', null, '2023-10-11 16:29:25.96', null, 1, '', '1', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建基站', 91, null, 0, 2, 1, 'sys:boundarystation:save', null, '2023-08-31 10:33:02.16', null, '2023-09-01 10:19:48.13', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除基站', 91, null, 0, 2, 1, 'sys:boundarystation:delete', null, '2023-08-31 10:33:02.23', null, '2023-09-01 10:19:48.13', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('编辑基站', 91, null, 0, 2, 1, 'sys:boundarystation:update', null, '2023-08-31 10:33:02.31', null, '2023-09-01 10:19:48.13', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('密钥中心分配', 49, '/link/allocate', 0, 1, 1, 'sys:keyCenterAllocate:query', null, '2023-06-02 09:46:53.10', null, '2023-09-26 17:59:45.53', null, 1, '', '0', 'link/allocate');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建指控中心', 168, '', 0, 2, 1, 'sys:center:save', null, '2023-07-18 10:24:30.12', null, '2023-10-10 09:29:45.10', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('修改指控中心', 168, '', 0, 2, 1, 'sys:center:update', null, '2023-07-18 10:24:30.12', null, '2023-10-10 09:30:00.94', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除指控中心', 168, '', 0, 2, 1, 'sys:center:delete', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('新建链路', 202, '', 0, 2, 1, 'sys:keyCenterAllocate:insert', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('修改链路', 202, '', 0, 2, 1, 'sys:keyCenterAllocate:modify', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('删除链路', 202, '', 0, 2, 1, 'sys:keyCenterAllocate:delete', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('启停链路', 202, '', 0, 2, 1, 'sys:keyCenterAllocate:switch', null, '2023-07-18 10:24:30.12', null, '2023-07-18 10:24:30.12', null, 1, '', '0', '');
INSERT INTO nmp_local.nmpl_menu (menu_name, parent_menu_id, url, is_frame, menu_type, menu_status, perms_code, create_user, create_time, update_user, update_time, remark, is_exist, icon, permission, component) VALUES ('启停链路', 50, '', 0, 2, 1, 'sys:link:switch', null, '2023-07-18 10:24:30.12', null, '2023-10-09 09:56:47.65', null, 1, '', '0', '');

INSERT INTO nmp_local.nmpl_role (role_name, role_code, menu_scope, status, create_user, create_time, update_user, update_time, remark, is_exist) VALUES ('超级管理员', 'admin', '1', 1, '-1', '2022-12-12 14:11:02.76', '1', '2023-07-19 15:17:03.63', null, 1);
INSERT INTO nmp_local.nmpl_role (role_name, role_code, menu_scope, status, create_user, create_time, update_user, update_time, remark, is_exist) VALUES ('业务管理员', 'common_admin', '1', 1, '-1', '2022-12-12 14:11:02.76', null, '2022-12-12 14:11:02.76', null, 1);


INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 1);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 2);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 3);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 4);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 5);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 6);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 7);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 8);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 9);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 10);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 11);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 12);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 13);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 14);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 17);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 18);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 19);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 20);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 21);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 23);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 24);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 25);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 27);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 28);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 29);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 30);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 31);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 32);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 33);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 34);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 36);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 37);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 38);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 40);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 41);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 42);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 44);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 45);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 46);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 48);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 49);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 50);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 51);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 52);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 54);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 55);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 61);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 63);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 70);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 71);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 73);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 74);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 77);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 78);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 79);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 80);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 81);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 82);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 86);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 87);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 88);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 89);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 90);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 95);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 96);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 98);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 99);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 100);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 102);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 116);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 118);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 119);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 120);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 121);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 122);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 124);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 125);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 126);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 127);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 128);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 129);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 131);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 132);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 133);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 134);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 135);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 136);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 137);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 138);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 139);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 140);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 141);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 142);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 143);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 144);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 145);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 146);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 147);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 148);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 151);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 152);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 154);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 155);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 157);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 158);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 159);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 160);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 161);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 163);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 164);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 165);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 166);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 170);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 172);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 173);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 174);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 175);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 177);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 178);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 179);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 181);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 182);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 183);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 185);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 186);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 187);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 189);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 190);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 191);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 193);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 194);
INSERT INTO nmp_local.nmpl_role_menu_relation (role_id, menu_id) VALUES (1, 195);

INSERT INTO nmp_local.nmpl_report_business (business_name, business_code, business_value, default_value, create_time, create_user, update_time, update_user, is_exist) VALUES ('终端用户采集', '1000', '1', '1', '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1);
INSERT INTO nmp_local.nmpl_report_business (business_name, business_code, business_value, default_value, create_time, create_user, update_time, update_user, is_exist) VALUES ('业务心跳采集', '1001', '1', '1', '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1);
INSERT INTO nmp_local.nmpl_report_business (business_name, business_code, business_value, default_value, create_time, create_user, update_time, update_user, is_exist) VALUES ('数据流量采集', '1002', '1', '1', '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1);
INSERT INTO nmp_local.nmpl_report_business (business_name, business_code, business_value, default_value, create_time, create_user, update_time, update_user, is_exist) VALUES ('小区信息采集', '1003', '1', '1', '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1);
INSERT INTO nmp_local.nmpl_report_business (business_name, business_code, business_value, default_value, create_time, create_user, update_time, update_user, is_exist) VALUES ('小区心跳采集', '1004', '1', '1', '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1);
INSERT INTO nmp_local.nmpl_report_business (business_name, business_code, business_value, default_value, create_time, create_user, update_time, update_user, is_exist) VALUES ('基站总数采集', '1005', '1', '1', '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1);
INSERT INTO nmp_local.nmpl_report_business (business_name, business_code, business_value, default_value, create_time, create_user, update_time, update_user, is_exist) VALUES ('边界基站数据采集', '1006', '1', '1', '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1);
INSERT INTO nmp_local.nmpl_report_business (business_name, business_code, business_value, default_value, create_time, create_user, update_time, update_user, is_exist) VALUES ('密钥中心数据采集', '1007', '1', '1', '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1);
INSERT INTO nmp_local.nmpl_report_business (business_name, business_code, business_value, default_value, create_time, create_user, update_time, update_user, is_exist) VALUES ('告警数据采集', '1008', '1', '1', '2023-09-19 09:53:21', null, '2023-09-19 09:53:21', null, 1);

INSERT INTO nmp_local.nmpl_user (village_id, login_account, nick_name, user_type, email, phone_number, password, role_id, status, is_exist, create_user, create_time, update_user, update_time, remark) VALUES (null, 'superAdmin', 'superAdmin', '00', null, '00000000000', 'j2sivmjjihBLggve6ed5lw==', '1', 1, 1, '-1', '2022-12-12 14:11:02.76', null, '2022-12-12 14:11:02.76', null);
INSERT INTO nmp_local.nmpl_user (village_id, login_account, nick_name, user_type, email, phone_number, password, role_id, status, is_exist, create_user, create_time, update_user, update_time, remark) VALUES (null, 'admin', 'admin', '00', null, '00000000000', 'j2sivmjjihBLggve6ed5lw==', '2', 1, 1, '-1', '2022-12-12 14:11:02.76', null, '2022-12-12 14:11:02.76', null);



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
