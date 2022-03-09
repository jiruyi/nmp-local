package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.request.DeviceInfoRequest;

import java.util.List;

public interface DeviceDomainService {

    int insertDevice(DeviceInfoRequest deviceInfoRequest);

    int deleteDevice(DeviceInfoRequest deviceInfoRequest);

    int updateDevice(DeviceInfoRequest deviceInfoRequest);

    List<DeviceInfoVo> selectDevice(DeviceInfoRequest deviceInfoRequest);
}
