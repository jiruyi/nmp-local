package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteUserReq extends BaseReq implements Serializable {

    private static final long serialVersionUID = -4150477607283081078L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 登录账号
     */
    private String loginAccount;

    /**
     * 电话
     */
    private String phoneNumber;

    /**
     * 删除类型（1：用户id 2：登录账号 3：电话）
     */
    private String deleteType;

    /**
     * 删除系统(uc:用户中心 jzdq:矩阵地球)
     */
    private String opSystem;
}
