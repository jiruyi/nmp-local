package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.DeviceResponse;


public interface DeviceService {

    Result<Integer> insertDevice(DeviceInfoRequest deviceInfoRequest);

    Result<Integer> deleteDevice(DeviceInfoRequest deviceInfoRequest);

    Result<Integer> updateDevice(DeviceInfoRequest deviceInfoRequest);

    Result<DeviceResponse> selectDevice(DeviceInfoRequest deviceInfoRequest);
}
