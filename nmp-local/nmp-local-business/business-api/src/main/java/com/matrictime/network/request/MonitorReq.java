package com.matrictime.network.request;

import lombok.Data;

@Data
public class MonitorReq {
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
