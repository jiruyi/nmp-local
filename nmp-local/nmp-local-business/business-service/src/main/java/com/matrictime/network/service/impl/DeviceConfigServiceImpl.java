package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSON;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DeviceConfigVo;
import com.matrictime.network.modelVo.DeviceReportBusinessVo;
import com.matrictime.network.modelVo.NmplDeviceTaskConfigVo;
import com.matrictime.network.modelVo.TaskVo;
import com.matrictime.network.request.DeviceConfigReq;
import com.matrictime.network.request.DeviceReportBusinessReq;
import com.matrictime.network.request.DeviceTaskConfigReq;
import com.matrictime.network.service.DeviceConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static com.matrictime.network.base.exception.ErrorMessageContants.PARAM_IS_NULL_MSG;

@Service
@Slf4j
public class DeviceConfigServiceImpl extends SystemBaseService implements DeviceConfigService {

    @Resource
    NmplDeviceConfigMapper nmplDeviceConfigMapper;

    @Resource
    NmplConfigMapper nmplConfigMapper;

    @Resource
    NmplBaseStationMapper nmplBaseStationMapper;

    @Resource
    NmplDeviceMapper nmplDeviceMapper;

    @Resource
    NmplReportBusinessMapper nmplReportBusinessMapper;

    @Resource
    NmplDeviceReportBusinessMapper nmplDeviceReportBusinessMapper;

    @Resource
    AsyncService asyncService;

    @Value("${proxy.port}")
    private String proxyPort;

    @Value("${proxy.context-path}")
    private String proxyContextPath;


    @Value("${datacollect.port}")
    private String datacollectPort;

    @Value("${datacollect.context-path}")
    private String datacollectContextPath;


    @Override
    public Result query(DeviceConfigReq deviceConfigReq) {
        Result result;
        try {
            //校验参数
            queryCheckParam(deviceConfigReq);

            //查询通用网元配置与此设备相关的配置
            NmplConfigExample nmplConfigExample = new NmplConfigExample();
            nmplConfigExample.createCriteria().andDeviceTypeEqualTo(deviceConfigReq.getDeviceType()).andIsExistEqualTo(true);
            List<NmplConfig> nmplConfigs =  nmplConfigMapper.selectByExample(nmplConfigExample);

            //查询节点网元配置
            NmplDeviceConfigExample nmplDeviceConfigExample = new NmplDeviceConfigExample();
            nmplDeviceConfigExample.createCriteria().andDeviceIdEqualTo(deviceConfigReq.getDeviceId()).andIsExistEqualTo(true);
            List<NmplDeviceConfig> nmplDeviceConfigs = nmplDeviceConfigMapper.selectByExample(nmplDeviceConfigExample);

            Map<String,NmplDeviceConfig> map = new HashMap<>();
            for (NmplDeviceConfig nmplDeviceTaskConfig : nmplDeviceConfigs) {
                map.put(nmplDeviceTaskConfig.getConfigCode(),nmplDeviceTaskConfig);
            }

            List<DeviceConfigVo> configVos = new ArrayList<>();
            //根据原则 节点修改值>通用修改值>默认值
            for (NmplConfig nmplConfig : nmplConfigs) {
                DeviceConfigVo deviceConfigVo = new DeviceConfigVo();
                BeanUtils.copyProperties(nmplConfig,deviceConfigVo);
                String totalValue = nmplConfig.getConfigValue()!=null?nmplConfig.getConfigValue():nmplConfig.getDefaultValue();
                deviceConfigVo.setDefaultValue(totalValue);

                if(map.containsKey(nmplConfig.getConfigCode())){
                    deviceConfigVo.setConfigValue(map.get(nmplConfig.getConfigCode()).getConfigValue()!=null?map.get(nmplConfig.getConfigCode()).getConfigValue():totalValue);
                }else {
                    deviceConfigVo.setConfigValue(totalValue);

                    //将这条数据插入到节点网元配置表中
                    NmplDeviceConfig nmplDeviceConfig = new NmplDeviceConfig();
                    BeanUtils.copyProperties(nmplConfig,nmplDeviceConfig);
                    nmplDeviceConfig.setId(null);
                    nmplDeviceConfig.setConfigValue(null);
                    nmplDeviceConfigMapper.insertSelective(nmplDeviceConfig);
                }
                configVos.add(deviceConfigVo);
            }
            result = buildResult(configVos);
        } catch (SystemException e) {
            log.info("网元配置查询异常", e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.error("网元配置查询异常：{}", e.getMessage());
            result = failResult("");
        }
        return result;
    }


    @Override
    public Result save(DeviceConfigReq deviceConfigReq) {
        Result result = null;
        try {
            saveCheckParam(deviceConfigReq);
            for (DeviceConfigVo DeviceConfigVo : deviceConfigReq.getDeviceConfigVoList()) {
                NmplDeviceConfig nmplDeviceConfig = new NmplDeviceConfig();
                BeanUtils.copyProperties(DeviceConfigVo,nmplDeviceConfig);
                nmplDeviceConfigMapper.updateByPrimaryKeySelective(nmplDeviceConfig);
            }
            List<String> ipList = getIpList(deviceConfigReq.getDeviceId(), deviceConfigReq.getDeviceType());
            List<TaskVo> taskVoList = new ArrayList<>();
            for (String ip : ipList) {
                TaskVo taskVo =new TaskVo();
                taskVo.setDeviceType(deviceConfigReq.getDeviceType());
                taskVo.setData(JSON.toJSON(deviceConfigReq.getDeviceConfigVoList()).toString());
                taskVo.setIp(ip);
                taskVoList.add(taskVo);
            }
            List<Future> mapFuture = pushConfigToProxy(taskVoList);
            result = buildResult(mapFuture);
        } catch (SystemException e) {
            log.info("网元定时任务配置同步异常", e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.error("网元定时任务配置同步异常：{}", e.getMessage());
            result = failResult("");
        }
        return result;
    }


    private void queryCheckParam(DeviceConfigReq deviceConfigReq){
        if(deviceConfigReq.getDeviceId()==null||deviceConfigReq.getDeviceType()==null){
            throw new SystemException(PARAM_IS_NULL_MSG);
        }
    }

    private void saveCheckParam(DeviceConfigReq deviceConfigReq){
        if(CollectionUtils.isEmpty(deviceConfigReq.getDeviceConfigVoList())){
            throw new SystemException(PARAM_IS_NULL_MSG);
        }
    }

    @Override
    public List<Future> pushConfigToProxy(List<TaskVo> taskVoList){
        Future<Map<String, Boolean>> mapFuture = null;
        List<Future> result = new ArrayList<>();
        for (TaskVo taskVo : taskVoList) {
            String url;
            switch (DeviceTypeEnum.getByCode(taskVo.getDeviceType())){
                case DATA_BASE:
//                    后续如果要特殊化处理需要走此处
//                    url = "http://" + taskVo.getIp() + ":" + datacollectPort + datacollectContextPath + "";
//                    break;
                case ACCUSE_CENTER:
//                    后续如果要特殊化处理需要走此处
//                    url = "http://" + taskVo.getIp() + ":" + datacollectPort + datacollectContextPath + "";
//                    break;
                default:
                    url = "http://" + taskVo.getIp() + ":" + proxyPort + proxyContextPath + "";
                    break;
            }
            Map<String,String> map = new HashMap<>();
            map.put(DataConstants.KEY_DATA, taskVo.getData());
            map.put(DataConstants.KEY_URL, url);
            mapFuture = asyncService.httpUpdateDeviceTask(map);
            result.add(mapFuture);
        }
        return result;
    }

    /**
     * 获取所有推送节点的ip
     * @param deviceId
     * @param deviceType
     * @return
     */
    public List<String> getIpList(String deviceId,String deviceType){
        List<String> ipList= new ArrayList<>();
        if(deviceType.equals(DeviceTypeEnum.STATION_INSIDE.getCode())||deviceType.equals(DeviceTypeEnum.STATION_BOUNDARY.getCode())){
            NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
            NmplBaseStationExample.Criteria criteria = nmplBaseStationExample.createCriteria();
            criteria.andIsExistEqualTo(true).andStationIdEqualTo(deviceId).andStationTypeEqualTo(deviceType);
            nmplBaseStationExample.createCriteria().andStationIdEqualTo(deviceId).andIsExistEqualTo(true);
            List<NmplBaseStation> nmplBaseStations = nmplBaseStationMapper.selectByExample(nmplBaseStationExample);
            if(!CollectionUtils.isEmpty(nmplBaseStations)){
                for (NmplBaseStation nmplBaseStation : nmplBaseStations) {
                    ipList.add(nmplBaseStation.getLanIp());
                }
            }
        }else {
            NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
            NmplDeviceExample.Criteria criteria = nmplDeviceExample.createCriteria();
            criteria.andIsExistEqualTo(true).andDeviceIdEqualTo(deviceId).andDeviceTypeEqualTo(deviceType);
            List<NmplDevice> nmplDeviceList = nmplDeviceMapper.selectByExample(nmplDeviceExample);
            if(!CollectionUtils.isEmpty(nmplDeviceList)){
                for (NmplDevice nmplDevice : nmplDeviceList) {
                    ipList.add(nmplDevice.getLanIp());
                }
            }
        }
        return ipList;
    }


    //---------------------------------------------------------------------------


    private void queryCheckParam(DeviceReportBusinessReq deviceReportBusinessReq){
        if(deviceReportBusinessReq.getDeviceId()==null||deviceReportBusinessReq.getDeviceType()==null){
            throw new SystemException(PARAM_IS_NULL_MSG);
        }
    }

    private void saveCheckParam(DeviceReportBusinessReq deviceReportBusinessReq){
        if(CollectionUtils.isEmpty(deviceReportBusinessReq.getDeviceReportBusinessVoList())){
            throw new SystemException(PARAM_IS_NULL_MSG);
        }
    }



    @Override
    public Result queryReportBusiness(DeviceReportBusinessReq deviceReportBusinessReq) {
        Result result = null;
        try {
            //校验参数
            queryCheckParam(deviceReportBusinessReq);

            //查询通用网元配置与此设备相关的配置
            NmplReportBusinessExample nmplReportBusinessExample = new NmplReportBusinessExample();
            nmplReportBusinessExample.createCriteria().andIsExistEqualTo(true);
            List<NmplReportBusiness> nmplReportBusinesses = nmplReportBusinessMapper.selectByExample(nmplReportBusinessExample);

            //查询节点网元配置
            NmplDeviceReportBusinessExample nmplDeviceReportBusinessExample = new NmplDeviceReportBusinessExample();
            nmplDeviceReportBusinessExample.createCriteria().andIsExistEqualTo(true).andDeviceIdEqualTo(deviceReportBusinessReq.getDeviceId());
            List<NmplDeviceReportBusiness> nmplDeviceReportBusinesses = nmplDeviceReportBusinessMapper.selectByExample(nmplDeviceReportBusinessExample);

            Map<String, NmplDeviceReportBusiness> map = new HashMap<>();
            for (NmplDeviceReportBusiness nmplDeviceReportBusiness : nmplDeviceReportBusinesses) {
                map.put(nmplDeviceReportBusiness.getBusinessCode(), nmplDeviceReportBusiness);
            }

            List<DeviceReportBusinessVo> deviceReportBusinessVoList = new ArrayList<>();
            //根据原则 节点修改值>通用修改值>默认值
            for (NmplReportBusiness nmplReportBusiness : nmplReportBusinesses) {
                DeviceReportBusinessVo deviceReportBusinessVo = new DeviceReportBusinessVo();
                BeanUtils.copyProperties(nmplReportBusiness, deviceReportBusinessVo);
                String totalValue = nmplReportBusiness.getBusinessValue() != null ? nmplReportBusiness.getBusinessValue() : nmplReportBusiness.getDefaultValue();
                deviceReportBusinessVo.setDefaultValue(totalValue);
                if (map.containsKey(nmplReportBusiness.getBusinessCode())) {
                    deviceReportBusinessVo.setBusinessValue(map.get(nmplReportBusiness.getBusinessCode()).getBusinessValue() != null ? map.get(nmplReportBusiness.getBusinessCode()).getBusinessValue() : totalValue);
                } else {
                    deviceReportBusinessVo.setBusinessValue(totalValue);

                    //将这条数据插入到节点网元定时任务配置表中
                    NmplDeviceReportBusiness nmplDeviceReportBusiness = new NmplDeviceReportBusiness();
                    BeanUtils.copyProperties(nmplReportBusiness, nmplDeviceReportBusiness);
                    nmplDeviceReportBusiness.setId(null);
                    nmplDeviceReportBusiness.setBusinessValue(null);
                    nmplDeviceReportBusinessMapper.insertSelective(nmplDeviceReportBusiness);
                }
                deviceReportBusinessVoList.add(deviceReportBusinessVo);
            }
            result = buildResult(deviceReportBusinessVoList);
        } catch (SystemException e) {
            log.info("网元定时任务配置查询异常", e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.error("网元定时任务配置查询异常：{}", e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result saveReportBusiness(DeviceReportBusinessReq deviceReportBusinessReq) {
        Result result = null;
        try {
            saveCheckParam(deviceReportBusinessReq);
            for (DeviceReportBusinessVo deviceReportBusinessVo : deviceReportBusinessReq.getDeviceReportBusinessVoList()) {
                NmplDeviceReportBusiness nmplDeviceTaskConfigVo = new NmplDeviceReportBusiness();
                BeanUtils.copyProperties(deviceReportBusinessVo,nmplDeviceTaskConfigVo);
                nmplDeviceReportBusinessMapper.updateByPrimaryKeySelective(nmplDeviceTaskConfigVo);
            }
            List<String> ipList = getIpList(deviceReportBusinessReq.getDeviceId(), deviceReportBusinessReq.getDeviceType());
            List<TaskVo> taskVoList = new ArrayList<>();
            for (String ip : ipList) {
                TaskVo taskVo =new TaskVo();
                taskVo.setDeviceType(deviceReportBusinessReq.getDeviceType());
                taskVo.setData(JSON.toJSON(deviceReportBusinessReq.getDeviceReportBusinessVoList()).toString());
                taskVo.setIp(ip);
                taskVoList.add(taskVo);
            }
            List<Future> mapFuture = pushReportBusinessToProxy(taskVoList);
            result = buildResult(mapFuture);
        } catch (SystemException e) {
            log.info("网元定时任务配置同步异常", e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.error("网元定时任务配置同步异常：{}", e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public List<Future> pushReportBusinessToProxy(List<TaskVo> taskVoList){
        Future<Map<String, Boolean>> mapFuture = null;
        List<Future> result = new ArrayList<>();
        for (TaskVo taskVo : taskVoList) {
            String url = "http://" + taskVo.getIp() + ":" + datacollectPort + datacollectContextPath + "";
            Map<String,String> map = new HashMap<>();
            map.put(DataConstants.KEY_DATA, taskVo.getData());
            map.put(DataConstants.KEY_URL, url);
            mapFuture = asyncService.httpUpdateDeviceTask(map);
            result.add(mapFuture);
        }
        return result;
    }

}
