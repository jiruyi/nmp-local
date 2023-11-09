package com.matrictime.network.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfoResp implements Serializable {


    private static final long serialVersionUID = -7770514075487387021L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录账号
     */
    private String loginAccount;

    /**
     * 密码
     */
    private String password;

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

}