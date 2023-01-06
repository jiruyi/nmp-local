package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.resp.GetServerStatusResp;

public interface ServerService {

    Result<GetServerStatusResp> getStatus();

    Result start();
}
