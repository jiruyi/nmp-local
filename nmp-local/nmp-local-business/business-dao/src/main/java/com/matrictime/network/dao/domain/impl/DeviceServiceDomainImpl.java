package com.matrictime.network.dao.domain.impl;


import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.request.DeviceInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class DeviceServiceDomainImpl implements DeviceDomainService {

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Override
    public int insertDevice(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.insertDevice(deviceInfoRequest);
    }

    @Override
    public int deleteDevice(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.deleteDevice(deviceInfoRequest);
    }

    @Override
    public int updateDevice(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.updateDevice(deviceInfoRequest);
    }

    @Override
    public List<DeviceInfoVo> selectDevice(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.selectDevice(deviceInfoRequest);
    }
}