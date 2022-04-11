package com.matrictime.network.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/4/6 0006 8:45
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest implements Serializable {

    private static final long serialVersionUID = 8195635114781810672L;
    /**
      * 主键id
      */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 设备ip
     */
    private String deviceIp;
    /**
     * 登录账号
     */
    private String loginAccount;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
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

    private String loginStatus;

    private String loginAppCode;

    private String logoutAppCode;

    private Boolean status;

    private Boolean isExist;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String remark;
}
