package com.matrictime.network.response;

import lombok.Data;

@Data
public class UserInfoResp {
    private Long userId;

    private String villageId;

    private String loginAccount;

    private String nickName;

    private String userType;

    private String email;

    private String phoneNumber;

    private String password;

    private String roleId;

    private Boolean status;

    private Boolean isExist;

    private String createUser;

    private String createTime;

    private String updateUser;

    private String updateTime;

    private String remark;
}
