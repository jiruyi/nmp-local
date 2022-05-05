package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.DeviceExtraInfoRequest;

public interface DeviceExtraInfoService {
    Result<Integer> insert(DeviceExtraInfoRequest deviceExtraInfoRequest);

    Result<Integer> update(DeviceExtraInfoRequest deviceExtraInfoRequest);

    Result<Integer> delete(DeviceExtraInfoRequest deviceExtraInfoRequest);
}
