package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DeviceExtraVo;
import com.matrictime.network.request.DeviceExtraInfoRequest;
import com.matrictime.network.response.PageInfo;

public interface DeviceExtraInfoService {
    Result<Integer> insert(DeviceExtraInfoRequest deviceExtraInfoRequest);

    Result<Integer> update(DeviceExtraInfoRequest deviceExtraInfoRequest);

    Result<Integer> delete(DeviceExtraInfoRequest deviceExtraInfoRequest);

    Result<PageInfo> select(DeviceExtraInfoRequest deviceExtraInfoRequest);
}
