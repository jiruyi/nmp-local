package com.matrictime.network.dao.domain.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationCountRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.CountBaseStationResponse;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class DeviceServiceDomainImpl implements DeviceDomainService {

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    private NmplDeviceExtraInfoMapper nmplDeviceExtraInfoMapper;

    @Resource
    private NmplDeviceMapper nmplDeviceMapper;

    @Resource
    private NmplDeviceCountMapper nmplDeviceCountMapper;


    @Override
    public int insertDevice(DeviceInfoRequest deviceInfoRequest) {
        InsertCheckUnique(deviceInfoRequest);
        NmplDevice nmplDevice = new NmplDevice();
        BeanUtils.copyProperties(deviceInfoRequest,nmplDevice);
        return nmplDeviceMapper.insertSelective(nmplDevice);
//        return nmplDeviceInfoMapper.insertDevice(deviceInfoRequest);
    }

    @Override
    public int deleteDevice(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.deleteDevice(deviceInfoRequest);
    }

    @Override
    public int updateDevice(DeviceInfoRequest deviceInfoRequest) {
        UpdateCheckUnique(deviceInfoRequest);
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
    public List<DeviceInfoVo> selectDeviceForLinkRelation(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.selectDeviceForLinkRelation(deviceInfoRequest);
    }

    @Override
    public List<DeviceInfoVo> selectActiveDevice(DeviceInfoRequest deviceInfoRequest) {
        return nmplDeviceInfoMapper.selectActiveDevice(deviceInfoRequest);
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


    @Override
    public void InsertCheckUnique(DeviceInfoRequest deviceInfoRequest) {
        //同设备ip不可相同
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andLanIpEqualTo(deviceInfoRequest.getLanIp()).andIsExistEqualTo(true).andDeviceTypeEqualTo(deviceInfoRequest.getDeviceType());
        nmplDeviceInfoExample.or().andStationNetworkIdEqualTo(deviceInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

        NmplDeviceExtraInfoExample nmplDeviceExtraInfoExample = new NmplDeviceExtraInfoExample();
        nmplDeviceExtraInfoExample.createCriteria().andLanIpEqualTo(deviceInfoRequest.getLanIp()).andIsExistEqualTo(true).andDeviceTypeEqualTo(deviceInfoRequest.getDeviceType());
        nmplDeviceExtraInfoExample.or().andStationNetworkIdEqualTo(deviceInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplDeviceExtraInfo> nmplDeviceExtraInfos =  nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        if(!CollectionUtils.isEmpty(nmplDeviceInfos)||!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
            throw new SystemException("同设备ip或入网码重复");
        }

        //不同设备 ip+端口不能相同
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andLanIpEqualTo(deviceInfoRequest.getLanIp())
                .andLanPortEqualTo(deviceInfoRequest.getLanPort()).andIsExistEqualTo(true);
        nmplBaseStationInfoExample.or().andStationNetworkIdEqualTo(deviceInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);

        nmplDeviceExtraInfoExample.clear();
        nmplDeviceExtraInfoExample.createCriteria().andLanIpEqualTo(deviceInfoRequest.getLanIp())
                .andLanPortEqualTo(deviceInfoRequest.getLanPort()).andIsExistEqualTo(true).andDeviceTypeNotEqualTo(deviceInfoRequest.getDeviceType());
        nmplDeviceExtraInfos = nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        nmplDeviceInfoExample.clear();
        nmplDeviceInfoExample.createCriteria().andLanIpEqualTo(deviceInfoRequest.getLanIp())
                .andLanPortEqualTo(deviceInfoRequest.getLanPort()).andIsExistEqualTo(true).andDeviceTypeNotEqualTo(deviceInfoRequest.getDeviceType());
        List<NmplDeviceInfo> nmplDeviceInfos1 = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

        if(!CollectionUtils.isEmpty(nmplBaseStationInfos)||!CollectionUtils.isEmpty(nmplDeviceExtraInfos)
            || !CollectionUtils.isEmpty(nmplDeviceInfos1)){
            throw new SystemException("不同设备ip+端口或入网码重复");
        }
    }

    @Override
    public void UpdateCheckUnique(DeviceInfoRequest deviceInfoRequest) {
        //修改时不能修改设备号 以及ip
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andStationNetworkIdEqualTo(deviceInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

        NmplDeviceExtraInfoExample nmplDeviceExtraInfoExample = new NmplDeviceExtraInfoExample();
        nmplDeviceExtraInfoExample.createCriteria().andStationNetworkIdEqualTo(deviceInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplDeviceExtraInfo> nmplDeviceExtraInfos =  nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        if(!CollectionUtils.isEmpty(nmplDeviceInfos)){
            //如果被修改的设备是自己
            if(!nmplDeviceInfos.get(0).getDeviceId().equals(deviceInfoRequest.getDeviceId())){
                throw new SystemException("设备入网码重复");
            }
        }
        if(!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
            throw new SystemException("备用设备入网码重复");
        }

        //不同设备 ip+端口不能相同
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andLanIpEqualTo(deviceInfoRequest.getLanIp())
                .andLanPortEqualTo(deviceInfoRequest.getLanPort()).andIsExistEqualTo(true);
        nmplBaseStationInfoExample.or().andStationNetworkIdEqualTo(deviceInfoRequest.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);

        nmplDeviceExtraInfoExample.clear();
        nmplDeviceExtraInfoExample.createCriteria().andLanIpEqualTo(deviceInfoRequest.getLanIp())
                .andLanPortEqualTo(deviceInfoRequest.getLanPort()).andIsExistEqualTo(true).andDeviceTypeNotEqualTo(deviceInfoRequest.getDeviceType());
        nmplDeviceExtraInfos = nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);

        nmplDeviceInfoExample.clear();
        nmplDeviceInfoExample.createCriteria().andLanIpEqualTo(deviceInfoRequest.getLanIp())
                .andLanPortEqualTo(deviceInfoRequest.getLanPort()).andIsExistEqualTo(true).andDeviceTypeNotEqualTo(deviceInfoRequest.getDeviceType());
        List<NmplDeviceInfo> nmplDeviceInfos1 = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

        if(!CollectionUtils.isEmpty(nmplBaseStationInfos)||!CollectionUtils.isEmpty(nmplDeviceExtraInfos)
                || !CollectionUtils.isEmpty(nmplDeviceInfos1)){
            throw new SystemException("不同设备ip+端口或入网码重复");
        }
    }

    @Override
    public CountBaseStationResponse countBaseStation(DeviceInfoRequest deviceInfoRequest) {
        NmplDeviceCountExample nmplDeviceCountExample = new NmplDeviceCountExample();
        NmplDeviceCountExample.Criteria criteria = nmplDeviceCountExample.createCriteria();
        criteria.andDeviceTypeEqualTo(deviceInfoRequest.getDeviceType());
        criteria.andRelationOperatorIdEqualTo(deviceInfoRequest.getRelationOperatorId());
        List<NmplDeviceCount> nmplDeviceCounts = nmplDeviceCountMapper.selectByExample(nmplDeviceCountExample);
        CountBaseStationResponse countBaseStationResponse = new CountBaseStationResponse();
        if(!CollectionUtils.isEmpty(nmplDeviceCounts)){
            int currentConnectCount = 0;
            for (int i = 0;i < nmplDeviceCounts.size();i++){
                if(StringUtils.isEmpty(nmplDeviceCounts.get(i).getCurrentConnectCount())){
                    nmplDeviceCounts.get(i).setCurrentConnectCount("0");
                }
                currentConnectCount = currentConnectCount +
                        Integer.parseInt(currentConnectCount + nmplDeviceCounts.get(i).getCurrentConnectCount());
            }
            countBaseStationResponse.setCountBaseStation(nmplDeviceCounts.size());
            countBaseStationResponse.setUserCount(currentConnectCount);
        }
        return countBaseStationResponse;
    }

    @Override
    public int updateConnectCount(BaseStationCountRequest baseStationCountRequest) {
        NmplDeviceCountExample nmplDeviceCountExample = new NmplDeviceCountExample();
        NmplDeviceCountExample.Criteria criteria = nmplDeviceCountExample.createCriteria();
        criteria.andDeviceIdEqualTo(baseStationCountRequest.getDeviceId());
        NmplDeviceCount nmplDeviceCount = new NmplDeviceCount();
        BeanUtils.copyProperties(baseStationCountRequest,nmplDeviceCount);
        return nmplDeviceCountMapper.updateByExampleSelective(nmplDeviceCount,nmplDeviceCountExample);
    }
}