package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.response.EditConfigResp;
public interface ConfigService {

    /**
     * 配置编辑
     * @param req
     * @return
     */
    Result<EditConfigResp> editConfig(EditConfigReq req);

}
