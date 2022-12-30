package com.matrictime.network.strategy.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.ConfigModeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.mapper.NmpCaConfMapper;
import com.matrictime.network.dao.model.NmpCaConf;
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

@Component("caConfig")
@ConfigMode(configMode = ConfigModeEnum.CA_CONFIG)
public class caConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Resource
    NmpCaConfMapper nmpCaConfMapper;


    @Override
    public Result insertOrUpdate(ConfigReq configReq) {
        checkReq(configReq);
        NmpCaConf nmpCaConf = new NmpCaConf();
        BeanUtils.copyProperties(configReq,nmpCaConf);
        List<NmpCaConf> nmpCaConfs = nmpCaConfMapper.selectByExample(null);
        Integer num;
        if(CollectionUtils.isEmpty(nmpCaConfs)){
            num = nmpCaConfMapper.insertSelective(nmpCaConf);
        }else {
            nmpCaConf.setId(nmpCaConfs.get(0).getId());
            num = nmpCaConfMapper.updateByPrimaryKeySelective(nmpCaConf);
        }
        return buildResult(num);
    }

    @Override
    public Result query() {
        Result result = new Result<>();
        List<NmpCaConf> nmpCaConfs = nmpCaConfMapper.selectByExample(null);
        if(!CollectionUtils.isEmpty(nmpCaConfs)){
            result = buildResult(nmpCaConfs.get(0));
        }
        return result;
    }

    @Override
    public void checkReq(ConfigReq configReq) {
        if(("1").equals(configReq.getAccessType())){
            //固定接入
            if(configReq.getNetworkId()==null||configReq.getSecretIp()==null||configReq.getCommIp()==null){
                throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if(!CommonCheckUtil.isIpv4Legal(configReq.getSecretIp())||!CommonCheckUtil.isIpv6Legal(configReq.getSecretIp())){
                throw new SystemException("隐私ip"+ErrorMessageContants.PARAM_FORMAT_ERROR_MSG);
            }
            if(!CommonCheckUtil.isIpv4Legal(configReq.getCommIp())||!CommonCheckUtil.isIpv6Legal(configReq.getCommIp())){
                throw new SystemException("通信ip"+ErrorMessageContants.PARAM_FORMAT_ERROR_MSG);
            }
        }else {
            //动态接入
            if(configReq.getDomainName()==null||configReq.getPort()==null){
                throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
    }
}
