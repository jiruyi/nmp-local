package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by wangqiang
 * @date 2022/9/8.
 */
@Data
public class AppCodeRequest extends BaseReq implements Serializable {
    /**
     * 用户id
     */
    private String userId;

    private String loginAppCode;

    private String logoutAppCode;
}
