package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest extends BaseRequest implements Serializable {
    private Long roleId;

    private String roleName;

    private String roleCode;

    private String menuScope;

    private Byte status;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String remark;

    private Byte isExist;

    private String startTime;

    private String endTime;

    private boolean isAdmin;

    private List<Long> meduId;

}
