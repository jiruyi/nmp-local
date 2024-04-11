package com.matrictime.network.dao.domain.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.domain.ConfigDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.dao.model.extend.ReportExt;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.util.ParamCheckUtil;
import com.matrictime.network.util.SystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

import static com.matrictime.network.constant.BusinessConsts.COMMON_SWITCH_ON;
import static com.matrictime.network.constant.BusinessConsts.SWITCH_CONFIGCODE;
import static com.matrictime.network.constant.DataConstants.*;

@Slf4j
@Service
public class ConfigDomainServiceImpl implements ConfigDomainService {

    @Resource
    private NmplConfigMapper nmplConfigMapper;

    @Resource
    private NmplReportBusinessMapper nmplReportBusinessMapper;

    @Resource
    private NmplDeviceInfoMapper deviceInfoMapper;

    @Resource
    private NmplDeviceConfigMapper nmplDeviceConfigMapper;

    @Resource
    private NmplDeviceReportBusinessMapper nmplDeviceReportBusinessMapper;

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

            // 通过ip获取本机deviceId
            Set<String> localIps = SystemUtils.getLocalIp4Address();
            NmplDeviceInfoExample example = new NmplDeviceInfoExample();
            example.createCriteria().andLanIpIn(new ArrayList<>(localIps)).andDeviceTypeEqualTo(DeviceTypeEnum.DATA_BASE.getCode()).andIsExistEqualTo(IS_EXIST);
            List<NmplDeviceInfo> deviceInfos = deviceInfoMapper.selectByExample(example);
            log.info("通过ip获取本机deviceId:url{},deviceInfo{}",JSONObject.toJSONString(localIps),JSONObject.toJSONString(deviceInfos));
            if (CollectionUtils.isEmpty(deviceInfos)){
                throw new Exception("本机信息获取异常");
            }
            String deviceId = deviceInfos.get(0).getDeviceId();
            // 查询数据采集启停开关
            String switchValue = getSwitch(deviceId);

            // 查询业务上报开关
            ReportExt reportSwitch = getReportSwitch(deviceId, businessCode);
            String reportValue = reportSwitch.getReportValue();
            if (ParamCheckUtil.checkVoStrBlank(switchValue)){
                throw new Exception("switchValue"+ ErrorMessageContants.CONFIG_IS_NOT_EXIST);
            }else if (ParamCheckUtil.checkVoStrBlank(reportValue)){
                throw new Exception("reportValue"+ErrorMessageContants.CONFIG_IS_NOT_EXIST);
            }else {
                // 两个开关同时开启时上报
                if (COMMON_SWITCH_ON.equals(switchValue) && COMMON_SWITCH_ON.equals(reportValue)){
                    result = true;
                    Date reportTime = new Date();
                    // 更新上报时间
                    NmplReportBusiness reportBusiness = new NmplReportBusiness();
                    reportBusiness.setId(reportSwitch.getMainId());
                    reportBusiness.setReportTime(reportTime);
                    nmplReportBusinessMapper.updateByPrimaryKeySelective(reportBusiness);

                    if (EDIT_RANGE_PART.equals(reportSwitch.getRange())){
                        NmplDeviceReportBusiness deviceReportBusiness = new NmplDeviceReportBusiness();
                        deviceReportBusiness.setId(reportSwitch.getPartId());
                        deviceReportBusiness.setReportTime(reportTime);
                        nmplDeviceReportBusinessMapper.updateByPrimaryKeySelective(deviceReportBusiness);
                    }
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

    /**
     * 获取数据采集启停开关
     * @param deviceId
     * @return
     */
    private String getSwitch(String deviceId){
        String res = null;

        // 查询网元参数配置是否为空，不为空直接返回网元设置值
        NmplDeviceConfigExample deviceConfigExample = new NmplDeviceConfigExample();
        deviceConfigExample.createCriteria().andDeviceIdEqualTo(deviceId).andConfigCodeEqualTo(SWITCH_CONFIGCODE).andIsExistEqualTo(IS_EXIST);
        List<NmplDeviceConfig> deviceConfigs = nmplDeviceConfigMapper.selectByExample(deviceConfigExample);
        if (!CollectionUtils.isEmpty(deviceConfigs)){
            res = deviceConfigs.get(0).getConfigValue();
            return res;
        }

        NmplConfigExample collectConfig = new NmplConfigExample();
        collectConfig.createCriteria().andConfigCodeEqualTo(SWITCH_CONFIGCODE).andIsExistEqualTo(IS_EXIST);
        List<NmplConfig> collectConfigs = nmplConfigMapper.selectByExample(collectConfig);
        if (!CollectionUtils.isEmpty(collectConfigs)){
            if (!ParamCheckUtil.checkVoStrBlank(collectConfigs.get(0).getConfigValue())){
                res = collectConfigs.get(0).getConfigValue();
            }else {
                res = collectConfigs.get(0).getDefaultValue();
            }
        }
        return res;
    }

    /**
     * 获取业务上报开关
     * @param deviceId
     * @return
     */
    private ReportExt getReportSwitch(String deviceId, String businessCode){
        ReportExt res = new ReportExt();
        NmplDeviceReportBusinessExample deviceReportBusinessExample = new NmplDeviceReportBusinessExample();
        deviceReportBusinessExample.createCriteria().andDeviceIdEqualTo(deviceId).andBusinessCodeEqualTo(businessCode).andIsExistEqualTo(IS_EXIST);
        List<NmplDeviceReportBusiness> deviceReportBusinesses = nmplDeviceReportBusinessMapper.selectByExample(deviceReportBusinessExample);
        if (!CollectionUtils.isEmpty(deviceReportBusinesses) && !ParamCheckUtil.checkVoStrBlank(deviceReportBusinesses.get(0).getBusinessValue())){// 网元值不为空
            res.setReportValue(deviceReportBusinesses.get(0).getBusinessValue());
            res.setRange(EDIT_RANGE_PART);
            res.setPartId(deviceReportBusinesses.get(0).getId());
            return res;
        }

        NmplReportBusinessExample businessConfig = new NmplReportBusinessExample();
        businessConfig.createCriteria().andBusinessCodeEqualTo(businessCode).andIsExistEqualTo(IS_EXIST);
        List<NmplReportBusiness> reportBusinesses = nmplReportBusinessMapper.selectByExample(businessConfig);
        if (!CollectionUtils.isEmpty(reportBusinesses)){
            res.setRange(EDIT_RANGE_ALL);
            res.setMainId(reportBusinesses.get(0).getId());
            // 修改值为空
            if (ParamCheckUtil.checkVoStrBlank(reportBusinesses.get(0).getBusinessValue())){
                res.setReportValue(reportBusinesses.get(0).getDefaultValue());
            }else {// 修改值不为空
                res.setReportValue(reportBusinesses.get(0).getBusinessValue());
            }
        }
        return res;
    }


}
