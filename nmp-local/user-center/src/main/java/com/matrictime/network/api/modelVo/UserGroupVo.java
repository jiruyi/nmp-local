package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserGroupVo implements Serializable {

    private static final long serialVersionUID = 3584434881911412150L;

    private String userId;

    private String groupId;

    private String nickName;

    private String loginStatus;

    private Boolean isExist;
}
