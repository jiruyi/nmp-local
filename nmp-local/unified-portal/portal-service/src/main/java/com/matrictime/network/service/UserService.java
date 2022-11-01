package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.UserReq;

public interface UserService {

    public Result login(UserReq req);


    public Result loginOut(UserReq req);

}
