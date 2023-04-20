package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.CheckHeartResp;
import com.matrictime.network.response.QueryMonitorResp;
import com.matrictime.network.response.TotalLoadChangeResp;
import org.springframework.web.bind.annotation.RequestBody;

public interface MonitorService {

    /**
     * 心跳监测
     * @param req
     * @return
     */
    Result<CheckHeartResp> checkHeart(CheckHeartReq req);

    /**
     * 物理设备心跳上报
     * @param req
     * @return
     */
    Result physicalDeviceHeartbeat(PhysicalDeviceHeartbeatReq req);

    /**
     * 物理设备资源情况信息上报
     * @param req
     * @return
     */
    Result physicalDeviceResource(PhysicalDeviceResourceReq req);

    /**
     * 运行系统资源信息上报
     * @param req
     * @return
     */
    Result systemResource(SystemResourceReq req);

    /**
     * 监控轮询展示查询（废弃）
     * @param req
     * @return
     */
    Result<QueryMonitorResp> queryMonitor(QueryMonitorReq req);

    /**
     * 总带宽负载变化查询(废弃)
     * @param req
     * @return
     */
    Result<TotalLoadChangeResp> totalLoadChange(TotalLoadChangeReq req);

}
