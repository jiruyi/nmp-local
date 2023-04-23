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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/21.
 */
@Service
@Slf4j
public class SystemHeartbeatDomainServiceImpl implements SystemHeartbeatDomainService {

    @Resource
    private NmplSystemHeartbeatMapper systemHeartbeatMapper;

    @Override
    public SystemHeartbeatResponse selectSystemHeartbeat() {
        NmplSystemHeartbeatExample systemHeartbeatExample = new NmplSystemHeartbeatExample();
        SystemHeartbeatResponse systemHeartbeatResponse = new SystemHeartbeatResponse();
        List<SystemHeartbeatVo> list = new ArrayList<>();
        List<NmplSystemHeartbeat> nmplSystemHeartbeats = systemHeartbeatMapper.selectByExample(systemHeartbeatExample);
        if(!CollectionUtils.isEmpty(nmplSystemHeartbeats)){
            for(NmplSystemHeartbeat nmplSystemHeartbeat: nmplSystemHeartbeats){
                SystemHeartbeatVo systemHeartbeatVo = new SystemHeartbeatVo();
                BeanUtils.copyProperties(nmplSystemHeartbeat,systemHeartbeatVo);
                list.add(systemHeartbeatVo);
            }
        }
        systemHeartbeatResponse.setList(list);
        return systemHeartbeatResponse;
    }
}
