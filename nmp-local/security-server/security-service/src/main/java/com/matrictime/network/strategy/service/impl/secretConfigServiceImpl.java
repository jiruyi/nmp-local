package com.matrictime.network.strategy.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.ConfigModeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.mapper.NmpSecretAreaConfMapper;
import com.matrictime.network.dao.model.NmpDnsConf;
import com.matrictime.network.dao.model.NmpSecretAreaConf;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.req.ConfigReq;
import com.matrictime.network.strategy.annotation.ConfigMode;
import com.matrictime.network.strategy.service.ConfigService;
import com.matrictime.network.util.CommonCheckUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component("secretConfig")
@ConfigMode(configMode = ConfigModeEnum.SECRET_CONFIG)
public class secretConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Resource
    NmpSecretAreaConfMapper nmpSecretAreaConfMapper;

    @Override
    public Result insertOrUpdate(ConfigReq configReq) {
        checkReq(configReq);
        NmpSecretAreaConf nmpSecretAreaConf = new NmpSecretAreaConf();
        BeanUtils.copyProperties(configReq,nmpSecretAreaConf);

        List<NmpSecretAreaConf> nmpSecretAreaConfs = nmpSecretAreaConfMapper.selectByExample(null);
        Integer num;
        if(CollectionUtils.isEmpty(nmpSecretAreaConfs)){
            num = nmpSecretAreaConfMapper.insertSelective(nmpSecretAreaConf);
        }else {
            nmpSecretAreaConf.setId(nmpSecretAreaConfs.get(0).getId());
            num = nmpSecretAreaConfMapper.updateByPrimaryKeySelective(nmpSecretAreaConf);
        }
        return buildResult(num);
    }

    @Override
    public Result query() {
        Result result = new Result<>();
        List<NmpSecretAreaConf> nmpSecretAreaConfs = nmpSecretAreaConfMapper.selectByExample(null);
        if(!CollectionUtils.isEmpty(nmpSecretAreaConfs)){
            result = buildResult(nmpSecretAreaConfs.get(0));
        }
        return result;
    }

    @Override
    public void checkReq(ConfigReq configReq) {
        if(configReq.getInputIp()==null||configReq.getDeviceName()==null){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if(!CommonCheckUtil.isIpv4Legal(configReq.getInputIp())||!CommonCheckUtil.isIpv6Legal(configReq.getInputIp())){
            throw new SystemException("通信ip"+ErrorMessageContants.PARAM_FORMAT_ERROR_MSG);
        }
    }



    public Result getIP(){
        return buildResult("");
    }
}
