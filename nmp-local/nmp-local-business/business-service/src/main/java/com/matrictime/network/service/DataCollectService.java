package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.BillRequest;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.MonitorReq;
import com.matrictime.network.response.PageInfo;

import java.util.concurrent.Future;

public interface DataCollectService {

    Result<PageInfo> queryByConditon(DataCollectReq dataCollectReq);

    Future<Result> save(DataCollectReq dataCollectReq);

    /**
     * 状态监控统计数据（已废弃）
     */
    Result monitorData(MonitorReq monitorReq);
    /**
     * 状态监控top10（已废弃）
     */
    Result monitorDataTopTen(MonitorReq monitorReq);


    Result selectAllDevice(DataCollectReq dataCollectReq);


    /**
     * 流量变化
     */
    Result flowTransformation(DataCollectReq dataCollectReq);

    /**
     * 最新流量
     */
    Result currentIpFlow(DataCollectReq dataCollectReq);

    /**
     * 处理新增数据放入redis
     */
    void handleAddData(String code,String ip);

}
