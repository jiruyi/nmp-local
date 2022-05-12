package com.matrictime.network.domain;

import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.api.request.LoginReq;
import com.matrictime.network.model.Result;

public interface CommonService {
    Result encrypt(String condition,String destination, Result result) throws Exception;

    String encryptToString(String condition,String destination, Result result) throws Exception;

    Result encryptForLogin(LoginReq req, Result result) throws Exception;
}
