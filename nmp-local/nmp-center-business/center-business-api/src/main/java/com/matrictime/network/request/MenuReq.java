package com.matrictime.network.request;

import lombok.Data;

@Data
public class MenuReq {

    /**
     * 权限标识
     */
    private String permission;
    /**
     * 被查看角色id
     */
    private String roleId;

    /**
     * 当前用户角色id
     */
    private String myRoleId;
}
