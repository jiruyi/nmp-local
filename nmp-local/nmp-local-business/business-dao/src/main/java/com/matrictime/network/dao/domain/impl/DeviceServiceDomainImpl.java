package com.matrictime.network.dao.domain.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.PageInfo;
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
    public PageInfo<DeviceInfoVo> selectDevice(DeviceInfoRequest deviceInfoRequest) {
        Page page = PageHelper.startPage(deviceInfoRequest.getPageNo(),deviceInfoRequest.getPageSize());
        List<DeviceInfoVo> deviceInfoVoList = nmplDeviceInfoMapper.selectDevice(deviceInfoRequest);
        PageInfo<DeviceInfoVo> pageResult =  new PageInfo<>();
        pageResult.setList(deviceInfoVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return  pageResult;
    }

    @Override
    public List<DeviceInfoVo> selectLinkDevice(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.selectDevice(deviceInfoRequest);
    }

    @Override
    public StationVo selectDeviceId(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.selectDeviceId(deviceInfoRequest);
    }

    @Override
    public PageInfo<DeviceInfoVo> selectDeviceALl(DeviceInfoRequest deviceInfoRequest) {
        Page page = PageHelper.startPage(deviceInfoRequest.getPageNo(),deviceInfoRequest.getPageSize());
        List<DeviceInfoVo> deviceInfoVoList = nmplDeviceInfoMapper.selectDeviceALl(deviceInfoRequest);
        PageInfo<DeviceInfoVo> pageResult =  new PageInfo<>();
        pageResult.setList(deviceInfoVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return  pageResult;
    }

    @Override
    public List<DeviceInfoVo> getDevices(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.getDevices(deviceInfoRequest);
    }

}