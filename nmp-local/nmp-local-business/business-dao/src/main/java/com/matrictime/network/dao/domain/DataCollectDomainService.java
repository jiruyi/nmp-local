package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.dao.model.NmplPcData;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.MonitorReq;
import com.matrictime.network.response.PageInfo;

import java.util.List;

public interface DataCollectDomainService {
    public PageInfo<DataCollectVo> queryByConditions(DataCollectReq dataCollectReq);

    public Integer save(DataCollectReq dataCollectReq);

    public List<NmplDataCollect> queryMonitorData(MonitorReq monitorReq) throws Exception;

//    Integer countDeviceNumber(MonitorReq monitorReq);
//
//    Double sumDataItemValue(MonitorReq monitorReq);

    public List<DataCollectVo> queryTopTen(MonitorReq monitorReq);
}
