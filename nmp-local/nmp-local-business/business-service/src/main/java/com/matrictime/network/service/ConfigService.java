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
     * 配置编辑
     * @param req
     * @return
     */
    Result<EditConfigResp> editConfig(EditConfigReq req);

    /**
     * 恢复默认接口（支持全量恢复）
     * @param req
     * @return
     */
    Result<ResetDefaultConfigResp> resetDefaultConfig(ResetDefaultConfigReq req);

    /**
     * 同步配置
     * @param req
     * @return
     */
    Result<SyncConfigResp> syncConfig(SyncConfigReq req);

    /**
     * 数据采集参数配置查询
     * @return
     */
    Result<QueryDataCollectResp> queryDataCollect();

    /**
     * 恢复数据采集上报业务配置接口（支持全量恢复）
     * @param req
     * @return
     */
    Result<ResetDataBusinessConfigResp> resetDataBusinessConfig(ResetDataBusinessConfigReq req);


}
