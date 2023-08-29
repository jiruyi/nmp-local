package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.ConfigDomainService;
import com.matrictime.network.dao.mapper.NmplConfigMapper;
import com.matrictime.network.dao.mapper.NmplReportBusinessMapper;
import com.matrictime.network.dao.model.NmplConfig;
import com.matrictime.network.dao.model.NmplConfigExample;
import com.matrictime.network.dao.model.NmplReportBusiness;
import com.matrictime.network.dao.model.NmplReportBusinessExample;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.matrictime.network.constant.BusinessConsts.COMMON_SWITCH_ON;
import static com.matrictime.network.constant.BusinessConsts.SWITCH_CONFIGCODE;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;

@Slf4j
@Service
public class ConfigDomainServiceImpl implements ConfigDomainService {

    @Autowired(required = false)
    private NmplConfigMapper nmplConfigMapper;

    @Autowired(required = false)
    private NmplReportBusinessMapper nmplReportBusinessMapper;

    /**
     * 判断业务是否上报（true:上报 false:不上报）
     * @param businessCode
     * @return
     */
    @Override
    public Boolean isReport(String businessCode) {
        Boolean result = false;
        try {
            // 校验入参
            checkIsReportParam(businessCode);
            // 查询数据采集启停开关
            NmplConfigExample collectConfig = new NmplConfigExample();
            collectConfig.createCriteria().andConfigCodeEqualTo(SWITCH_CONFIGCODE).andIsExistEqualTo(IS_EXIST);
            List<NmplConfig> collectConfigs = nmplConfigMapper.selectByExample(collectConfig);

            // 查询业务上报开关
            NmplReportBusinessExample businessConfig = new NmplReportBusinessExample();
            businessConfig.createCriteria().andBusinessCodeEqualTo(businessCode).andIsExistEqualTo(IS_EXIST);
            List<NmplReportBusiness> reportBusinesses = nmplReportBusinessMapper.selectByExample(businessConfig);
            if (CollectionUtils.isEmpty(collectConfigs)){
                throw new Exception("collectConfig"+ ErrorMessageContants.CONFIG_IS_NOT_EXIST);
            }else if (CollectionUtils.isEmpty(reportBusinesses)){
                throw new Exception("reportBusinesses"+ErrorMessageContants.CONFIG_IS_NOT_EXIST);
            }else {
                String dataValue = collectConfigs.get(0).getConfigValue();
                String businessValue = reportBusinesses.get(0).getBusinessValue();
                // 两个开关同时开启时上报
                if (COMMON_SWITCH_ON.equals(dataValue) && COMMON_SWITCH_ON.equals(businessValue)){
                    result = true;
                }
            }
        }catch (Exception e){
            log.error("ConfigDomainServiceImpl.isReport Exception:{}",e.getMessage());
        }
        return result;
    }

    private void checkIsReportParam(String businessCode) throws Exception{
        // 校验数据操作范围入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(businessCode)){
            throw new Exception("businessCode"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
