USE `nmp_proxy`;

create table nmpl_task_config
(
    id        bigint                                        comment '主键'
        primary key,
    task_id        varchar(16)                              comment '任务ID',
    task_name      varchar(16)                              null comment '业务名称',
    task_type      char(2)                                  null comment '任务类型 01:定时任务 02:周期任务',
    device_id      varchar(128)                             null comment '设备id',
    current_value  bigint                                   null comment '当前值',
    status         tinyint(1)  default 1                    null comment '启停 1启动 0 停止',
    remark         varchar(64)                              null comment '备注',
    update_time    datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    update_user    varchar(20)                              null comment '修改人',
    is_exist       tinyint(1)  default 1                    null comment '状态 true:存在(1)  false:删除(0)'
)
    comment '定时任务配置表';

drop table nmpl_config;
create table nmpl_config
(
    id            bigint                                   comment '主键'
        primary key,
    device_id     varchar(128)                             null comment '设备id',
    config_name   varchar(100)                             null comment '配置项名称',
    config_code   varchar(32)                              null comment '配置编码',
    config_value  varchar(2000)                            null comment '配置值',
    unit          varchar(32)                              null comment '单位',
    status        tinyint     default 1                    null comment '状态 1同步 0 未同步',
    update_time   datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    update_user   varchar(20)                              null comment '修改人',
    is_exist      tinyint(1)  default 1                    null comment '状态 true:存在(1)  false:删除(0)',
    remark        varchar(100)                             null comment '备注'
)
    comment '系统配置表';