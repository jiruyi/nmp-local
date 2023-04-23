package com.matrictime.network.service;

import java.util.Date;

public interface TaskService {

    void heartReport(String url);

    void logPush(String url);

    void pcData(String url);

    void dataCollectPush(String url,String localIp);

    void billPush(String url);

    /**
     * 物理设备心跳上报服务
     * @param uploadTime
     * @param url
     */
    void physicalDeviceHeartbeat(Date uploadTime, String url);

    /**
     * 物理设备资源情况上报服务
     * @param uploadTime
     * @param url
     */
    void physicalDeviceResource(Date uploadTime,String url);

    /**
     * 运行系统资源上报服务
     * @param uploadTime
     * @param url
     */
    void systemResource(Date uploadTime,String url);

    /**
     * 业务心跳上报
     * @param url
     */
    void SystemHeartbeat(String url);

    /**
     * 用户状态上报
     * @param url
     */
    void TerminalUser(String url);

    /**
     * 终端流量收集
     * @param url
     */
    void collectTerminalData(String url);

}
