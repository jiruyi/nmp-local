package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;
@Data
public class DataInfoVo {

    /**
     * 入网id
     */
    private String networkId;
    /**
     * 数值
     */
    private Long dataValue;
    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 上报时间
     */
    private Date uploadTime;
}
