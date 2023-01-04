package com.matrictime.network.strategy.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.ConfigModeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.mapper.NmpCommAreaConfMapper;
import com.matrictime.network.dao.model.NmpCommAreaConf;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CommAreaConfVo;
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

@Component("commConfig")
@ConfigMode(configMode = ConfigModeEnum.COMM_CONFIG)
@Slf4j
public class commConfigServiceImpl extends SystemBaseService implements ConfigService {


    @Resource
    NmpCommAreaConfMapper nmpCommAreaConfMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertOrUpdate(ConfigReq configReq) {
        Result result;
        try {
            checkReq(configReq);
            NmpCommAreaConf nmpCommAreaConf = new NmpCommAreaConf();
            BeanUtils.copyProperties(configReq, nmpCommAreaConf);

            List<NmpCommAreaConf> nmpCommAreaConfs = nmpCommAreaConfMapper.selectByExample(null);
            Integer num;
            if (CollectionUtils.isEmpty(nmpCommAreaConfs)) {
                num = nmpCommAreaConfMapper.insertSelective(nmpCommAreaConf);
            } else {
                nmpCommAreaConf.setId(nmpCommAreaConfs.get(0).getId());
                num = nmpCommAreaConfMapper.updateByPrimaryKeySelective(nmpCommAreaConf);
            }
            result = buildResult(num);
        }catch (SystemException e) {
            log.info("通信配置异常：", e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("通信配置异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result query() {
        Result result = new Result<>();
        List<NmpCommAreaConf> nmpCommAreaConfs = nmpCommAreaConfMapper.selectByExample(null);
        CommAreaConfVo commAreaConfVo = new CommAreaConfVo();
        if(!CollectionUtils.isEmpty(nmpCommAreaConfs)){
           BeanUtils.copyProperties(nmpCommAreaConfs.get(0),commAreaConfVo);
        }
        result = buildResult(commAreaConfVo);
        return result;
    }

    @Override
    public void checkReq(ConfigReq configReq) {
        if(configReq.getPort()==null||configReq.getCommIp()==null){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if(!CommonCheckUtil.isIpv4Legal(configReq.getCommIp())&&!CommonCheckUtil.isIpv6Legal(configReq.getCommIp())){
            throw new SystemException("通信ip"+ErrorMessageContants.PARAM_FORMAT_ERROR_MSG);
        }
    }
}
