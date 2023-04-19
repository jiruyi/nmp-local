package com.matrictime.network.request;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/4/18.
 */
@Data
public class NmplTerminalData {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 一体机设备id
     */
    private String terminalNetworkId;

    /**
     * 基站设备id
     */
    private String parentId;

    /**
     * 数据类型 01:剩余 02:补充 03:使用
     */
    private String dataType;

    /**
     * 上行密钥量
     */
    private Integer upValue;

    /**
     * 下行密钥量
     */
    private Integer downValue;

    /**
     * 一体机ip
     */
    private String terminalIp;

    /**
     * 上报时间
     */
    private Date uploadTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
