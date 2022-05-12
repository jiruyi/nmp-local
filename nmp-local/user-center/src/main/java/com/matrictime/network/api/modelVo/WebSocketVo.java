package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebSocketVo implements Serializable {
    private String destination;

    private String requestId;

    private String userId;

    private String addUserId;

    private String remarkName;

    private String remark;

    private String phoneNumber;

    private String sex;

    private String nickName;

}
