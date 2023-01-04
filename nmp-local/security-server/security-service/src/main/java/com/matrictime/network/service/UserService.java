package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.UserReq;


public interface UserService {

    public Result login(UserReq req);


    public Result loginOut();

}
