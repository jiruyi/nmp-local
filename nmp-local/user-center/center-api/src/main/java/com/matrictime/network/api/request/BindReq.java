package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BindReq extends BaseReq implements Serializable {
    private static final long serialVersionUID = -2336467512723505925L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 操作类型，bing-绑定，unbind-解绑
     */
    private String oprType;

    /**
     * 本地用户id
     */
    private String lid;

    /**
     * 中心用户名,可选
     */
    private String loginAccount;
}
