package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/15 0015 10:06
 * @desc
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsCodeRequest {

    /**
      *手机号
      */
    private String phoneNo;
    /**
     *业务类型
     */
    private String bizCode;
    /**
     *验证码
     */
    private String smsCode;
}
