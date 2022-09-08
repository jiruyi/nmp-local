package com.matrictime.network.service;

import com.matrictime.network.api.request.*;
import com.matrictime.network.api.response.LoginResp;
import com.matrictime.network.api.response.RegisterResp;
import com.matrictime.network.model.Result;

public interface LoginService {
    /**
     * 注册
     * @param registerReq
     * @return
     */
    Result<RegisterResp> register(RegisterReq registerReq);

    /**
     * 登录
     * @param loginReq
     * @return
     */
    Result<LoginResp> login(LoginReq loginReq);

    /**
     * 登出
     * @param logoutReq
     * @return
     */
    Result logout(LogoutReq logoutReq);

    /**
     * 绑定
     * @param bindReq
     * @return
     */
    Result bind(BindReq bindReq);


    /**
     * 删除用户
     * @param deleteUserReq
     * @return
     */
//    Result deleteUser(DeleteUserReq deleteUserReq);

    /**
     * 系统登出
     * @param logoutReq
     * @return
     */
    Result syslogout(LogoutReq logoutReq);

    Result pushToken(PushTokenReq req);


}
