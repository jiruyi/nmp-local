package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.SystemHeartbeatDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceCountMapper;
import com.matrictime.network.dao.mapper.NmplSystemHeartbeatMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.SystemHeartbeatVo;
import com.matrictime.network.request.SystemHeartbeatRequest;
import com.matrictime.network.response.SystemHeartbeatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@Service
@Slf4j
public class SystemHeartbeatDomainServiceImpl implements SystemHeartbeatDomainService {

    @Resource
    private NmplSystemHeartbeatMapper nmplSystemHeartbeatMapper;

    @Resource
    private NmplBaseStationInfoMapper baseStationInfoMapper;

    @Resource
    private NmplDeviceCountMapper nmplDeviceCountMapper;

    @Override
    public int insertSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest) {
        NmplSystemHeartbeat nmplSystemHeartbeat = new NmplSystemHeartbeat();
        BeanUtils.copyProperties(systemHeartbeatRequest,nmplSystemHeartbeat);
        return nmplSystemHeartbeatMapper.insertSelective(nmplSystemHeartbeat);
    }

    @Override
    public int updateSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest) {
        NmplSystemHeartbeatExample nmplSystemHeartbeatExample = new NmplSystemHeartbeatExample();
        NmplSystemHeartbeatExample.Criteria criteria = nmplSystemHeartbeatExample.createCriteria();
        criteria.andSourceIdEqualTo(systemHeartbeatRequest.getSourceId());
        criteria.andTargetIdEqualTo(systemHeartbeatRequest.getTargetId());
        NmplSystemHeartbeat nmplSystemHeartbeat = new NmplSystemHeartbeat();
        BeanUtils.copyProperties(systemHeartbeatRequest,nmplSystemHeartbeat);
        return nmplSystemHeartbeatMapper.updateByExampleSelective(nmplSystemHeartbeat,nmplSystemHeartbeatExample);
    }

    @Override
    public SystemHeartbeatResponse selectSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest) {
        NmplSystemHeartbeatExample nmplSystemHeartbeatExample = new NmplSystemHeartbeatExample();
        SystemHeartbeatResponse systemHeartbeatResponse = new SystemHeartbeatResponse();
        List<SystemHeartbeatVo> list = new ArrayList<>();
        List<BaseStationInfoVo> baseStationInfoVoList = baseStationInfoMapper.selectAllDevice(systemHeartbeatRequest);
        if(CollectionUtils.isEmpty(baseStationInfoVoList)){
            return systemHeartbeatResponse;
        }
        List<NmplSystemHeartbeat> nmplSystemHeartbeats = nmplSystemHeartbeatMapper.selectByExample(nmplSystemHeartbeatExample);
        if(!CollectionUtils.isEmpty(nmplSystemHeartbeats)){
            for(NmplSystemHeartbeat nmplSystemHeartbeat: nmplSystemHeartbeats){
                SystemHeartbeatVo systemHeartbeatVo = new SystemHeartbeatVo();
                BeanUtils.copyProperties(nmplSystemHeartbeat,systemHeartbeatVo);
                systemHeartbeatVo.setTargetName(getTargetName(nmplSystemHeartbeat));
                systemHeartbeatVo.setSourceName(getSourceName(nmplSystemHeartbeat));
                list.add(systemHeartbeatVo);
            }
            systemHeartbeatResponse.setList(list);
            systemHeartbeatResponse.setStationInfoVoList(baseStationInfoVoList);
        }
        return systemHeartbeatResponse;
    }

    /**
     * 获取来源设备名称
     * @param nmplSystemHeartbeat
     * @return
     */
    private String getSourceName(NmplSystemHeartbeat nmplSystemHeartbeat){
        //来源设备
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
        criteria.andStationIdEqualTo(nmplSystemHeartbeat.getSourceId());
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        if(CollectionUtils.isEmpty(baseStationInfos)){
            NmplDeviceCountExample nmplDeviceCountExample = new NmplDeviceCountExample();
            NmplDeviceCountExample.Criteria criteria1 = nmplDeviceCountExample.createCriteria();
            criteria1.andDeviceIdEqualTo(nmplSystemHeartbeat.getSourceId());
            List<NmplDeviceCount> nmplDeviceCounts = nmplDeviceCountMapper.selectByExample(nmplDeviceCountExample);
            if(CollectionUtils.isEmpty(nmplDeviceCounts)){
                throw new RuntimeException("该设备不在设备列表中");
            }
            return nmplDeviceCounts.get(0).getDeviceName();
        }
        return baseStationInfos.get(0).getStationName();
    }

    /**
     * 获取目标设备名称
     * @param nmplSystemHeartbeat
     * @return
     */
    private String getTargetName(NmplSystemHeartbeat nmplSystemHeartbeat){
        //目标设备
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
        criteria.andStationIdEqualTo(nmplSystemHeartbeat.getTargetId());
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        if(CollectionUtils.isEmpty(baseStationInfos)){
            NmplDeviceCountExample nmplDeviceCountExample = new NmplDeviceCountExample();
            NmplDeviceCountExample.Criteria criteria1 = nmplDeviceCountExample.createCriteria();
            criteria1.andDeviceIdEqualTo(nmplSystemHeartbeat.getTargetId());
            List<NmplDeviceCount> nmplDeviceCounts = nmplDeviceCountMapper.selectByExample(nmplDeviceCountExample);
            if(CollectionUtils.isEmpty(nmplDeviceCounts)){
                throw new RuntimeException("该设备不在设备列表中");
            }
            return nmplDeviceCounts.get(0).getDeviceName();
        }
        return baseStationInfos.get(0).getStationName();
    }

}
