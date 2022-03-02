package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryConfigByPagesReq extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 9149339166450996327L;

    /**
     * 查询配置项名称（支持模糊查询）
     */
    private String configName;
}
