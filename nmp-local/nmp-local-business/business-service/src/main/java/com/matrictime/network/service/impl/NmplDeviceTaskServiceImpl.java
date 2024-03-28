package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSON;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplBaseStationMapper;
import com.matrictime.network.dao.mapper.NmplDeviceMapper;
import com.matrictime.network.dao.mapper.NmplDeviceTaskConfigMapper;
import com.matrictime.network.dao.mapper.NmplTaskConfigMapper;
import com.matrictime.network.dao.model.*;

import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplDeviceTaskConfigVo;
import com.matrictime.network.modelVo.TaskVo;
import com.matrictime.network.request.DeviceTaskConfigReq;
import com.matrictime.network.service.NmplDeviceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;

import static com.matrictime.network.base.exception.ErrorMessageContants.PARAM_IS_NULL_MSG;

@Service
@Slf4j
public class NmplDeviceTaskServiceImpl extends SystemBaseService implements NmplDeviceTaskService {

    @Resource
    NmplDeviceTaskConfigMapper nmplDeviceTaskConfigMapper;

    @Resource
    NmplTaskConfigMapper nmplTaskConfigMapper;

    @Resource
    NmplBaseStationMapper nmplBaseStationMapper;

    @Resource
    NmplDeviceMapper nmplDeviceMapper;

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
    public Result query(DeviceTaskConfigReq deviceTaskConfigReq) {
        Result result;
        try {
            //校验参数
            queryCheckParam(deviceTaskConfigReq);

            //查询通用网元定时任务配置与此设备相关的配置
            NmplTaskConfigExample nmplTaskConfigExample = new NmplTaskConfigExample();
            nmplTaskConfigExample.createCriteria().andDeviceTypeEqualTo(deviceTaskConfigReq.getDeviceType()).andIsExistEqualTo(true);
            List<NmplTaskConfig> nmplTaskConfigs =  nmplTaskConfigMapper.selectByExample(nmplTaskConfigExample);

            //查询节点网元定时任务配置
            NmplDeviceTaskConfigExample nmplDeviceTaskConfigExample = new NmplDeviceTaskConfigExample();
            nmplDeviceTaskConfigExample.createCriteria().andDeviceIdEqualTo(deviceTaskConfigReq.getDeviceId()).andIsExistEqualTo(true);
            List<NmplDeviceTaskConfig> nmplDeviceTaskConfigs = nmplDeviceTaskConfigMapper.selectByExample(nmplDeviceTaskConfigExample);

            Map<String,NmplDeviceTaskConfig> map = new HashMap<>();
            for (NmplDeviceTaskConfig nmplDeviceTaskConfig : nmplDeviceTaskConfigs) {
                map.put(nmplDeviceTaskConfig.getTaskId(),nmplDeviceTaskConfig);
            }

            List<NmplDeviceTaskConfigVo> configVos = new ArrayList<>();
            //根据原则 节点修改值>通用修改值>默认值
            for (NmplTaskConfig nmplTaskConfig : nmplTaskConfigs) {
                NmplDeviceTaskConfigVo nmplDeviceTaskConfigVo = new NmplDeviceTaskConfigVo();
                BeanUtils.copyProperties(nmplTaskConfig,nmplDeviceTaskConfigVo);
                Long totalValue = nmplTaskConfig.getCurrentValue()!=null?nmplTaskConfig.getCurrentValue():nmplTaskConfig.getDefaultValue();
                nmplDeviceTaskConfigVo.setDefultValue(totalValue);
                if(map.containsKey(nmplTaskConfig.getTaskId())){
                    nmplDeviceTaskConfigVo.setCurrentValue(map.get(nmplTaskConfig.getTaskId()).getCurrentValue()!=null?map.get(nmplTaskConfig.getTaskId()).getCurrentValue():totalValue);
                }else {
                    nmplDeviceTaskConfigVo.setCurrentValue(totalValue);

                    //将这条数据插入到节点网元定时任务配置表中
                    NmplDeviceTaskConfig nmplDeviceTaskConfig = new NmplDeviceTaskConfig();
                    BeanUtils.copyProperties(nmplTaskConfig,nmplDeviceTaskConfig);
                    nmplDeviceTaskConfig.setId(null);
                    nmplDeviceTaskConfig.setCurrentValue(null);
                    nmplDeviceTaskConfigMapper.insertSelective(nmplDeviceTaskConfig);
                }
                configVos.add(nmplDeviceTaskConfigVo);
            }
            result = buildResult(configVos);
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
    public Result save(DeviceTaskConfigReq deviceTaskConfigReq) {
        Result result = null;
        try {
            saveCheckParam(deviceTaskConfigReq);
            for (NmplDeviceTaskConfigVo nmplDeviceTaskConfigVo : deviceTaskConfigReq.getNmplDeviceTaskConfigVoList()) {
                NmplDeviceTaskConfig nmplDeviceTaskConfig = new NmplDeviceTaskConfig();
                BeanUtils.copyProperties(nmplDeviceTaskConfigVo,nmplDeviceTaskConfig);
                nmplDeviceTaskConfigMapper.updateByPrimaryKeySelective(nmplDeviceTaskConfig);
            }
            List<String> ipList = getIpList(deviceTaskConfigReq.getDeviceId(), deviceTaskConfigReq.getDeviceType());
            List<TaskVo> taskVoList = new ArrayList<>();
            for (String ip : ipList) {
                TaskVo taskVo =new TaskVo();
                taskVo.setDeviceType(deviceTaskConfigReq.getDeviceType());
                taskVo.setData(JSON.toJSON(deviceTaskConfigReq.getNmplDeviceTaskConfigVoList()).toString());
                taskVo.setIp(ip);
                taskVoList.add(taskVo);
            }
            Future<Map<String, Boolean>> mapFuture = pushToProxy(taskVoList);
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



    private void queryCheckParam(DeviceTaskConfigReq deviceTaskConfigReq){
        if(deviceTaskConfigReq.getDeviceId()==null||deviceTaskConfigReq.getDeviceType()==null){
            throw new SystemException(PARAM_IS_NULL_MSG);
        }
    }

    private void saveCheckParam(DeviceTaskConfigReq deviceTaskConfigReq){
        if(CollectionUtils.isEmpty(deviceTaskConfigReq.getNmplDeviceTaskConfigVoList())){
            throw new SystemException(PARAM_IS_NULL_MSG);
        }
    }



    public Future<Map<String,Boolean>> pushToProxy(List<TaskVo> taskVoList){
        Future<Map<String, Boolean>> mapFuture = null;
        for (TaskVo taskVo : taskVoList) {
            String url;
            switch (DeviceTypeEnum.getByCode(taskVo.getDeviceType())){
                case DATA_BASE:
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
        }
        return mapFuture;
    }

    /**
     * 获取所有推送节点的ip
     * @param deviceId
     * @param deviceType
     * @return
     */
    private List<String> getIpList(String deviceId,String deviceType){
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

}
