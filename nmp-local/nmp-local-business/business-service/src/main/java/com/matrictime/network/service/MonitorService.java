package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.CheckHeartReq;
import com.matrictime.network.request.QueryMonitorReq;
import com.matrictime.network.request.TotalLoadChangeReq;
import com.matrictime.network.response.CheckHeartResp;
import com.matrictime.network.response.QueryMonitorResp;
import com.matrictime.network.response.TotalLoadChangeResp;

public interface MonitorService {

    /**
     * 心跳监测
     * @param req
     * @return
     */
    Result<CheckHeartResp> checkHeart(CheckHeartReq req);

    /**
     * 监控轮询展示查询
     * @param req
     * @return
     */
    Result<QueryMonitorResp> queryMonitor(QueryMonitorReq req);

    /**
     * 总带宽负载变化查询
     * @param req
     * @return
     */
    Result<TotalLoadChangeResp> totalLoadChange(TotalLoadChangeReq req);

}
