package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CenterBaseStationInfoVo;
import com.matrictime.network.modelVo.CenterDeviceInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;

import java.util.Date;
import java.util.List;


public interface DeviceInfoService {

    Result<Integer> addDeviceInfo(DeviceInfoVo infoVo);

    Result<Integer> updateDeviceInfo(DeviceInfoVo infoVo);

    Integer updateTable(String deviceType, Date createTime, String operationType, String tableName);

    void initLocalInfo(List<CenterDeviceInfoVo> deviceInfoVos);

    void initInfo(List<CenterDeviceInfoVo> deviceInfoVos);

}
