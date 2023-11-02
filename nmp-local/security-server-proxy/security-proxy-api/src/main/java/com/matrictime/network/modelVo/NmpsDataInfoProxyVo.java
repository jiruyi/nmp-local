package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class NmpsDataInfoProxyVo implements Serializable {


    private static final long serialVersionUID = -955825028312427561L;
    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 数据值（单位byte）
     */
    private Long dataValue;

    /**
     * 数据类型（1000：剩余上行密钥量 1001：已使用上行密钥量 2000：剩余下行密钥量 2001：已使用下行密钥量）
     */
    private String dataType;

    /**
     * 上报时间
     */
    private Date uploadTime;

    /**
     * 创建时间
     */
    private Date createTime;
}
