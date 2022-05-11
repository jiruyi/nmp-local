package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserGroupReq extends BaseReq implements Serializable {
    private static final long serialVersionUID = -4435912094417233661L;

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

    /**
     *  目标组id
     */
    private String targetGroupId;



}
