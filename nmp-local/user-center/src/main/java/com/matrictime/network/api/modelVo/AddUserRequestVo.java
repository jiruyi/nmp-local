package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AddUserRequestVo implements Serializable {

    private String requestId;

    private String userId;

    private String status;

    private String addUserId;

    private String nickName;

    private String remark;

    private Date createTime;

    private String sex;

    private String phone;
}
