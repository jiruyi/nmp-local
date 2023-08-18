package com.matrictime.network.dao.model.extend;

import lombok.Data;

import java.util.Date;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/18 0018 9:39
 * @desc
 */
@Data
public class NmplAlarmInfoExt {
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
     * 小区编码
     */
    private String alarmAreaCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 小区名字
     */
    private String companyName;
}
