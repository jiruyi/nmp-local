package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.DataInfoReq;
import com.matrictime.network.req.DataReq;
import com.matrictime.network.resp.DailyDataResp;

import java.util.List;

public interface DataService {
    Result<List<DailyDataResp>> queryWaveformData(DataReq dataReq);

    Result queryCurrentData(DataReq dataReq);


    Result insert(DataInfoReq dataInfoReq);
}
