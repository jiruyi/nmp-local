package com.matrictime.network.req;

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

    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String confirmPassword;
}
