package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;



public interface DeviceService {

    Result<Integer> insertDevice(DeviceInfoRequest deviceInfoRequest);

    Result<Integer> deleteDevice(DeviceInfoRequest deviceInfoRequest);

    Result<Integer> updateDevice(DeviceInfoRequest deviceInfoRequest);

    Result<PageInfo> selectDevice(DeviceInfoRequest deviceInfoRequest);

    Result<DeviceResponse> selectLinkDevice(DeviceInfoRequest deviceInfoRequest);

    Result<DeviceResponse> selectDeviceForLinkRelation(DeviceInfoRequest deviceInfoRequest);

    Result<DeviceResponse> selectActiveDevice(DeviceInfoRequest deviceInfoRequest);

    Result<StationVo> selectDeviceId(DeviceInfoRequest deviceInfoRequest);

    Result<PageInfo> selectDeviceALl(DeviceInfoRequest deviceInfoRequest);

    public void pushToProxy(String deviceId,String suffix)throws Exception;
}
