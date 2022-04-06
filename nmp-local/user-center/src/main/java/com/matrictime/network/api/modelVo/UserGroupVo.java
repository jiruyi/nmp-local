package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserGroupVo implements Serializable {

    private static final long serialVersionUID = 3584434881911412150L;

    private Long userId;

    private Long groupId;

    private String nickName;

    private String loginStatus;

    private Boolean isExist;
}
