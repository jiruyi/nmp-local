/*
V4.0.5增量sql包
*/

USE `nmp_local`;

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

alter table nmpl_report_business add report_time datetime(2)  null comment '上报时间';

drop table nmpl_config;
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

INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('01', '加强密度配置（128bit-128k）', 'as0001', '1：1', '1：1', null, 0, '2024-04-02 10:39:39.13', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('01', '加强密度配置（>128k ）', 'as0002', '1：32', '1：32', null, 0, '2024-04-02 10:39:39.28', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('01', '话单上报', 'as0003', '60', '60', 's', 0, '2024-04-02 10:39:39', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('01', '基站状态上报', 'as0004', '1', '1', 's', 0, '2024-04-02 10:39:39.46', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('01', '通信状态上报', 'as0005', '60', '60', 's', 0, '2024-04-02 10:39:39.62', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('11', '生成密钥量', 'kc0001', '10', '10', 'T', 0, '2024-04-02 10:39:39.75', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('11', '分发密钥量', 'kc0002', '1', '1', 'T', 0, '2024-04-02 10:39:39.91', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('20', '采集周期', 'dc0001', '101', '1800', 's', 0, '2024-04-02 10:39:40', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('20', '上报周期', 'dc0002', '100', '1800', 's', 0, '2024-04-02 10:39:40', null, 1, null);
INSERT INTO nmp_local.nmpl_config (device_type, config_name, config_code, config_value, default_value, unit, status, update_time, update_user, is_exist, remark) VALUES ('20', '数据采集开关', 'dataSwitch', '0', '0', null, 1, '2024-04-02 10:39:40', null, 1, null);
