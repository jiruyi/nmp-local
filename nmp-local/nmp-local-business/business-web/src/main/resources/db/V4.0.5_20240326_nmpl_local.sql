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