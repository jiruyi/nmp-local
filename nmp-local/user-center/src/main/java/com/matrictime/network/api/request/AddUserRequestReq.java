package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class AddUserRequestReq implements Serializable {
    private Long id;

    private String requestId;

    private String userId;

    private String addUserId;

    private String remarkName;

    private String status;

    private Date createTime;

    private Date updateTime;
}
