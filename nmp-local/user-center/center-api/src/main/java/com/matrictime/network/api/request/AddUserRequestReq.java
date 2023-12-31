package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class AddUserRequestReq extends BaseReq implements Serializable {
    private Long id;

    private String requestId;

    private String userId;

    private String addUserId;

    private String remarkName;

    private String remark;

    private String status;

    private String agree;

    private String sex;

    private String nickName;

    private String groupId;

    private String addGroupId;

    private Date createTime;

    private Date updateTime;

    private String refuse;
}
