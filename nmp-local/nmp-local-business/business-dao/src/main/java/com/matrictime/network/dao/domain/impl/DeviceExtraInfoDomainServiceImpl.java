package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.DeviceExtraInfoDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceExtraInfoMapper;
import com.matrictime.network.dao.model.NmplDeviceExtraInfo;
import com.matrictime.network.dao.model.NmplDeviceExtraInfoExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DeviceExtraInfoDomainServiceImpl implements DeviceExtraInfoDomainService {

    @Resource
    NmplDeviceExtraInfoMapper nmplDeviceExtraInfoMapper;

    @Override
    public int insert(NmplDeviceExtraInfo nmplDeviceExtraInfo) {
        //入参判断
        if(nmplDeviceExtraInfo.getStationNetworkId() != null ||
                !"".equals(nmplDeviceExtraInfo.getStationNetworkId())){
            NmplDeviceExtraInfoExample nmplDeviceExtraInfoExample =new NmplDeviceExtraInfoExample();
            nmplDeviceExtraInfoExample.createCriteria().andIsExistEqualTo(true).
                    andStationNetworkIdEqualTo(nmplDeviceExtraInfo.getStationNetworkId());
            List<NmplDeviceExtraInfo> nmplDeviceExtraInfos = nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);
            if(!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
                throw new SystemException("设备id重复插入");
            }
        }else {
            throw new SystemException("设备id为空");
        }
        return nmplDeviceExtraInfoMapper.insertSelective(nmplDeviceExtraInfo);
    }

    @Override
    public List<NmplDeviceExtraInfo> selectByCondition(NmplDeviceExtraInfo nmplDeviceExtraInfo) {
        NmplDeviceExtraInfoExample nmplDeviceExtraInfoExample = new NmplDeviceExtraInfoExample();
        NmplDeviceExtraInfoExample.Criteria criteria = nmplDeviceExtraInfoExample.createCriteria();
        //条件判断
        if(nmplDeviceExtraInfo.getStationNetworkId()!= null){
            criteria.andStationNetworkIdEqualTo(nmplDeviceExtraInfo.getStationNetworkId());
        }
        if(nmplDeviceExtraInfo.getDeviceName()!=null){
            criteria.andDeviceNameEqualTo(nmplDeviceExtraInfo.getDeviceName());
        }
        criteria.andIsExistEqualTo(true);
        return nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);
    }

    @Override
    public int update(NmplDeviceExtraInfo nmplDeviceExtraInfo) {
        if(nmplDeviceExtraInfo.getId() != null){
            return nmplDeviceExtraInfoMapper.updateByPrimaryKeySelective(nmplDeviceExtraInfo);
        }else {
            throw new SystemException("id字段为空");
        }
    }

    @Override
    public int delete(NmplDeviceExtraInfo nmplDeviceExtraInfo) {
        if(nmplDeviceExtraInfo.getId() != null){
            nmplDeviceExtraInfo.setIsExist(false);
            return nmplDeviceExtraInfoMapper.updateByPrimaryKeySelective(nmplDeviceExtraInfo);
        }else {
            throw new SystemException("id字段为空");
        }
    }
}























