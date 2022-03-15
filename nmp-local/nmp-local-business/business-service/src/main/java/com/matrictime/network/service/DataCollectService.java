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


    Result monitorData(MonitorReq monitorReq);

    Result monitorDataTopTen(MonitorReq monitorReq);

}
