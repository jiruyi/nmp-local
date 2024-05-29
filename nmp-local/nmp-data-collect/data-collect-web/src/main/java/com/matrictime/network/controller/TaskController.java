package com.matrictime.network.controller;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceReportBusinessMapper;
import com.matrictime.network.dao.mapper.NmplReportBusinessMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.ConfigEnum;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplConfigVo;
import com.matrictime.network.modelVo.NmplReportBusinessVo;
import com.matrictime.network.request.ScheduleCronReq;
import com.matrictime.network.request.SyncConfigReq;
import com.matrictime.network.request.TaskReq;
import com.matrictime.network.strategy.BusinessConifg;
import com.matrictime.network.util.SystemUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

import static com.matrictime.network.constant.BusinessConsts.COMMON_SWITCH_ON;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;

@RequestMapping(value = "/task")
@Api(value = "终端用户", tags = "终端用户相关接口")
@RestController
@Slf4j
public class TaskController extends BusinessConifg {

    @Resource
    private NmplDeviceInfoMapper deviceInfoMapper;

    @Resource
    private NmplReportBusinessMapper nmplReportBusinessMapper;

    @Resource
    private NmplDeviceReportBusinessMapper nmplDeviceReportBusinessMapper;

    @ApiOperation(value = "手动上报", notes = "手动上报")
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public Result report(@RequestBody TaskReq taskReq) {
        try {
            if(!CollectionUtils.isEmpty(taskReq.getCodeList())){
                for (String s : taskReq.getCodeList()) {
                    configServiceMap.get(s).businessData();
                }
            }
            return new Result();
        }catch (Exception e){
            log.error("TaskController report exception:{}", e);
            return new Result(false, e.getMessage());
        }
    }

    @ApiOperation(value = "同步数据采集参数配置", notes = "同步数据采集参数配置")
    @RequestMapping(value = "/syncConfig", method = RequestMethod.POST)
    public Result updateTime(@RequestBody SyncConfigReq req) {
        try {
            if (CollectionUtils.isEmpty(req.getConfigVos())){
                throw new Exception("configVos"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            // 通过ip获取本机deviceId
            Set<String> localIps = SystemUtils.getLocalIp4Address();
            NmplDeviceInfoExample example = new NmplDeviceInfoExample();
            example.createCriteria().andLanIpIn(new ArrayList<>(localIps)).andDeviceTypeEqualTo(DeviceTypeEnum.DATA_BASE.getCode()).andIsExistEqualTo(IS_EXIST);
            List<NmplDeviceInfo> deviceInfos = deviceInfoMapper.selectByExample(example);
            log.info("通过ip获取本机deviceId:url{},deviceInfo{}", JSONObject.toJSONString(localIps),JSONObject.toJSONString(deviceInfos));
            if (CollectionUtils.isEmpty(deviceInfos)){
                throw new Exception("本机信息获取异常");
            }
            String deviceId = deviceInfos.get(0).getDeviceId();

            NmplReportBusinessExample nmplReportBusinessExample = new NmplReportBusinessExample();
            List<NmplReportBusiness> nmplReportBusinesses = nmplReportBusinessMapper.selectByExample(nmplReportBusinessExample);

            NmplDeviceReportBusinessExample nmplDeviceReportBusinessExample = new NmplDeviceReportBusinessExample();
            nmplDeviceReportBusinessExample.createCriteria().andDeviceIdEqualTo(deviceId).andIsExistEqualTo(true).andBusinessValueIsNotNull();
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

            for (NmplConfigVo configVo: req.getConfigVos()){
                if (ConfigEnum.REPORT_PERIOD.getCode().equals(configVo.getConfigCode())){// 目前只处理上报周期配置
//                    if (CollectionUtils.isEmpty(req.getReportBusiness())){
//                        throw new Exception("reportBusiness"+ErrorMessageContants.PARAM_IS_NULL_MSG);
//                    }

                    for (String businessCode: codeList){
                        configServiceMap.get(businessCode).updateTimer(Long.valueOf(configVo.getConfigValue())*1000);
                    }
                }
            }
            return new Result();
        }catch (Exception e){
            log.error("TaskController syncConfig exception:{}", e);
            return new Result(false, e.getMessage());
        }
    }


}
