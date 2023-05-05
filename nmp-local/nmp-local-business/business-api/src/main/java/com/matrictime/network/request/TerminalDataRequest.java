package com.matrictime.network.request;

import lombok.Data;



/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
@Data
public class TerminalDataRequest extends BaseRequest{
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
     * 基站设备ip
     */
    private String parenIp;



}
