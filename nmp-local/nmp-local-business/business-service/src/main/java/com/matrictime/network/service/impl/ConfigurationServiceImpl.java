package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.ConfigurationDomainService;
import com.matrictime.network.dao.mapper.NmplConfigMapper;
import com.matrictime.network.dao.mapper.NmplDeviceMapper;
import com.matrictime.network.dao.mapper.NmplReportBusinessMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.request.ConfigurationReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.ConfigurationService;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.base.exception.ErrorMessageContants.*;
import static com.matrictime.network.constant.BusinessConsts.*;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;

@Service
@Slf4j
public class ConfigurationServiceImpl extends SystemBaseService implements ConfigurationService {

    @Autowired
    ConfigurationDomainService configurationDomainService;
    @Autowired(required = false)
    private NmplReportBusinessMapper nmplReportBusinessMapper;
    @Autowired
    private NmplConfigMapper nmplConfigMapper;
    @Resource
    private NmplDeviceMapper nmplDeviceMapper;



    @Override
    public Result insertOrUpdate(ConfigurationReq configurationReq) {
        Result result = null;
        try {
            result = buildResult(configurationDomainService.insertOrUpdate(configurationReq));
        }catch (Exception e){
            log.error("设备配置信息更新异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result query(ConfigurationReq configurationReq) {
        Result result = null;
        try {
            result = buildResult(configurationDomainService.queryByCondition(configurationReq));
        }catch (Exception e){
            log.error("设备配置信息查询异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }


    @Override
    public Result reportBusinessData() {
        Result result = new Result<>();
        try {
            NmplConfigExample collectConfig = new NmplConfigExample();
            collectConfig.createCriteria().andConfigCodeEqualTo(SWITCH_CONFIGCODE).andIsExistEqualTo(IS_EXIST).andConfigValueEqualTo(COMMON_SWITCH_ON);
            List<NmplConfig> collectConfigs = nmplConfigMapper.selectByExample(collectConfig);
            NmplReportBusinessExample nmplReportBusinessExample=  new NmplReportBusinessExample();
            nmplReportBusinessExample.createCriteria().andBusinessValueEqualTo(COMMON_SWITCH_ON);
            List<NmplReportBusiness> nmplReportBusinesses = nmplReportBusinessMapper.selectByExample(nmplReportBusinessExample);
            //当上报项为空 或者禁止上报时 无法手动上报
            if(CollectionUtils.isEmpty(nmplReportBusinesses)||CollectionUtils.isEmpty(collectConfigs)){
                throw new SystemException(PROHIBIT_REPORT);
            }
            //查询数据采集信息
            NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
            nmplDeviceExample.createCriteria().andDeviceTypeEqualTo(String.valueOf(DeviceTypeEnum.DATA_BASE)).andIsExistEqualTo(true);
            List<NmplDevice> nmplDeviceList = nmplDeviceMapper.selectByExample(nmplDeviceExample);
            if(nmplDeviceList.isEmpty()){
                throw new SystemException(DATACOLLECT_NOT_EXIST);
            }
            List<String> ipList=  new ArrayList<>();
            for (NmplDevice nmplDevice : nmplDeviceList) {
                ipList.add(nmplDevice.getLanIp());
            }

            List<String> codeList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject();
            //根据业务code来上报对应的服务
            for (NmplReportBusiness nmplReportBusiness : nmplReportBusinesses) {
                codeList.add(nmplReportBusiness.getBusinessCode());
            }
            jsonObject.put("codeList",codeList);
            for (String ip : ipList) {
                //发送指令到数据采集
                String url = HTTP_TITLE+ip+KEY_SPLIT+COLLEECT_REPORT_URL;
                String postResp = HttpClientUtil.post(url,jsonObject.toJSONString());
                JSONObject json = JSONObject.parseObject(postResp);
                if (json != null) {
                    Object success = json.get(KEY_SUCCESS);
                    if (success != null && success instanceof Boolean) {
                        if (!(Boolean) success) {
                            throw new SystemException(REPORT_ERROR);
                        }
                    }
                }
            }
            result = buildResult("");
        }catch (SystemException e){
            log.error("手动上报业务数据异常：",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("手动上报业务数据异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }
}
