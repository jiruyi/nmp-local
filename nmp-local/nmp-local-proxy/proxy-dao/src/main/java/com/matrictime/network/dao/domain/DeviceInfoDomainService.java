package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;

import java.util.List;


public interface DeviceInfoDomainService {

    int insert(NmplDeviceInfo deviceInfo);

    int update(NmplDeviceInfo deviceInfo);

    int insertDeviceInfo(List<DeviceInfoVo> deviceInfos);

    int localInsertDeviceInfo(List<DeviceInfoVo> deviceInfos);

}
