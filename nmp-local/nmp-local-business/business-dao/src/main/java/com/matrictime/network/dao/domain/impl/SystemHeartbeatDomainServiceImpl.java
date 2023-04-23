package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.SystemHeartbeatDomainService;
import com.matrictime.network.dao.mapper.NmplSystemHeartbeatMapper;
import com.matrictime.network.dao.model.NmplSystemHeartbeat;
import com.matrictime.network.dao.model.NmplSystemHeartbeatExample;
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
        NmplSystemHeartbeatExample.Criteria criteria = nmplSystemHeartbeatExample.createCriteria();
        if(!StringUtils.isEmpty(systemHeartbeatRequest.getSourceId())){
            criteria.andSourceIdEqualTo(systemHeartbeatRequest.getSourceId());
        }
        if(!StringUtils.isEmpty(systemHeartbeatRequest.getTargetId())){
            criteria.andTargetIdEqualTo(systemHeartbeatRequest.getTargetId());
        }
        SystemHeartbeatResponse systemHeartbeatResponse = new SystemHeartbeatResponse();
        List<SystemHeartbeatVo> list = new ArrayList<>();
        List<NmplSystemHeartbeat> nmplSystemHeartbeats = nmplSystemHeartbeatMapper.selectByExample(nmplSystemHeartbeatExample);
        if(!CollectionUtils.isEmpty(nmplSystemHeartbeats)){
            for(NmplSystemHeartbeat nmplSystemHeartbeat: nmplSystemHeartbeats){
                SystemHeartbeatVo systemHeartbeatVo = new SystemHeartbeatVo();
                BeanUtils.copyProperties(nmplSystemHeartbeat,systemHeartbeatVo);
                list.add(systemHeartbeatVo);
            }
            systemHeartbeatResponse.setList(list);
        }
        return systemHeartbeatResponse;
    }


}
