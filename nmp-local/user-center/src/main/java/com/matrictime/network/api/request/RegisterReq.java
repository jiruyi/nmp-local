package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterReq extends BaseReq implements Serializable {
    private static final long serialVersionUID = 4714567565357599548L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 一体机设备ID
     */
    private String deviceId;

    /**
     * 登录账号
     */
    private String loginAccount;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 性别（1：男 0：女）
     */
    private String sex;

    /**
     * 用户类型（00系统用户 01注册用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件号
     */
    private String idNo;

    /**
     * 密码
     */
    private String password;

    /**
     * 当前登录系统
     */
    private String loginAppCode;
}
