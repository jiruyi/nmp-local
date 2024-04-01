package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.TaskVo;
import com.matrictime.network.request.DeviceConfigReq;
import com.matrictime.network.request.DeviceReportBusinessReq;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public interface DeviceConfigService {


    Result query(DeviceConfigReq deviceConfigReq);

    Result save(DeviceConfigReq deviceConfigReq);

    Result queryReportBusiness(DeviceReportBusinessReq deviceReportBusinessReq);


    Result saveReportBusiness(DeviceReportBusinessReq deviceReportBusinessReq);

    List<Future> pushReportBusinessToProxy(List<TaskVo> taskVoList);

    List<Future> pushConfigToProxy(List<TaskVo> taskVoList);

}
