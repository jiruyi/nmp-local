package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;

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
     * 物理设备网络拓扑图查询
     * @param req
     * @return
     */
    Result<QueryPhysicalDevicesResp> queryPhysicalDevices(QueryPhysicalDevicesReq req);

    /**
     * 物理设备资源查询
     * @param req
     * @return
     */
    Result<QueryPhysicalDeviceResourceResp> queryPhysicalDeviceResource(QueryPhysicalDevicesResourceReq req);

    /**
     * 运行系统资源查询
     * @param req
     * @return
     */
    Result querySystemResource(QueryPhysicalDevicesResourceReq req);

    /**
     * 监控轮询展示查询（废弃）
     * @param req
     * @return
     */
//    Result<QueryMonitorResp> queryMonitor(QueryMonitorReq req);

    /**
     * 总带宽负载变化查询(废弃)
     * @param req
     * @return
     */
//    Result<TotalLoadChangeResp> totalLoadChange(TotalLoadChangeReq req);

}
