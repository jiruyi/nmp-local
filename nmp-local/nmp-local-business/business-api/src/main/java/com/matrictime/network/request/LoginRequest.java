package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/6 0006 14:59
 * @desc
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String loginAccount;

    private String password;

    private String phone;

    private String email;

    private String smsCode;

    private String type;

    private String userId;

    /**
      * 登录ip
      */
    private String sourceIp;

    /**
     * 登录地址
     */
    private String loginAddr;
}
