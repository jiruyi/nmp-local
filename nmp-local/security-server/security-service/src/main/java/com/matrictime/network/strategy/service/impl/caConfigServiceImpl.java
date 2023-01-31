package com.matrictime.network.strategy.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.ConfigModeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.DecimalConversionUtil;
import com.matrictime.network.dao.mapper.NmpCaConfMapper;
import com.matrictime.network.dao.model.NmpCaConf;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CaConfVo;
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

@Component("caConfig")
@ConfigMode(configMode = ConfigModeEnum.CA_CONFIG)
@Slf4j
public class caConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Resource
    NmpCaConfMapper nmpCaConfMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertOrUpdate(ConfigReq configReq) {
        Result result;
        try {
            checkReq(configReq);
            NmpCaConf nmpCaConf = new NmpCaConf();
            BeanUtils.copyProperties(configReq,nmpCaConf);
            if(nmpCaConf.getNetworkId()!=null){
                nmpCaConf.setPrefixNetworkId(DecimalConversionUtil.getPreBid(DecimalConversionUtil.idToByteArray(nmpCaConf.getNetworkId())));
                nmpCaConf.setSuffixNetworkId(DecimalConversionUtil.getSuffBid(DecimalConversionUtil.idToByteArray(nmpCaConf.getNetworkId())));
            }
            List<NmpCaConf> nmpCaConfs = nmpCaConfMapper.selectByExample(null);
            Integer num;
            if(CollectionUtils.isEmpty(nmpCaConfs)){
                num = nmpCaConfMapper.insertSelective(nmpCaConf);
            }else {
                nmpCaConf.setId(nmpCaConfs.get(0).getId());
                num = nmpCaConfMapper.updateByPrimaryKeySelective(nmpCaConf);
            }
            result = buildResult(num);
        }catch (SystemException e) {
            log.info("CA配置异常：", e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("CA配置异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result query() {
        Result result = new Result<>();
        List<NmpCaConf> nmpCaConfs = nmpCaConfMapper.selectByExample(null);
        CaConfVo caConfVo = new CaConfVo();
        if(!CollectionUtils.isEmpty(nmpCaConfs)){
            BeanUtils.copyProperties(nmpCaConfs.get(DataConstants.ZERO),caConfVo);
        }
        result = buildResult(caConfVo);
        return result;
    }

    @Override
    public void checkReq(ConfigReq configReq) {
        if(("1").equals(configReq.getAccessType())){
            //固定接入
            if(configReq.getNetworkId()==null||configReq.getSecretIp()==null||configReq.getCommIp()==null){
                throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if(!CommonCheckUtil.isIpv4Legal(configReq.getSecretIp())&&!CommonCheckUtil.isIpv6Legal(configReq.getSecretIp())){
                throw new SystemException("隐私ip"+ErrorMessageContants.PARAM_FORMAT_ERROR_MSG);
            }
            if(!CommonCheckUtil.isIpv4Legal(configReq.getCommIp())&&!CommonCheckUtil.isIpv6Legal(configReq.getCommIp())){
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
