package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.ProxyReq;
import com.matrictime.network.response.ProxyResp;

public interface ProxyInitService {

    Result<ProxyResp> init(ProxyReq req);
}
