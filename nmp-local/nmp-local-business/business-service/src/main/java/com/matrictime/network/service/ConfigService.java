package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;

import java.util.Map;

public interface ConfigService {

    /**
     * 分页查询
     * @param req
     * @return
     */
    Result<PageInfo> queryConfigByPages(QueryConfigByPagesReq req);



    /**
     * 数据采集参数配置查询
     * @return
     */
    Result<QueryDataCollectResp> queryDataCollect();

    Result editDataCollectConfig(EditDataCollectConfigReq req);

    /**
     * 配置编辑
     * @param req
     * @return
     */
    Result<SyncConfigResp> sync(SyncConfigReq req);

}
