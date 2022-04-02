package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MonitorReq extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -2642651810900130015L;
    /**
     * 小区id
     */
    private String companyCode;
    /**
     * 当前时间
     */
    private Date currentTime;
    /**
     * 统计项名
     */
    private String dataItemName;

    /**
     * 收集项编号
     */
    private String dataItemCode;
}
