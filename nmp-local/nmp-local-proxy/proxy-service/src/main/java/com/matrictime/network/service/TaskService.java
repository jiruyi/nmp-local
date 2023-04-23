package com.matrictime.network.service;

import java.util.Date;

public interface TaskService {

    void heartReport(String url);

    void logPush(String url);

    void pcData(String url);

    void dataCollectPush(String url);

    void billPush(String url);

    /**
     * 物理设备心跳上报服务
     * @param uploadTime
     */
    void physicalDeviceHeartbeat(Date uploadTime);

    /**
     * 物理设备资源情况上报服务
     * @param uploadTime
     */
    void physicalDeviceResource(Date uploadTime);

    /**
     * 运行系统资源上报服务
     * @param uploadTime
     */
    void systemResource(Date uploadTime);

}
