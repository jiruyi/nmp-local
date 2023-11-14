package com.matrictime.network.dao.model;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @author   cxk
 * @date   2023-11-10
 */
@Data
public class NmpsAlarmInfo {
    /**
     * 主键
     */
    private Long alarmId;

    /**
     * 入网id
     */
    private String networkId;

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
     * 来源类型 00资源告警 01安全服务器
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

}