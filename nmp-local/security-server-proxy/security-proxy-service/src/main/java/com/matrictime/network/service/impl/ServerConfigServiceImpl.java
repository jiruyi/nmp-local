package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpsServerConfigMapper;
import com.matrictime.network.dao.model.NmpsDnsManage;
import com.matrictime.network.dao.model.NmpsServerConfig;
import com.matrictime.network.dao.model.NmpsServerConfigExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.ServerConfigVo;
import com.matrictime.network.service.ServerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/6.
 */
@Service
@Slf4j
public class ServerConfigServiceImpl implements ServerConfigService {

    @Resource
    private NmpsServerConfigMapper serverConfigMapper;

    @Override
    public Result<Integer> insertConfig(ServerConfigVo serverConfigVo) {
        Result<Integer> result = new Result<>();
        int update = 0;
        try {
            NmpsServerConfig serverConfig = new NmpsServerConfig();
            BeanUtils.copyProperties(serverConfigVo,serverConfig);
            serverConfig.setIsExist(true);
            NmpsServerConfigExample serverConfigExample = new NmpsServerConfigExample();
            NmpsServerConfigExample.Criteria criteria = serverConfigExample.createCriteria();
            criteria.andConfigCodeEqualTo(serverConfigVo.getConfigCode());
            criteria.andIsExistEqualTo(true);
            update = serverConfigMapper.updateByExampleSelective(serverConfig,serverConfigExample);
            result.setSuccess(true);
            result.setResultObj(update);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertConfig: {}",e.getMessage());
        }
        return result;
    }
}
