package com.matrictime.network.service;

import java.util.Date;

public interface TaskService {

    /**
     * 安全服务器状态上报服务
     * @param excuteTime
     */
    void heartReport(Date excuteTime);


    void dataPush();

    /**
     * 初始化本端代理数据
     */
    void initData();

}
