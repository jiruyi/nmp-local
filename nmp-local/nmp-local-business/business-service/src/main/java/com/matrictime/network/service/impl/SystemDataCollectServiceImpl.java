package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.DataCollectEnum;
import com.matrictime.network.dao.domain.SystemDataCollectDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.TerminalDataReq;
import com.matrictime.network.service.DataCollectService;
import com.matrictime.network.service.SystemDataCollectService;
import com.matrictime.network.service.TerminalDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
@Service
@Slf4j
public class SystemDataCollectServiceImpl implements SystemDataCollectService {

    @Resource
    private SystemDataCollectDomainService systemDataCollectDomainService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DataCollectService dataCollectService;

    /**
     * 查询接入基站流量数据
     * @param dataCollectReq
     * @return
     */
    @Override
    public Result<BaseStationDataVo> selectBaseStationData(DataCollectReq dataCollectReq) {
        Result<BaseStationDataVo> result = new Result<>();
        try {
            BaseStationDataVo baseStationDataVo = systemDataCollectDomainService.selectBaseStationData(dataCollectReq);
            result.setSuccess(true);
            result.setResultObj(baseStationDataVo);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.info("selectBaseStationData:{}",e.getMessage());
        }
        return result;
    }

    /**
     * 查询边界基站流量数据
     * @param dataCollectReq
     * @return
     */
    @Override
    public Result<BorderBaseStationDataVo> selectBorderBaseStationData(DataCollectReq dataCollectReq) {
        Result<BorderBaseStationDataVo> result = new Result<>();
        try {
            BorderBaseStationDataVo borderBaseStationDataVo = systemDataCollectDomainService.selectBorderBaseStationData(dataCollectReq);
            result.setSuccess(true);
            result.setResultObj(borderBaseStationDataVo);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.info("selectBorderBaseStationData:{}",e.getMessage());
        }
        return result;
    }

    /**
     * 查询密钥中心流量数据
     * @param dataCollectReq
     * @return
     */
    @Override
    public Result<KeyCenterDataVo> selectKeyCenterData(DataCollectReq dataCollectReq) {
        Result<KeyCenterDataVo> result = new Result<>();
        try {
            KeyCenterDataVo keyCenterDataVo = systemDataCollectDomainService.selectKeyCenterData(dataCollectReq);
            result.setResultObj(keyCenterDataVo);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.info("selectKeyCenterData:{}",e.getMessage());
        }
        return result;
    }

    /**
     * 业务数据收集
     * @param dataCollectReq
     * @return
     */
    @Override
    public Result<Integer> insertSystemData(DataCollectReq dataCollectReq) {
        Result<Integer> result = new Result<>();
        try {
            int i = 0;
            i = systemDataCollectDomainService.insertSystemData(dataCollectReq.getDataCollectVoList());
            //插入缓存
            Set<String> set = new HashSet<>();
            String deviceIp = "";
            for (DataCollectVo dataCollectVo: dataCollectReq.getDataCollectVoList()){
                deviceIp = dataCollectVo.getDeviceIp();
                if(Integer.parseInt(DataCollectEnum.FORWARD_LOAD_UP_FLOW.getCode()) <= Integer.parseInt(dataCollectVo.getDataItemCode()) &&
                        Integer.parseInt(dataCollectVo.getDataItemCode())<= Integer.parseInt(DataCollectEnum.ISOLATIONLOAD_DOWN_FLOW.getCode())){
                    set.add(dataCollectVo.getDataItemCode());
                }
            }
            for (String s : set) {
                dataCollectService.handleAddData(s,deviceIp);
            }
            result.setResultObj(i);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("");
            result.setSuccess(false);
            log.info("insertSystemData:{}",e.getMessage());
        }
        return result;
    }
}
