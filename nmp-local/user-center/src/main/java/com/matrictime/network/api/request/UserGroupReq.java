package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserGroupReq implements Serializable {
    private static final long serialVersionUID = -4435912094417233661L;

    private String userId;
    /**
     * 组id
     */
    private String groupId;
    /**
     *  目标组id
     */
    private String targetGroupId;

    private Boolean isExist;

}
