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
    is_exist       tinyint(1)  default 1                    null comment '状态 true:存在(1)  false:删除(0)',
    report_time    datetime(2)                              null comment '上报时间'
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

CREATE TABLE `nmpl_static_route` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                     `route_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由Id',
                                     `company_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小区id',
                                     `station_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '基站id',
                                     `network_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '入网Id',
                                     `device_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备入网Id',
                                     `server_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务名称',
                                     `is_exist` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（1代表存在 0代表删除）',
                                     `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                     `create_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) COMMENT '创建时间',
                                     `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                     `update_time` datetime(2) DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2) COMMENT '更新时间',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='静态路由';

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

set global log_bin_trust_function_creators=TRUE;
DELIMITER $$
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

end$$

INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('01', '加强密度配置（128bit-128k）', 'as0001', '1：1', '1：1', null, 0, '2024-04-02 10:39:39.13', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('01', '加强密度配置（>128k ）', 'as0002', '1：32', '1：32', null, 0, '2024-04-02 10:39:39.28', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('01', '话单上报', 'as0003', '60', '60', 's', 0, '2024-04-02 10:39:39', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('01', '基站状态上报', 'as0004', '1', '1', 's', 0, '2024-04-02 10:39:39.46', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('01', '通信状态上报', 'as0005', '60', '60', 's', 0, '2024-04-02 10:39:39.62', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('11', '生成密钥量', 'kc0001', '10', '10', 'T', 0, '2024-04-02 10:39:39.75', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('11', '分发密钥量', 'kc0002', '1', '1', 'T', 0, '2024-04-02 10:39:39.91', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('20', '采集周期', 'dc0001', '10', '1800', 's', 0, '2024-04-02 10:39:40', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('20', '上报周期', 'dc0002', '10', '1800', 's', 0, '2024-04-02 10:39:40', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('20', '数据采集开关', 'dataSwitch', '0', '0', null, 1, '2024-04-02 10:39:40', null, 1, null);


INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('1', '用户权限', '-1', '/userCenter', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.09', NULL, '2023-09-21 17:16:58.66', NULL, '1', 'user', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('2', '角色管理', '1', '/role', '0', '1', '1', 'sys:role:query', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', 'user/role');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('3', '新建角色', '2', NULL, '0', '2', '1', 'sys:role:save', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('4', '删除角色', '2', NULL, '0', '2', '1', 'sys:role:delete', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('5', '编辑角色', '2', NULL, '0', '2', '1', 'sys:role:update', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('6', '用户管理', '1', '/user', '0', '1', '1', 'sys:user:query', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', 'user');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('7', '新建用户', '6', NULL, '0', '2', '1', 'sys:user:save', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('8', '删除用户', '6', NULL, '0', '2', '1', 'sys:user:delete', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('9', '编辑用户', '6', NULL, '0', '2', '1', 'sys:user:update', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('10', '运营商管理', '-1', '/guideindex', '0', '0', '1', '', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', 'guide', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('11', '运营商', '10', '/guide', '0', '1', '1', 'sys:operator:query', NULL, '2023-06-02 09:46:53.09', NULL, '2023-09-21 17:19:04.23', NULL, '1', '', '0', 'guide');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('12', '新增运营商', '11', NULL, '0', '2', '1', 'sys:operator:save', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('13', '修改运营商', '11', NULL, '0', '2', '1', 'sys:operator:update', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('14', '删除运营商', '11', NULL, '0', '2', '1', 'sys:operator:delete', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('17', '区域管理', '-1', '/area', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', 'add-location', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('18', '大区', '17', '/area/max', '0', '1', '1', 'sys:region:query', NULL, '2023-06-02 09:46:53.09', NULL, '2023-09-21 17:14:02.76', NULL, '1', '', '0', 'area/max');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('19', '小区', '17', '/area/min', '0', '1', '1', 'sys:village:query', NULL, '2023-06-02 09:46:53.09', NULL, '2023-09-21 17:14:04.80', NULL, '1', '', '0', 'area/min');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('20', '新建大区', '18', NULL, '0', '2', '1', 'sys:region:save', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('21', '删除大区', '18', NULL, '0', '2', '1', 'sys:region:delete', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('23', '编辑大区', '18', NULL, '0', '2', '1', 'sys:region:update', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('24', '新建小区', '19', NULL, '0', '2', '1', 'sys:village:save', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('25', '删除小区', '19', NULL, '0', '2', '1', 'sys:village:delete', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('27', '编辑小区', '19', NULL, '0', '2', '1', 'sys:village:update', NULL, '2023-06-02 09:46:53.09', NULL, '2023-06-02 09:46:53.09', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('28', '节点管理', '-1', '/node', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', 'more-filled', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('29', '接入基站', '28', '/node/instation', '0', '1', '1', 'sys:station:query', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-21 17:17:38.80', NULL, '1', '', '0', 'node/instation');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('33', '新建基站', '29', NULL, '0', '2', '1', 'sys:station:save', NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('34', '删除基站', '29', NULL, '0', '2', '1', 'sys:station:delete', NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('36', '编辑基站', '29', NULL, '0', '2', '1', 'sys:station:update', NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('37', '新建密钥中心', '92', NULL, '0', '2', '1', 'sys:dispenser:save', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-01 10:19:05.91', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('38', '删除密钥中心', '92', NULL, '0', '2', '1', 'sys:dispenser:delete', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-01 10:19:05.91', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('40', '编辑密钥中心', '92', NULL, '0', '2', '1', 'sys:dispenser:update', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-01 10:19:05.91', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('41', '新建生成机', '93', NULL, '0', '2', '1', 'sys:generator:save', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-01 10:19:05.81', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('42', '删除生成机', '93', NULL, '0', '2', '1', 'sys:generator:delete', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-01 10:19:05.81', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('44', '编辑生成机', '93', NULL, '0', '2', '1', 'sys:generator:update', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-01 10:19:05.81', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('45', '新增缓存机', '94', NULL, '0', '2', '1', 'sys:cache:save', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-01 10:19:05.72', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('46', '删除缓存机', '94', NULL, '0', '2', '1', 'sys:cache:delete', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-01 10:19:05.72', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('48', '编辑缓存机', '94', NULL, '0', '2', '1', 'sys:cache:update', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-01 10:19:05.72', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('49', '链路管理', '-1', '/linkindex', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', 'operation', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('50', '链路配置', '49', '/link', '0', '1', '1', 'sys:link:query', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-21 17:18:48.05', NULL, '1', '', '0', 'link');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('51', '新建链路', '50', NULL, '0', '2', '1', 'sys:link:save', NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('52', '删除链路', '50', NULL, '0', '2', '1', 'sys:link:delete', NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('54', '编辑链路', '50', NULL, '0', '2', '1', 'sys:link:update', NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('55', '路由管理', '-1', '/routeindex', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', 'help', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('61', '系统设置', '-1', '/setting', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', 'setting', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('63', '权限菜单', '61', '/setting/auth', '0', '1', '1', 'sys:power:query', NULL, '2023-06-02 09:46:53.10', NULL, '2023-06-02 09:46:53.10', NULL, '1', '', '0', 'setting/auth');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('64', '恢复默认参数', '62', NULL, '0', '2', '1', 'sys:parm:reset', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('65', '删除参数', '62', NULL, '0', '2', '1', 'sys:parm:delete', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('67', '编辑参数', '62', NULL, '0', '2', '1', 'sys:parm:update', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('68', '同步参数', '62', NULL, '0', '2', '1', 'sys:parm:synchro', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('70', '状态监控', '-1', '/monitor', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:47:27.26', NULL, '1', 'cpu', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('71', '状态监控', '70', '/status', '0', '1', '1', 'sys:monitor:status', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:47:27.26', NULL, '1', '', '0', 'monitor/status');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('73', '信令管理', '-1', '/signalindex', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', 'data-analysis', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('74', '信令上报', '73', '/signal', '0', '1', '1', 'sys:sign:query,sys:sign:selectDeviceIds,sys:sign:deviceIds', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '1', 'signal');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('77', '清空', '74', NULL, '0', '2', '1', 'sys:sign:clear', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('78', '导出文件', '74', NULL, '0', '2', '1', 'sys:sign:export', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('79', '日志管理', '-1', '/log', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', 'notification', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('80', '登录日志', '79', '/log/login', '0', '1', '1', 'sys:loginLog:query', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '0', 'log/login');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('81', '操作日志', '79', '/log/operate', '0', '1', '1', 'sys:operateLog:query', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '0', 'log/operate');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('82', '系统日志', '79', '/log/system', '0', '1', '1', 'sys:sysLog:query', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '0', 'log/system');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('86', '统计管理', '-1', '/statistics', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', 'monitor', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('87', '基站统计数据', '86', '/statistics/station', '0', '1', '1', 'sys:stationData:query', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '0', 'statistics/station');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('88', '密钥中心统计数据', '86', '/statistics/dispenser', '0', '1', '1', 'sys:dispenserData:query', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '0', 'statistics/dispenser');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('89', '生成机统计数据', '86', '/statistics/generate', '0', '1', '1', 'sys:generatorData:query', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '0', 'statistics/generate');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('90', '缓存机统计数据', '86', '/statistics/cache', '0', '1', '1', 'sys:cacheData:query', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '0', 'statistics/cache');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('91', '边界基站', '28', '/node/boundarystation', '0', '1', '1', 'sys:boundarystation:query', NULL, '2023-08-31 10:30:13.35', NULL, '2023-09-21 17:17:39.72', NULL, '1', '', '0', 'node/boundarystation');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('92', '密钥中心', '28', '/node/dispenser', '0', '1', '1', 'sys:dispenser:query', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-21 17:17:40.90', NULL, '1', '', '0', 'node/dispenser');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('93', '密钥生成', '28', '/node/generate', '0', '1', '1', 'sys:generator:query', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-21 17:17:46.91', NULL, '1', '', '0', 'node/generate');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('94', '缓存机', '28', '/node/cache', '0', '1', '1', 'sys:cache:query', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-21 17:17:50.33', NULL, '1', '', '0', 'node/cache');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('95', '话单管理', '-1', '/ticketindex', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', 'iphone', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('96', '话单上报', '95', '/ticket', '0', '1', '1', 'sys:bill:query', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '1', 'ticket');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('98', '版本管理', '-1', '/versionindex', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', 'warning', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('99', '版本维护', '98', '/versionmanage', '0', '1', '1', 'sys:version:fileList,sys:version:list', '', '2023-01-10 10:24:40.26', '', '2023-03-15 09:42:25.35', '', '1', '', '1', 'version/versionmanage');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('100', '新建版本', '99', NULL, '0', '2', '1', 'sys:version:save', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('102', '删除版本文件', '99', NULL, '0', '2', '1', 'sys:version:deleteFile', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('116', '修改用户密码(更多)', '6', NULL, '0', '2', '1', 'sys:user:changePasswd', NULL, '2023-06-02 09:46:53.11', NULL, '2023-06-02 09:46:53.11', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('118', '信令启停', '74', NULL, '0', '2', '1', 'sys:sign:track', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('119', '离线分发机认证放号', '-1', '/outlinedispenserindex', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', 'collection', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('120', '离线分发机认证放号', '119', '/outlinedispenser', '0', '1', '1', 'sys:sorter:query', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', 'outlinedispenser');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('121', '新建设备', '120', NULL, '0', '2', '1', 'sys:sorter:insert', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('122', '编辑', '120', NULL, '0', '2', '1', 'sys:sorter:modify', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('124', '删除', '120', NULL, '0', '2', '1', 'sys:sorter:delete', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('125', '导入文件', '120', NULL, '0', '2', '1', 'sys:sorter:upload', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('126', '终端设备认证放号', '-1', '/allinoneindex', '0', '0', '1', NULL, NULL, '2023-06-02 09:46:53.12', NULL, '2023-07-20 10:01:53.46', NULL, '1', 'credit-card', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('127', '终端设备认证放号', '126', '/allinone', '0', '1', '1', 'sys:pc:query', NULL, '2023-06-02 09:46:53.12', NULL, '2023-07-20 10:01:53.46', NULL, '1', '', '0', 'allinone');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('128', '新建设备', '127', NULL, '0', '2', '1', 'sys:pc:insert', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('129', '删除', '127', NULL, '0', '2', '1', 'sys:pc:delete', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('131', '编辑', '127', NULL, '0', '2', '1', 'sys:pc:modify', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('132', '导入文件', '127', NULL, '0', '2', '1', 'sys:pc:upload', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('133', '主备', '28', '/node/mainbackup', '0', '1', '1', 'sys:deviceExtra:query', NULL, '2023-06-02 09:46:53.12', NULL, '2023-09-21 17:17:53.59', NULL, '1', '', '0', 'node/mainbackup');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('134', '新建主备', '133', NULL, '0', '2', '1', 'sys:deviceExtra:save', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('135', '编辑主备', '133', NULL, '0', '2', '1', 'sys:deviceExtra:update', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('136', '删除主备', '133', NULL, '0', '2', '1', 'sys:deviceExtra:delete', NULL, '2023-06-02 09:46:53.12', NULL, '2023-06-02 09:46:53.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('137', 'ID分配', '55', '/routestatic', '0', '1', '1', 'sys:staticRoute:select', NULL, '2022-09-22 14:15:12.01', NULL, '2023-09-21 17:15:53.52', NULL, '1', '', '0', 'route/static');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('138', '路由配置', '55', '/routeweb', '0', '1', '1', 'sys:internetRoute:select', NULL, '2022-09-22 14:16:10.70', NULL, '2023-09-21 17:15:41.74', NULL, '1', '', '0', 'route/web');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('139', '业务服务', '55', '/routeservice', '0', '1', '1', 'sys:businessRoute:select', NULL, '2022-09-22 14:15:53.80', NULL, '2023-06-02 09:46:55.62', NULL, '1', '', '0', 'route/service');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('140', '新建路由', '137', NULL, '0', '2', '1', 'sys:staticRoute:insert', NULL, '2022-09-22 14:17:12.15', NULL, '2023-06-02 09:46:55.62', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('141', '删除', '137', NULL, '0', '2', '1', 'sys:staticRoute:delete', NULL, '2022-09-22 14:17:37.23', NULL, '2023-06-02 09:46:55.62', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('142', '编辑', '137', NULL, '0', '2', '1', 'sys:staticRoute:update', NULL, '2022-09-22 14:17:46.96', NULL, '2023-06-02 09:46:55.62', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('143', '填入', '139', NULL, '0', '2', '1', 'sys:businessRoute:insert', NULL, '2022-09-22 14:18:49.52', NULL, '2023-09-27 09:53:50.89', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('144', '编辑', '139', NULL, '0', '2', '1', 'sys:businessRoute:update', NULL, '2022-09-22 14:18:59.12', NULL, '2023-09-27 09:53:52.92', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('145', '新建路由', '138', NULL, '0', '2', '1', 'sys:internetRoute:insert', NULL, '2022-09-22 14:19:44.65', NULL, '2023-09-27 09:53:55.02', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('146', '编辑', '138', NULL, '0', '2', '1', 'sys:internetRoute:update', NULL, '2022-09-22 14:20:13.46', NULL, '2023-09-27 09:53:57.60', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('147', '删除', '138', NULL, '0', '2', '1', 'sys:internetRoute:delete', NULL, '2022-09-22 14:20:17.36', NULL, '2023-09-27 09:54:00.50', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('151', '更新版本文件', '99', '', '0', '2', '1', 'sys:version:updateFile', '', '2023-02-16 16:43:46.59', '', '2023-02-16 16:43:46.59', '', '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('152', '删除版本文件', '99', '', '0', '2', '1', 'sys:version:deleteFile', '', '2023-02-16 16:43:46.59', '', '2023-02-16 16:43:46.59', '', '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('154', '加载管理', '98', '/deviceversion', '0', '1', '1', 'sys:versionControl:queryLoadVersion', NULL, '2023-06-02 09:47:19.30', NULL, '2023-06-02 09:47:24.01', NULL, '1', '', '1', 'version/deviceversion');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('155', '设备服务管理', '98', '/deviceservice', '0', '1', '1', 'sys:versionControl:queryRunVersion', NULL, '2023-06-02 09:47:19.30', NULL, '2023-06-02 09:47:19.30', NULL, '1', '', '1', 'version/deviceservice');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('157', '启动', '155', NULL, '0', '2', '1', 'sys:versionControl:run', NULL, '2023-06-02 09:47:19.30', NULL, '2023-06-02 09:47:19.30', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('158', '停止', '155', NULL, '0', '2', '1', 'sys:versionControl:stop', NULL, '2023-06-02 09:47:19.30', NULL, '2023-06-02 09:47:19.30', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('159', '卸载', '155', NULL, '0', '2', '1', 'sys:versionControl:uninstall', NULL, '2023-06-02 09:47:19.30', NULL, '2023-06-02 09:47:19.30', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('160', '加载', '154', NULL, '0', '2', '1', 'sys:versionControl:load', NULL, '2023-06-02 09:47:19.30', NULL, '2023-06-02 09:47:24.01', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('161', '加载运行', '154', NULL, '0', '2', '1', 'sys:versionControl:loadAndRun', NULL, '2023-06-02 09:47:19.30', NULL, '2023-06-02 09:47:24.01', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('163', '资源监控', '70', '/resource', '0', '1', '1', 'sys:monitor:resource', NULL, '2023-06-02 09:47:27.25', NULL, '2023-06-02 09:47:27.25', NULL, '1', '', '0', 'monitor/resource');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('164', '设备流量监控', '70', '/device', '0', '1', '1', 'sys:monitor:device', NULL, '2023-06-02 09:47:27.25', NULL, '2023-06-02 09:47:27.25', NULL, '1', '', '0', 'monitor/device');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('165', '系统终端监控', '70', '/systemterminal', '0', '1', '1', 'sys:monitor:system', NULL, '2023-06-02 09:47:27.25', NULL, '2023-06-02 09:47:27.25', NULL, '1', '', '0', 'monitor/system');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('166', '告警监控', '70', '/warning', '0', '1', '1', 'sys:monitor:warning', NULL, '2023-06-02 09:47:27.25', NULL, '2023-06-02 09:47:27.25', NULL, '1', '', '0', 'monitor/warning');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('168', '指控中心', '28', '/node/center', '0', '1', '1', 'sys:center:query', NULL, '2023-07-18 10:24:30.12', NULL, '2023-09-26 17:57:04.14', NULL, '1', '', '0', 'node/center');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('170', '数据采集', '28', '/node/datacollect', '0', '1', '1', 'sys:dataCollect:query', NULL, '2023-07-18 10:24:30.12', NULL, '2023-09-21 17:17:56.15', NULL, '1', '', '0', 'node/datacollect');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('173', '定时任务配置', '61', '/setting/schedule', '0', '1', '1', 'sys:taskConfig:query', NULL, '2023-07-18 10:24:30.12', NULL, '2024-04-09 14:50:51.07', NULL, '1', '', '1', 'setting/schedule');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('174', '网元参数配置', '61', '/setting/networkset', '0', '1', '1', 'sys:config:query', NULL, '2023-07-18 10:24:30.12', NULL, '2024-04-09 14:51:07.72', NULL, '1', '', '1', 'setting/networkset');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('175', '采集业务配置', '61', '/setting/datacollect', '0', '1', '1', 'sys:parmDataCollect:query', NULL, '2023-07-18 10:24:30.12', NULL, '2024-04-02 15:03:23.29', NULL, '1', '', '1', 'setting/datacollect');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('177', '新建采集系统', '170', '', '0', '2', '1', 'sys:dataCollect:insert', NULL, '2023-07-18 10:24:30.12', NULL, '2023-07-18 10:24:30.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('178', '修改采集系统', '170', '', '0', '2', '1', 'sys:dataCollect:modify', NULL, '2023-07-18 10:24:30.12', NULL, '2023-07-18 10:24:30.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('179', '删除采集系统', '170', '', '0', '2', '1', 'sys:dataCollect:delete', NULL, '2023-07-18 10:24:30.12', NULL, '2023-07-18 10:24:30.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('193', '手动上报业务数据', '175', '', '0', '2', '1', 'sys:parmDataCollect:report', NULL, '2023-07-18 10:24:30.13', NULL, '2023-07-18 10:24:30.13', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('194', '修改上报信息', '175', '', '0', '2', '1', 'sys:parmDataCollect:modifyConfig', NULL, '2023-07-18 10:24:30.13', NULL, '2023-07-18 10:24:30.13', NULL, '1', '', '1', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('199', '新建基站', '91', NULL, '0', '2', '1', 'sys:boundarystation:save', NULL, '2023-08-31 10:33:02.16', NULL, '2023-09-01 10:19:48.13', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('200', '删除基站', '91', NULL, '0', '2', '1', 'sys:boundarystation:delete', NULL, '2023-08-31 10:33:02.23', NULL, '2023-09-01 10:19:48.13', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('201', '编辑基站', '91', NULL, '0', '2', '1', 'sys:boundarystation:update', NULL, '2023-08-31 10:33:02.31', NULL, '2023-09-01 10:19:48.13', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('202', '密钥中心分配', '49', '/link/allocate', '0', '1', '1', 'sys:keyCenterAllocate:query', NULL, '2023-06-02 09:46:53.10', NULL, '2023-09-26 17:59:45.53', NULL, '1', '', '0', 'link/allocate');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('203', '新建指控中心', '168', '', '0', '2', '1', 'sys:center:insert', NULL, '2023-07-18 10:24:30.12', NULL, '2023-07-18 10:24:30.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('204', '修改指控中心', '168', '', '0', '2', '1', 'sys:center:modify', NULL, '2023-07-18 10:24:30.12', NULL, '2023-07-18 10:24:30.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('205', '删除指控中心', '168', '', '0', '2', '1', 'sys:center:delete', NULL, '2023-07-18 10:24:30.12', NULL, '2023-07-18 10:24:30.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('206', '新建链路', '202', '', '0', '2', '1', 'sys:keyCenterAllocate:insert', NULL, '2023-07-18 10:24:30.12', NULL, '2023-07-18 10:24:30.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('207', '修改链路', '202', '', '0', '2', '1', 'sys:keyCenterAllocate:modify', NULL, '2023-07-18 10:24:30.12', NULL, '2023-07-18 10:24:30.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('208', '删除链路', '202', '', '0', '2', '1', 'sys:keyCenterAllocate:delete', NULL, '2023-07-18 10:24:30.12', NULL, '2023-07-18 10:24:30.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('209', '启停链路', '202', '', '0', '2', '1', 'sys:keyCenterAllocate:switch', NULL, '2023-07-18 10:24:30.12', NULL, '2023-07-18 10:24:30.12', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('210', '同步参数', '173', NULL, '0', '2', '1', 'sys:taskConfig:sync', NULL, '2024-04-02 15:10:19.63', NULL, '2024-04-02 15:13:13.24', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('211', '同步参数', '174', NULL, '0', '2', '1', 'sys:config:sync', NULL, '2024-04-02 15:10:40.08', NULL, '2024-04-02 15:21:01.04', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('212', '配置', '29', NULL, '0', '2', '1', 'sys:deviceTask:stationInsideConfig', NULL, '2023-06-02 09:46:53.10', NULL, '2024-04-02 15:20:49.99', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('213', '配置', '91', NULL, '0', '2', '1', 'sys:deviceTask:stationBoundaryConfig', NULL, '2023-06-02 09:46:53.10', NULL, '2024-04-02 15:21:12.85', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('214', '配置', '92', NULL, '0', '2', '1', 'sys:deviceTask:dispenserConfig', NULL, '2023-06-02 09:46:53.10', NULL, '2024-04-02 15:21:24.27', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('217', '配置', '168', NULL, '0', '2', '1', 'sys:deviceTask:dataBaseConfig', NULL, '2023-06-02 09:46:53.10', NULL, '2024-04-02 15:22:45.99', NULL, '1', '', '0', '');
INSERT INTO `nmp_local`.`nmpl_menu` (`menu_id`, `menu_name`, `parent_menu_id`, `url`, `is_frame`, `menu_type`, `menu_status`, `perms_code`, `create_user`, `create_time`, `update_user`, `update_time`, `remark`, `is_exist`, `icon`, `permission`, `component`) VALUES ('218', '配置', '170', NULL, '0', '2', '1', 'sys:deviceTask:accuseCenterConfig', NULL, '2023-06-02 09:46:53.10', NULL, '2024-04-02 15:22:49.54', NULL, '1', '', '0', '');


INSERT INTO nmp_local.nmpl_role (role_name, role_code, menu_scope, status, create_user, create_time, update_user, update_time, remark, is_exist) VALUES ('超级管理员', 'admin', '1', 1, '-1', '2022-12-12 14:11:02.76', '1', '2023-07-19 15:17:03.63', null, 1);
INSERT INTO nmp_local.nmpl_role (role_name, role_code, menu_scope, status, create_user, create_time, update_user, update_time, remark, is_exist) VALUES ('业务管理员', 'common_admin', '1', 1, '-1', '2022-12-12 14:11:02.76', null, '2022-12-12 14:11:02.76', null, 1);



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

INSERT INTO nmp_local.sys_sequence (seq_name, min_value, max_value, current_value, increment_value) VALUES ('seq_name1', 1, 2000000000, 1, 1);


create table nmpl_task_config
(
    id             bigint auto_increment                    comment '主键'
        primary key,
    task_id        varchar(16)                              comment '任务ID',
    task_name      varchar(16)                              null comment '业务名称',
    device_type    char(2)                                  null comment '设备类型 01:接入基站 02:边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心',
    task_type      char(2)                                  null comment '任务类型 01:定时任务 02:周期任务',
    current_value  bigint                                   null comment '当前值',
    default_value  bigint                                   null comment '默认值',
    status         tinyint(1)  default 1                    null comment '启停 1启动 0 停止',
    remark         varchar(64)                              null comment '备注',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    update_user    varchar(20)                              null comment '修改人',
    is_exist       tinyint(1)  default 1                    null comment '状态 true:存在(1)  false:删除(0)'
)
    comment '通用网元定时任务配置表';

create table nmpl_device_task_config
(
    id             bigint auto_increment                    comment '主键'
        primary key,
    task_id        varchar(16)                              comment '任务ID',
    task_name      varchar(16)                              null comment '业务名称',
    device_id      varchar(128)                             null comment '设备id',
    device_type    char(2)                                  null comment '设备类型 01:接入基站 02:边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心',
    task_type      char(2)                                  null comment '任务类型 01:定时任务 02:周期任务',
    current_value  bigint                                   null comment '当前值',
    status         tinyint(1)  default 1                    null comment '启停 1启动 0 停止',
    remark         varchar(64)                             null comment '备注',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    update_user    varchar(20)                              null comment '修改人',
    is_exist       tinyint(1)  default 1                    null comment '状态 true:存在(1)  false:删除(0)'
)
    comment '节点网元定时任务配置表';

create table nmpl_device_config
(
    id            bigint auto_increment comment '主键'
        primary key,
    device_id     varchar(128)                             null comment '设备id',
    device_type   char(2)                                  null comment '设备类型 01:接入基站 02:边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心',
    config_name   varchar(100)                             null comment '配置项名称',
    config_code   varchar(32)                              null comment '配置编码',
    config_value  varchar(2000)                            null comment '配置值',
    default_value varchar(2000)                            null comment '默认值',
    unit          varchar(32)                              null comment '单位',
    status        tinyint     default 1                    null comment '状态 1同步 0 未同步',
    update_time   datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    update_user   varchar(20)                              null comment '修改人',
    is_exist      tinyint(1)  default 1                    null comment '状态 true:存在(1)  false:删除(0)',
    remark        varchar(100)                             null comment '备注'
)
    comment '节点网元系统配置表';

create table nmpl_device_report_business
(
    id             bigint auto_increment comment '主键'
        primary key,
    device_id      varchar(128)                             null comment '设备id',
    business_name  varchar(100)                             null comment '业务名称',
    business_code  varchar(32)                              null comment '业务编码',
    business_value varchar(128)                             null comment '业务配置值',
    report_time    datetime(2) default CURRENT_TIMESTAMP(2) null comment '上报时间',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    update_user    varchar(20)                              null comment '修改人',
    is_exist       tinyint(1)  default 1                    null comment '状态 true:存在(1)  false:删除(0)'
)
    comment '数据采集节点业务上报配置表';


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
