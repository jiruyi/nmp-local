package com.matrictime.network.request;

import lombok.Data;

@Data
public class UserReq {
    /**
     *  登录账号
     */
    private String loginAccount;
    /**
     *  密码
     */
    private String password;

    /**
     *  用户id
     */
    private String userId;
}
