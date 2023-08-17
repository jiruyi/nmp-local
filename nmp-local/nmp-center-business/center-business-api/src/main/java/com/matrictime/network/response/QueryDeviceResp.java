package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryDeviceResp implements Serializable {

    private static final long serialVersionUID = -1946906627784791637L;

    /**
     * 总网络数
     */
    private String totalNet;

    /**
     * 总接入基站数
     */
    private String totalInsideStation;

    /**
     * 总密钥中心数
     */
    private String totalKeyCenter;

    /**
     * 总边界基站数
     */
    private String totalBoundaryStation;

    /**
     * 总一体机数
     */
    private String totalOutlinePc;

    /**
     * 总安全服务器数
     */
    private String totalSafeServer;
}
