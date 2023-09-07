package com.matrictime.network.dao.model;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @author   xxxx
 * @date   2023-04-18
 */
@Data
public class NmplAlarmInfo {
    /**
     * 主键
     */
    private Long alarmId;

    /**
     * 业务系统id  物理设备无
     */
    private String alarmSourceId;

    /**
     * 设备ip
     */
    private String alarmSourceIp;

    /**
     * 告警内容
     */
    private String alarmContent;

    /**
     * 级别 1严重 2 紧急 3 一般
     */
    private String alarmLevel;

    /**
     * 操作时间
     */
    private Date alarmUploadTime;

    /**
     * 来源类型： 1资源告警 2接入告警  3 边界  4 密钥中心
     */
    private String alarmSourceType;

    /**
     * 告警内容类型  1: cpu 2 内存 3 磁盘 4流量 5 其他
     */
    private String alarmContentType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 小区编码
     */
    private String alarmAreaCode;
}