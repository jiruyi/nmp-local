package com.matrictime.network.domain;

import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.model.Result;

public interface CommonService {
    Result encrypt(String userId,String destination, Result result) throws Exception;
}
