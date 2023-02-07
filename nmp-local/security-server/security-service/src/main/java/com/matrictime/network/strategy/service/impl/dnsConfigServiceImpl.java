package com.matrictime.network.strategy.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.ConfigModeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.DecimalConversionUtil;
import com.matrictime.network.dao.mapper.NmpDnsConfMapper;
import com.matrictime.network.dao.model.NmpCommAreaConf;
import com.matrictime.network.dao.model.NmpDnsConf;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DnsConfVo;
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

@Component("dnsConfig")
@ConfigMode(configMode = ConfigModeEnum.DNS_CONFIG)
@Slf4j
public class dnsConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Resource
    NmpDnsConfMapper nmpDnsConfMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertOrUpdate(ConfigReq configReq) {
        Result result;
        try {
            checkReq(configReq);
            NmpDnsConf nmpDnsConf = new NmpDnsConf();
            BeanUtils.copyProperties(configReq, nmpDnsConf);
            nmpDnsConf.setPrefixNetworkId(DecimalConversionUtil.getPreBid(DecimalConversionUtil.idToByteArray(nmpDnsConf.getNetworkId())));
            nmpDnsConf.setSuffixNetworkId(DecimalConversionUtil.getSuffBid(DecimalConversionUtil.idToByteArray(nmpDnsConf.getNetworkId())));
            List<NmpDnsConf> nmpDnsConfs = nmpDnsConfMapper.selectByExample(null);
            Integer num;
            if (CollectionUtils.isEmpty(nmpDnsConfs)) {
                num = nmpDnsConfMapper.insertSelective(nmpDnsConf);
            } else {
                nmpDnsConf.setId(nmpDnsConfs.get(0).getId());
                num = nmpDnsConfMapper.updateByPrimaryKeySelective(nmpDnsConf);
            }
            result = buildResult(num);
        }catch (SystemException e) {
            log.info("DNS配置异常：", e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("DNS配置异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result query() {
        Result result = new Result<>();
        List<NmpDnsConf> nmpDnsConfs = nmpDnsConfMapper.selectByExample(null);
        DnsConfVo dnsConfVo = new DnsConfVo();
        if(!CollectionUtils.isEmpty(nmpDnsConfs)){
          BeanUtils.copyProperties(nmpDnsConfs.get(0),dnsConfVo);
        }
        result = buildResult(dnsConfVo);
        return result;
    }

    @Override
    public void checkReq(ConfigReq configReq) {
        if(configReq.getNetworkId()==null||configReq.getSecretIp()==null||configReq.getCommIp()==null){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if(!CommonCheckUtil.isIpv4Legal(configReq.getSecretIp())&&!CommonCheckUtil.isIpv6Legal(configReq.getSecretIp())){
            throw new SystemException("隐私ip"+ErrorMessageContants.PARAM_FORMAT_ERROR_MSG);
        }
        if(!CommonCheckUtil.isIpv4Legal(configReq.getCommIp())&&!CommonCheckUtil.isIpv6Legal(configReq.getCommIp())){
            throw new SystemException("通信ip"+ErrorMessageContants.PARAM_FORMAT_ERROR_MSG);
        }
    }
}
