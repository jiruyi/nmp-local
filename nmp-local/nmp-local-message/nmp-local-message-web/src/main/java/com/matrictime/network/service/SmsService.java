package com.matrictime.network.service;

import com.matrictime.network.model.Result;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/14 0014 16:23
 * @desc
 */
public interface SmsService {
    Result sendSmsCode(String phoneNumber, String bizCode);
}
