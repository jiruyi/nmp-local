package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebSocketVo implements Serializable {

    private static final long serialVersionUID = -3054182848388200367L;

    private String destination;

    private String requestId;

    private String userId;

    private String addUserId;

    private String remarkName;

    private String remark;

    private String phoneNumber;

    private String sex;

    private String nickName;

    private String loginAccount;

}
