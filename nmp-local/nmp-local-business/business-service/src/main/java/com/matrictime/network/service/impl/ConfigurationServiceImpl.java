package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.ConfigurationDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.request.ConfigurationReq;
import com.matrictime.network.request.EditDataCollectConfigReq;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.base.exception.ErrorMessageContants.*;
import static com.matrictime.network.constant.BusinessConsts.*;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;

@Service
@Slf4j
public class ConfigurationServiceImpl extends SystemBaseService implements ConfigurationService {

    @Resource
    ConfigurationDomainService configurationDomainService;
    @Resource
    private NmplReportBusinessMapper nmplReportBusinessMapper;
    @Resource
    private NmplConfigMapper nmplConfigMapper;
    @Resource
    private NmplDeviceMapper nmplDeviceMapper;

    @Resource
    private NmplDeviceConfigMapper nmplDeviceConfigMapper;

    @Resource
    private NmplDeviceReportBusinessMapper nmplDeviceReportBusinessMapper;



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
    public Result reportBusinessData(EditDataCollectConfigReq req) {
        Result result = new Result<>();
        try {
            //查询数据采集信息
            NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
            NmplDeviceExample.Criteria criteria = nmplDeviceExample.createCriteria();
            criteria.andDeviceTypeEqualTo(String.valueOf(DeviceTypeEnum.DATA_BASE.getCode())).andIsExistEqualTo(true);
            List<NmplDevice> nmplDeviceList = nmplDeviceMapper.selectByExample(nmplDeviceExample);
            List<Result> list = new ArrayList<>();
            for (NmplDevice nmplDevice : nmplDeviceList) {
                EditDataCollectConfigReq request = new EditDataCollectConfigReq();
                request.setDeviceId(nmplDevice.getDeviceId());
                request.setIp(nmplDevice.getLanIp());
                list.add(reportDeviceBusinessData(request));
            }
            result = buildResult(list);
        }catch (SystemException e){
            log.error("手动上报业务数据异常：",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("手动上报业务数据异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    @Override
    public Result reportDeviceBusinessData(EditDataCollectConfigReq req) {
        Result result = new Result<>();
        try {
            if(!canReport(req.getDeviceId())){
                throw new SystemException("配置禁用无法上报");
            }

            NmplReportBusinessExample nmplReportBusinessExample = new NmplReportBusinessExample();
            List<NmplReportBusiness> nmplReportBusinesses = nmplReportBusinessMapper.selectByExample(nmplReportBusinessExample);

            NmplDeviceReportBusinessExample nmplDeviceReportBusinessExample = new NmplDeviceReportBusinessExample();
            nmplDeviceReportBusinessExample.createCriteria().andDeviceIdEqualTo(req.getDeviceId()).andIsExistEqualTo(true).andBusinessValueIsNotNull();
            List<NmplDeviceReportBusiness> nmplDeviceReportBusinesses = nmplDeviceReportBusinessMapper.selectByExample(nmplDeviceReportBusinessExample);
            Map<String, String> map = new HashMap<>();
            for (NmplDeviceReportBusiness nmplDeviceReportBusiness : nmplDeviceReportBusinesses) {
                map.put(nmplDeviceReportBusiness.getBusinessCode(), nmplDeviceReportBusiness.getBusinessValue());
            }

            List<String> codeList = new ArrayList<>();
            for (NmplReportBusiness nmplReportBusiness : nmplReportBusinesses) {
                //当有设备配置 且设备配置不为null时 取配置值
                if (map.containsKey(nmplReportBusiness.getBusinessCode())&&map.get(nmplReportBusiness.getBusinessCode())!=null) {
                    if (map.get(nmplReportBusiness.getBusinessCode()).equals(COMMON_SWITCH_ON)) {
                        codeList.add(nmplReportBusiness.getBusinessCode());
                    }
                } else {
                    if (nmplReportBusiness.getBusinessValue().equals(COMMON_SWITCH_ON)) {
                        codeList.add(nmplReportBusiness.getBusinessCode());
                    }
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codeList", codeList);
            httpPush(req.getIp(), jsonObject.toJSONString());
            result = buildResult("");
        } catch (SystemException e) {
            log.error("手动上报业务数据异常：", e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.error("手动上报业务数据异常：", e.getMessage());
            result = failResult("");
        }
        return result;
    }


    /**
     * 判定能否上报
     * @param deviceId
     * @return
     */
    private Boolean canReport(String deviceId){
        NmplConfigExample collectConfig = new NmplConfigExample();
        collectConfig.createCriteria().andConfigCodeEqualTo(SWITCH_CONFIGCODE).andIsExistEqualTo(IS_EXIST);
        List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(collectConfig);
        if (CollectionUtils.isEmpty(nmplConfigs)) {
            throw new SystemException("数据采集启停配置异常");
        }

        //获取单设备的配置信息
        NmplDeviceConfigExample nmplDeviceConfigExample = new NmplDeviceConfigExample();
        nmplDeviceConfigExample.createCriteria().andDeviceIdEqualTo(deviceId).andConfigCodeEqualTo(SWITCH_CONFIGCODE);
        List<NmplDeviceConfig> nmplDeviceConfigs = nmplDeviceConfigMapper.selectByExample(nmplDeviceConfigExample);

        if(nmplDeviceConfigs.isEmpty()){
            //单设备无配置 取默认配置
            return nmplConfigs.get(0).getConfigValue().equals(COMMON_SWITCH_ON);
        }else if(nmplDeviceConfigs.get(0).getConfigValue()!=null) {
            //单设备有配置 取设备配置
            return nmplDeviceConfigs.get(0).getConfigValue().equals(COMMON_SWITCH_ON);
        }else {
            //单设备无配置 取默认配置
            return nmplConfigs.get(0).getConfigValue().equals(COMMON_SWITCH_ON);
        }
    }



    private void httpPush(String ip, String data)throws Exception{
        //发送指令到数据采集
        String url = HTTP_TITLE+ip+KEY_SPLIT+COLLEECT_REPORT_URL;
        String postResp = HttpClientUtil.post(url,data);
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


}
