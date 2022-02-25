package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.LoginRequest;
import com.matrictime.network.response.LoginResponse;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/24 0024 10:22
 * @desc
 */
public interface UserService {

    /**
      * @title login 登录接口
      * @param [loginRequest]
      * @return com.matrictime.network.model.Result<com.matrictime.network.response.LoginResponse>
      * @description 
      * @author jiruyi
      * @create 2022/2/24 0024 11:33
      */
    Result<LoginResponse> login(LoginRequest loginRequest);
}
