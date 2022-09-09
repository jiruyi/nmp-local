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

    /**
     * 当前登录系统
     */
    private String loginAppCode;

    /**
     * 当前退出系统
     */
    private String logoutAppCode;
}
