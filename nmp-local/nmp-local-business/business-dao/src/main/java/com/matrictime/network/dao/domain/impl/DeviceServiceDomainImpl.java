package com.matrictime.network.dao.domain.impl;


import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.request.DeviceInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class DeviceServiceDomainImpl implements DeviceDomainService {

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Override
    public int insertDevice(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.insertDevice(deviceInfoRequest);
    }
}