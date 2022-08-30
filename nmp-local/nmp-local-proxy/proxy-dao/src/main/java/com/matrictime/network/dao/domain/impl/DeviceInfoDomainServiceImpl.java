package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.DeviceInfoDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.DeviceInfoMapper;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class DeviceInfoDomainServiceImpl implements DeviceInfoDomainService {

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    @Override
    @Transactional
    public int insert(NmplDeviceInfo deviceInfo) {
        return nmplDeviceInfoMapper.insertSelective(deviceInfo);
    }

    @Override
    @Transactional
    public int update(NmplDeviceInfo deviceInfo) {
        return nmplDeviceInfoMapper.updateByPrimaryKeySelective(deviceInfo);
    }

    @Override
    @Transactional
    public int insertDeviceInfo(List<DeviceInfoVo> deviceInfos) {
        return deviceInfoMapper.batchInsert(deviceInfos);
    }

}