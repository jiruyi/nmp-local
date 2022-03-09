package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.request.QueryConfigByPagesReq;
import com.matrictime.network.request.ResetDefaultConfigReq;
import com.matrictime.network.response.EditConfigResp;
import com.matrictime.network.response.QueryConfigByPagesResp;
import com.matrictime.network.response.ResetDefaultConfigResp;

import java.util.List;

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

    /**
     * 恢复默认接口（支持全量恢复,同时需要同步数据）
     * @param req
     * @return
     */
    Result<ResetDefaultConfigResp> resetDefaultConfig(ResetDefaultConfigReq req);
}
