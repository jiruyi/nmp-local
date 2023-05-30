package com.matrictime.network.service;

import java.util.Date;

public interface TaskService {

    void heartReport();

//    void logPush(String url);
//
//    void pcData(String url);

    void dataCollectPush(String url,String localIp);

//    void billPush(String url);

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

    /**
     * 业务心跳上报
     * @param url
     */
    void systemHeartbeat(String url);

    /**
     * 用户状态上报
     * @param url
     */
    void terminalUser(String url);

    /**
     * 终端流量收集
     * @param url
     */
    void collectTerminalData(String url);

    /**
     * 更新在线用户
     */
    void updateCurrentConnectCount(String url);

}
