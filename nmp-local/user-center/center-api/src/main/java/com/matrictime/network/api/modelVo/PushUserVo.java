package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PushUserVo implements Serializable {

    private static final long serialVersionUID = -839018111380835881L;

    /**
     * ID
     */
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户id
     */
    private String friendUserId;

    /**
     * 绑定本地用户id
     */
    private String lId;

    /**
     * sid
     */
    private String sid;

    /**
     * 一体机设备ID
     */
    private String deviceId;

    /**
     * 登录ip
     */
    private String deviceIp;

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
     * 当前登录状态
     */
    private String loginStatus;

    /**
     * 当前登录系统
     */
    private String loginAppCode;

    /**
     * 当前退出系统
     */
    private String logoutAppCode;

    /**
     * 帐号状态（1正常 0停用注销）
     */
    private Boolean status;

    /**
     * 删除标志（1代表存在 0代表删除）
     */
    private Boolean isExist;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    private Byte agreeFriend;

    /**
     * 删除好友是否通知（0：不通知 1：通知）
     */
    private Boolean deleteFriend;
}
