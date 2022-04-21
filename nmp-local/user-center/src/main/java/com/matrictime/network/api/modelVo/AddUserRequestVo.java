package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AddUserRequestVo implements Serializable {
    private Long id;

    private String requestId;

    private String userId;

    private String addUserId;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String nickName;
}
