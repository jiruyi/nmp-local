package com.matrictime.network.strategy.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.ConfigModeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.mapper.NmpStationConfMapper;
import com.matrictime.network.dao.model.NmpStationConf;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationConfVo;
import com.matrictime.network.req.ConfigReq;
import com.matrictime.network.strategy.annotation.ConfigMode;
import com.matrictime.network.strategy.service.ConfigService;
import com.matrictime.network.util.CommonCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component("stationConfig")
@ConfigMode(configMode = ConfigModeEnum.STATION_CONFIG)
@Slf4j
public class stationConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Resource
    NmpStationConfMapper nmpStationConfMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertOrUpdate(ConfigReq configReq) {
        Result result;
        try {
            checkReq(configReq);
            NmpStationConf nmpStationConf = new NmpStationConf();
            BeanUtils.copyProperties(configReq, nmpStationConf);

            List<NmpStationConf> nmpStationConfs = nmpStationConfMapper.selectByExample(null);
            Integer num;
            if (CollectionUtils.isEmpty(nmpStationConfs)) {
                num = nmpStationConfMapper.insertSelective(nmpStationConf);
            } else {
                nmpStationConf.setId(nmpStationConfs.get(0).getId());
                num = nmpStationConfMapper.updateByPrimaryKeySelective(nmpStationConf);
            }
            result = buildResult(num);
        }catch (SystemException e) {
            log.info("基站配置异常：", e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("基站配置异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result query() {
        Result result = new Result<>();
        List<NmpStationConf> nmpStationConfs = nmpStationConfMapper.selectByExample(null);
        StationConfVo stationConfVo = new StationConfVo();
        if(!CollectionUtils.isEmpty(nmpStationConfs)){
            BeanUtils.copyProperties(nmpStationConfs.get(0),stationConfVo);
        }
        result = buildResult(stationConfVo);
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
            if(!CommonCheckUtil.isIpv4Legal(configReq.getSpareCommIp())&&!CommonCheckUtil.isIpv6Legal(configReq.getSpareCommIp())){
                throw new SystemException("备用基站通信ip"+ErrorMessageContants.PARAM_FORMAT_ERROR_MSG);
            }
        }else {
            if(configReq.getSpareDomainName()==null||configReq.getSparePort()==null){
                throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
    }
}
