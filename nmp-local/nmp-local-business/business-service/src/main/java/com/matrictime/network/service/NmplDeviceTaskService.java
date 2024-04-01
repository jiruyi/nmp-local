package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.TaskVo;
import com.matrictime.network.request.DeviceTaskConfigReq;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public interface NmplDeviceTaskService {

    Result query(DeviceTaskConfigReq deviceTaskConfigReq);

    Result save(DeviceTaskConfigReq deviceTaskConfigReq);


    List<Future> pushToProxy(List<TaskVo> taskVoList);

}
