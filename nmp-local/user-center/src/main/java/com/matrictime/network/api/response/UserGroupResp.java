package com.matrictime.network.api.response;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserGroupResp implements Serializable {

    private static final long serialVersionUID = 7470179248525359847L;

    private Long userId;

    private Long groupId;

    private Boolean isExist;

    private String nickName;

    private String loginStatus;


}
