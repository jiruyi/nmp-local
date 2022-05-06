package com.matrictime.network.api.response;

import lombok.Data;

@Data
public class UserResp {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String phoneNumber;

    private String sex;


}
