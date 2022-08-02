package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserFriendReq extends BaseReq implements Serializable {
    private String nickName;

    private String remarkName;

    private String userId;

    private String friendUserId;

    private String sex;

    private String phoneNumber;
}
