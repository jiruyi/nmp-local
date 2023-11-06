package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpsServerConfigMapper;
import com.matrictime.network.dao.mapper.extend.NmpsServerConfigExtMapper;
import com.matrictime.network.dao.model.NmpsServerConfig;
import com.matrictime.network.dao.model.NmpsServerConfigExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.ServerConfigVo;
import com.matrictime.network.req.ServerConfigRequest;
import com.matrictime.network.resp.ServerConfigResp;
import com.matrictime.network.service.ServerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Slf4j
@Service
public class ServerConfigServiceImpl implements ServerConfigService {

    @Resource
    private NmpsServerConfigExtMapper serverConfigExtMapper;

    @Resource
    private NmpsServerConfigMapper serverConfigMapper;

    @Override
    public Result<Integer> insertServerConfig(ServerConfigRequest serverConfigRequest) {
        Result<Integer> result = new Result<>();
        try {
            int i = 0;
            NmpsServerConfig serverConfig = new NmpsServerConfig();
            BeanUtils.copyProperties(serverConfigRequest,serverConfig);
            //构建条件
            NmpsServerConfigExample serverConfigExample = new NmpsServerConfigExample();
            NmpsServerConfigExample.Criteria criteria = serverConfigExample.createCriteria();
            criteria.andNetworkIdEqualTo(serverConfigRequest.getNetworkId());
            criteria.andConfigCodeEqualTo(serverConfigRequest.getConfigCode());
            //数据查询
            List<NmpsServerConfig> nmpsServerConfigs = serverConfigMapper.selectByExample(serverConfigExample);
            if(CollectionUtils.isEmpty(nmpsServerConfigs)){
                i = serverConfigMapper.insertSelective(serverConfig);
            }else {
                i = serverConfigMapper.updateByExampleSelective(serverConfig,serverConfigExample);
            }
            if(i == 1){
                result.setSuccess(true);
                result.setResultObj(i);
            }
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertServerConfig:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<ServerConfigResp> selectServerConfig(ServerConfigRequest serverConfigRequest) {
        Result<ServerConfigResp> result = new Result<>();
        try {
            List<ServerConfigVo> serverConfigVos = serverConfigExtMapper.selectConfig(serverConfigRequest);
            ServerConfigResp serverConfigResp = new ServerConfigResp();
            serverConfigResp.setList(serverConfigVos);
            result.setResultObj(serverConfigResp);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.error("selectServerConfig:{}",e.getMessage());
        }
        return result;
    }
}
