package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.DeviceTaskConfigReq;

public interface NmplDeviceTaskService {

    Result query(DeviceTaskConfigReq deviceTaskConfigReq);

    Result save(DeviceTaskConfigReq deviceTaskConfigReq);

}
