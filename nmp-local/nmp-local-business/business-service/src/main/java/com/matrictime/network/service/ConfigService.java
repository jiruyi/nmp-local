package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.request.QueryConfigByPagesReq;
import com.matrictime.network.response.EditConfigResp;
import com.matrictime.network.response.QueryConfigByPagesResp;

public interface ConfigService {

    /**
     * 分页查询
     * @param req
     * @return
     */
    Result<QueryConfigByPagesResp> queryByPages(QueryConfigByPagesReq req);

    /**
     * 配置编辑
     * @param req
     * @return
     */
    Result<EditConfigResp> editConfig(EditConfigReq req);
}
