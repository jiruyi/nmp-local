package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserGroupVo implements Serializable {

    private static final long serialVersionUID = 3584434881911412150L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 组id
     */
    private String groupId;

    /**
     * 0 删除  1 正常
     */
    private Boolean isExist;

    private String nickName;

    private String loginStatus;
}
