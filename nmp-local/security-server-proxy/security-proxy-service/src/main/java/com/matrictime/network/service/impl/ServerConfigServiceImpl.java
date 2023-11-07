package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpsServerConfigMapper;
import com.matrictime.network.dao.model.NmpsDnsManage;
import com.matrictime.network.dao.model.NmpsServerConfig;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.ServerConfigVo;
import com.matrictime.network.service.ServerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        try {
            NmpsServerConfig serverConfig = new NmpsServerConfig();
            BeanUtils.copyProperties(serverConfigVo,serverConfig);
            serverConfig.setIsExist(true);
            int insert = serverConfigMapper.insert(serverConfig);
            result.setSuccess(true);
            result.setResultObj(insert);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertConfig: {}",e.getMessage());
        }
        return result;
    }
}
