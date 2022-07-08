package com.matrictime.network.domain;

import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.api.request.LoginReq;
import com.matrictime.network.model.Result;

public interface CommonService {
    Result encrypt(String condition,String destination, Result result) throws Exception;

    String encryptToString(String condition,String destination, Object o) throws Exception;

    Result encryptForWs(String condition,String destination, Result result) throws Exception;

    Result encryptForLogin(LoginReq req,String destination, Result result) throws Exception;

    Result encryptForRegister(String sid,String destination, Result res) throws Exception;
}
