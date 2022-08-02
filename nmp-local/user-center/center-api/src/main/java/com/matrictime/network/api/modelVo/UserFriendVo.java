package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserFriendVo  implements Serializable {
    private String nickName;

    private String remarkName;

    private String userId;

    private String friendUserId;

    private String sex;

    private String phoneNumber;

    private String sid;

}
