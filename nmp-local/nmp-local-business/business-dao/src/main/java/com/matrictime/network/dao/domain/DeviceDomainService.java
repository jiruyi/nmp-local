package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.PageInfo;

import java.util.List;


public interface DeviceDomainService {

    int insertDevice(DeviceInfoRequest deviceInfoRequest);

    int deleteDevice(DeviceInfoRequest deviceInfoRequest);

    int updateDevice(DeviceInfoRequest deviceInfoRequest);

    PageInfo<DeviceInfoVo> selectDevice(DeviceInfoRequest deviceInfoRequest);

    List<DeviceInfoVo> selectLinkDevice(DeviceInfoRequest deviceInfoRequest);

    List<DeviceInfoVo> selectActiveDevice(DeviceInfoRequest deviceInfoRequest);

    StationVo selectDeviceId(DeviceInfoRequest deviceInfoRequest);

    PageInfo<DeviceInfoVo> selectDeviceALl(DeviceInfoRequest deviceInfoRequest);

    List<DeviceInfoVo> getDevices(DeviceInfoRequest deviceInfoRequest);


    void InsertCheckUnique(DeviceInfoRequest deviceInfoRequest);

    void UpdateCheckUnique(DeviceInfoRequest deviceInfoRequest);

}
