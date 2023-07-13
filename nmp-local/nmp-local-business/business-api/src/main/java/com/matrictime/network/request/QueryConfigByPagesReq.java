package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryConfigByPagesReq extends BaseRequest implements Serializable {


    private static final long serialVersionUID = -2187274622413435553L;
    /**
     * 查询配置项名称（支持模糊查询）
     */
    private String configName;

    /**
     * 系统类型：必输（01:接入基站 02:边界基站 11:密钥中心 20:数据采集）
     */
    private String deviceType;
}
