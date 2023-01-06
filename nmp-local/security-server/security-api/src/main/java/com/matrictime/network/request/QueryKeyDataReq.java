package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QueryKeyDataReq implements Serializable {

    private static final long serialVersionUID = -8942684979770433056L;

    /**
     * 数据类型（1000：剩余上行密钥量 1001：已使用上行密钥量 2000：剩余下行密钥量 2001：已使用下行密钥量）
     */
    private String dataType;

    /**
     * 时间区间（开始） 非必输
     */
    private Date beginTime;

    /**
     * 时间区间（结束） 非必输
     */
    private Date endTime;
}
