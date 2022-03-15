package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.MonitorReq;
import com.matrictime.network.response.PageInfo;

import java.util.List;

public interface DataCollectDomainService {
    public PageInfo<DataCollectVo> queryByConditions(DataCollectReq dataCollectReq);

    public Integer save(DataCollectReq dataCollectReq);

    public List<NmplDataCollect> queryMonitorData(MonitorReq monitorReq) throws Exception;


    public List<DataCollectVo> queryTopTen(MonitorReq monitorReq);
}
