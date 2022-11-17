/*
Navicat MySQL Data Transfer

Source Server         : nmpl
Source Server Version : 80027
Source Host           : 192.168.72.230:3306
Source Database       : user_center

Target Server Type    : MYSQL
Target Server Version : 80027
File Encoding         : 65001

Date: 2022-05-16 16:02:37
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `user_center` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci /*!80016 DEFAULT ENCRYPTION='N' */;

USE `user_center`;

-- ----------------------------
-- Table structure for user
-- ----------------------------
create table user
(
    id              bigint auto_increment comment 'ID'
        primary key,
    user_id         varchar(50)                              not null comment '用户id',
    l_id            varchar(50)                              null comment '绑定本地用户id',
    sid             varchar(64)                              null comment 'sid',
    ca_id           varchar(128)                             null comment '证书id',
    device_id       varchar(30)                              null comment '一体机设备ID',
    device_ip       varchar(20)                              null comment '登录ip',
    login_account   varchar(30)                              not null comment '登录账号',
    nick_name       varchar(30)                              null comment '用户昵称',
    sex             char                                     null comment '性别（1：男 0：女）',
    user_type       char(2)     default '01'                 null comment '用户类型（00系统用户 01注册用户）',
    email           varchar(50)                              null comment '用户邮箱',
    phone_number    varchar(11)                              null comment '手机号码',
    id_type         char(2)                                  null comment '证件类型',
    id_no           varchar(30)                              null comment '证件号',
    password        varchar(128)                             null comment '密码',
    login_status    char        default '0'                  not null comment '当前登录状态(0:未登录 1:已登录)',
    login_app_code  varchar(20) default ''                   null comment '当前登录系统',
    logout_app_code varchar(512) default ''                  null comment '当前退出系统',
    agree_friend    tinyint(1)  default 1                    null comment '添加好友条件（0：直接添加 1：需要询问）',
    delete_friend   tinyint(1)  default 0                    null comment '删除好友是否通知（0：不通知 1：通知）',
    status          tinyint(1)  default 1                    null comment '帐号状态（1正常 0停用注销）',
    is_exist        tinyint(1)  default 1                    null comment '删除标志（1代表存在 0代表删除）',
    create_user     varchar(64)                              null comment '创建者',
    create_time     datetime(2) default CURRENT_TIMESTAMP(2) null comment '创建时间',
    update_user     varchar(64)                              null comment '更新者',
    update_time     datetime(2) default CURRENT_TIMESTAMP(2) null on update CURRENT_TIMESTAMP(2) comment '更新时间',
    remark          varchar(500)                             null comment '备注'
)
    comment '用户信息表';


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
