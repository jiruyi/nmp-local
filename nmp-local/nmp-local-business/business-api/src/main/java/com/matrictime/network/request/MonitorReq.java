package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class MonitorReq extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -2642651810900130015L;
    /**
     * 小区id
     */
    private String companyId;
    /**
     * 当前时间
     */
    private String currentTime;
    /**
     * 统计项名
     */
    private String dataItemName;

    /**
     * 收集项编号
     */
    private String dataItemCode;
}
