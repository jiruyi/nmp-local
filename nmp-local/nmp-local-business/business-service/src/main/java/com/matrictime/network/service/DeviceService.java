package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.DeviceInfoRequest;

public interface DeviceService {

    Result<Integer> insertDevice(DeviceInfoRequest deviceInfoRequest);
}
