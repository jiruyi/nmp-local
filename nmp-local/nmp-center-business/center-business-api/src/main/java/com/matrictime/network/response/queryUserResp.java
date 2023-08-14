package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class queryUserResp implements Serializable {

    private static final long serialVersionUID = 1677928169428586340L;

    /**
     * 总用户数
     */
    private String totalUser;

    /**
     * 在线用户数
     */
    private String onlineUser;

    /**
     * 接入用户数
     */
    private String accessUser;

    /**
     * 僵尸用户数
     */
    private String zombieUser;
}
