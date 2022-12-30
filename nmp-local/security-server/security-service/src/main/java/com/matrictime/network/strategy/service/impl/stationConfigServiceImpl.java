package com.matrictime.network.strategy.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.ConfigModeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.mapper.NmpStationConfMapper;
import com.matrictime.network.dao.model.NmpStationConf;
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

@Component("stationConfig")
@ConfigMode(configMode = ConfigModeEnum.STATION_CONFIG)
public class stationConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Resource
    NmpStationConfMapper nmpStationConfMapper;

    @Override
    public Result insertOrUpdate(ConfigReq configReq) {
        checkReq(configReq);
        NmpStationConf nmpStationConf = new NmpStationConf();
        BeanUtils.copyProperties(configReq,nmpStationConf);

        List<NmpStationConf> nmpStationConfs = nmpStationConfMapper.selectByExample(null);
        Integer num;
        if(CollectionUtils.isEmpty(nmpStationConfs)){
            num = nmpStationConfMapper.insertSelective(nmpStationConf);
        }else {
            nmpStationConf.setId(nmpStationConfs.get(0).getId());
            num = nmpStationConfMapper.updateByPrimaryKeySelective(nmpStationConf);
        }
        return buildResult(num);
    }

    @Override
    public Result query() {
        Result result = new Result<>();
        List<NmpStationConf> nmpStationConfs = nmpStationConfMapper.selectByExample(null);
        if(!CollectionUtils.isEmpty(nmpStationConfs)){
            result = buildResult(nmpStationConfs.get(0));
        }
        return result;
    }

    @Override
    public void checkReq(ConfigReq configReq) {
        //主基站
        if("1".equals(configReq.getMainAccessType())){
            if(configReq.getMainCommIp()==null){
                throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if(!CommonCheckUtil.isIpv4Legal(configReq.getMainCommIp())||!CommonCheckUtil.isIpv6Legal(configReq.getMainCommIp())){
                throw new SystemException("主基站通信ip"+ErrorMessageContants.PARAM_FORMAT_ERROR_MSG);
            }
        }else {
            if(configReq.getMainDomainName()==null||configReq.getMainPort()==null){
                throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }

        //备用基站
        if("1".equals(configReq.getSpareAccessType())){
            if(configReq.getSpareCommIp()==null){
                throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if(!CommonCheckUtil.isIpv4Legal(configReq.getSpareCommIp())||!CommonCheckUtil.isIpv6Legal(configReq.getSpareCommIp())){
                throw new SystemException("备用基站通信ip"+ErrorMessageContants.PARAM_FORMAT_ERROR_MSG);
            }
        }else {
            if(configReq.getSpareDomainName()==null||configReq.getSparePort()==null){
                throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
    }
}
