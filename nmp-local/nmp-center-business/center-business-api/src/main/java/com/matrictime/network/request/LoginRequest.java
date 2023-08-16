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
public class LoginRequest extends BaseRequest {

     /**
      *  登录账号
      */
    private String loginAccount;
    /**
     *  密码
     */
    private String password;
    /**
     *  手机号
     */
    private String phone;

    private String email;
    /**
     *  短信验证码
     */
    private String smsCode;
    /**
     *  登录类型 1用户名密码  2手机验验证码
     */
    private String type;
    /**
     *  用户id
     */
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
