package com.matrictime.network.service;

import com.matrictime.network.api.request.LoginReq;
import com.matrictime.network.api.request.LogoutReq;
import com.matrictime.network.api.request.RegisterReq;
import com.matrictime.network.model.Result;

public interface LoginService {
    /**
     * 注册
     * @param registerReq
     * @return
     */
    Result register(RegisterReq registerReq);

    /**
     * 登录
     * @param loginReq
     * @return
     */
    Result login(LoginReq loginReq);

    /**
     * 登出
     * @param logoutReq
     * @return
     */
    Result logout(LogoutReq logoutReq);
}
