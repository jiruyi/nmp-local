package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.SystemHeartbeatDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceCountMapper;
import com.matrictime.network.dao.mapper.NmplSystemHeartbeatMapper;
import com.matrictime.network.dao.mapper.extend.NmplSystemHeartbeatExtMapper;
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

    @Resource
    private NmplSystemHeartbeatExtMapper systemHeartbeatExtMapper;

    @Override
    public int insertSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest) {
        NmplSystemHeartbeat nmplSystemHeartbeat = new NmplSystemHeartbeat();
        BeanUtils.copyProperties(systemHeartbeatRequest,nmplSystemHeartbeat);
        nmplSystemHeartbeat.setId(null);
        return nmplSystemHeartbeatMapper.insertSelective(nmplSystemHeartbeat);
    }

    /**
     * 更新业务心跳
     * @param systemHeartbeatRequest
     * @return
     */
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

    /**
     * 查询心跳状态
     * @param systemHeartbeatRequest
     * @return
     */
    @Override
    public SystemHeartbeatResponse selectSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest) {
        NmplSystemHeartbeatExample nmplSystemHeartbeatExample = new NmplSystemHeartbeatExample();
        NmplSystemHeartbeatExample.Criteria criteria = nmplSystemHeartbeatExample.createCriteria();
        SystemHeartbeatResponse systemHeartbeatResponse = new SystemHeartbeatResponse();
        List<SystemHeartbeatVo> list = new ArrayList<>();
        List<BaseStationInfoVo> baseStationInfoVoList = baseStationInfoMapper.selectAllDevice(systemHeartbeatRequest);
        if(!CollectionUtils.isEmpty(baseStationInfoVoList)){
            systemHeartbeatResponse.setStationInfoVoList(baseStationInfoVoList);
        }
        //查询业务心跳表中的数据
        if(!StringUtils.isEmpty(systemHeartbeatRequest.getSourceId())){
            criteria.andSourceIdEqualTo(systemHeartbeatRequest.getSourceId());
        }
        if(!StringUtils.isEmpty(systemHeartbeatRequest.getTargetId())){
            criteria.andTargetIdEqualTo(systemHeartbeatRequest.getTargetId());
        }
        //List<NmplSystemHeartbeat> nmplSystemHeartbeats = nmplSystemHeartbeatMapper.selectByExample(nmplSystemHeartbeatExample);
        List<SystemHeartbeatVo> systemHeartbeatVoList = systemHeartbeatExtMapper.selectSystemHeartbeat(systemHeartbeatRequest);

        if(!CollectionUtils.isEmpty(systemHeartbeatVoList)){
            for(SystemHeartbeatVo nmplSystemHeartbeat: systemHeartbeatVoList){
                nmplSystemHeartbeat.setTargetName(getTargetName(nmplSystemHeartbeat));
                nmplSystemHeartbeat.setSourceName(getSourceName(nmplSystemHeartbeat));
                nmplSystemHeartbeat.setSourceId(changeNetworkId(nmplSystemHeartbeat.getSourceId()));
                nmplSystemHeartbeat.setTargetId(changeNetworkId(nmplSystemHeartbeat.getTargetId()));
                list.add(nmplSystemHeartbeat);
            }
            systemHeartbeatResponse.setList(list);

        }
        return systemHeartbeatResponse;
    }

    /**
     * 获取来源设备名称
     * @param nmplSystemHeartbeat
     * @return
     */
    private String getSourceName(SystemHeartbeatVo nmplSystemHeartbeat){
        //来源设备
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
        criteria.andStationNetworkIdEqualTo(changeNetworkId(nmplSystemHeartbeat.getSourceId()));
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        if(CollectionUtils.isEmpty(baseStationInfos)){
            NmplDeviceCountExample nmplDeviceCountExample = new NmplDeviceCountExample();
            NmplDeviceCountExample.Criteria criteria1 = nmplDeviceCountExample.createCriteria();
            criteria1.andStationNetworkIdEqualTo(changeNetworkId(nmplSystemHeartbeat.getSourceId()));
            List<NmplDeviceCount> nmplDeviceCounts = nmplDeviceCountMapper.selectByExample(nmplDeviceCountExample);
            if(CollectionUtils.isEmpty(nmplDeviceCounts)){
                return "";
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
    private String getTargetName(SystemHeartbeatVo nmplSystemHeartbeat){
        //目标设备
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
        criteria.andStationNetworkIdEqualTo(changeNetworkId(nmplSystemHeartbeat.getTargetId()));
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        if(CollectionUtils.isEmpty(baseStationInfos)){
            NmplDeviceCountExample nmplDeviceCountExample = new NmplDeviceCountExample();
            NmplDeviceCountExample.Criteria criteria1 = nmplDeviceCountExample.createCriteria();
            criteria1.andStationNetworkIdEqualTo(changeNetworkId(nmplSystemHeartbeat.getTargetId()));
            List<NmplDeviceCount> nmplDeviceCounts = nmplDeviceCountMapper.selectByExample(nmplDeviceCountExample);
            if(CollectionUtils.isEmpty(nmplDeviceCounts)){
                return "";
            }
            return nmplDeviceCounts.get(0).getDeviceName();
        }
        return baseStationInfos.get(0).getStationName();
    }

    /**
     * 入网id1 16转10 进制
     * @param networkId
     * @return
     */
    private String changeNetworkId(String networkId){
        String[] split = networkId.split("-");
        String networkStr = "";
        for(int i = 0; i <= 4;i++){
            Integer change = Integer.parseInt(split[i],16);
            networkStr = networkStr + change + "-";
        }
        return networkStr.substring(0,networkStr.length() - 1);
    }

}
