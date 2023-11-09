package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.UserReq;
import com.matrictime.network.resp.UserInfoResp;


public interface UserService {
    /**
     * 登录
     * @param req
     * @return
     */
    public Result login(UserReq req);

    /**
     * 登出
     * @return
     */
    public Result loginOut();

    /**
     * 修改密码
     * @param req
     * @return
     */
    Result changePasswd(UserReq req);

    Result<UserInfoResp> getUserInfo(UserReq userRequest);

}
