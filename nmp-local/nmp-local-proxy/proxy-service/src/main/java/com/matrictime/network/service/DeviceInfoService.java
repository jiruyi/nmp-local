package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DeviceInfoVo;

import java.util.Date;


public interface DeviceInfoService {

    Result<Integer> addDeviceInfo(DeviceInfoVo infoVo);

    Result<Integer> updateDeviceInfo(DeviceInfoVo infoVo);

    Integer updateTable(String deviceType, Date createTime, String operationType);

}
