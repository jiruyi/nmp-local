package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.CheckHeartReq;
import com.matrictime.network.request.QueryMonitorReq;
import com.matrictime.network.response.CheckHeartResp;
import com.matrictime.network.response.QueryMonitorResp;

public interface MonitorService {

    /**
     * 心跳监测
     * @param req
     * @return
     */
    Result<CheckHeartResp> checkHeart(CheckHeartReq req);

    /**
     *
     * @param req
     * @return
     */
    Result<QueryMonitorResp> queryMonitor(QueryMonitorReq req);

}
