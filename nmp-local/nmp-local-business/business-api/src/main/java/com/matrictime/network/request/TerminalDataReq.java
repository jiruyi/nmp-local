package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class TerminalDataReq implements Serializable {

    private static final long serialVersionUID = -7632485470328621204L;

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

}
