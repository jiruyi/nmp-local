/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.27 : Database - nmp_security_server
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `nmp_security_proxy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  /*!80016 DEFAULT ENCRYPTION='N' */;

USE `nmp_security_proxy`;

create table nmps_security_server_info
(
    id            bigint auto_increment comment '主键ID'
        primary key,
    server_name   varchar(32)                              null comment '安全服务器名称',
    ip            varchar(1024)                            null comment 'ip',
    network_id    varchar(128)                             null comment '入网id',
    net_card_type char(2)                                  null comment '网卡类型（1：物理网卡 2：虚拟网卡）',
    adapter_name  varchar(64)                              null comment '适配器名称',
    server_status char(2)     default '02'                 null comment '状态 01:上线  02:下线',
    is_exist      tinyint(1)  default 1                    null comment '删除标志（1代表存在 0代表删除）',
    create_user   varchar(64)                              null comment '创建者',
    create_time   datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user   varchar(64)                              null comment '更新者',
    update_time   datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    remark        varchar(500)                             null comment '备注'
)
    comment '安全服务器信息表';

create table nmps_server_heart_info
(
    network_id    varchar(128)                             not null comment '入网id',
    server_status smallint                                 not null comment '状态 1:正常',
    create_time   datetime(2) default CURRENT_TIMESTAMP(2) not null comment '创建时间'
)
    comment '安全服务器心跳上报信息表';


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
