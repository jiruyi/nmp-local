package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.domain.SystemDataCollectDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationDataVo;
import com.matrictime.network.modelVo.BorderBaseStationDataVo;
import com.matrictime.network.modelVo.KeyCenterDataVo;
import com.matrictime.network.modelVo.TimeDataVo;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.service.SystemDataCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
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

    @Override
    public Result<BaseStationDataVo> selectBaseStationData(DataCollectReq dataCollectReq) {
        Result<BaseStationDataVo> result = new Result<>();
        try {
            String value = (String) redisTemplate.opsForValue().
                    get(DataConstants.BASE_STATION_FLOW_COUNT);
            if(ObjectUtils.isEmpty(value)){
                BaseStationDataVo baseStationDataVo = systemDataCollectDomainService.selectBaseStationData(dataCollectReq);
                redisTemplate.opsForValue().set(DataConstants.BASE_STATION_FLOW_COUNT,JSONObject.toJSONString(baseStationDataVo),30, TimeUnit.MINUTES);
                result.setSuccess(true);
                result.setResultObj(baseStationDataVo);
                return result;
            }
            BaseStationDataVo baseStationDataVo = JSONObject.parseObject(value, BaseStationDataVo.class);
            result.setResultObj(baseStationDataVo);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("");
            result.setSuccess(false);
            log.info("selectBaseStationData:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<BorderBaseStationDataVo> selectBorderBaseStationData(DataCollectReq dataCollectReq) {
        Result<BorderBaseStationDataVo> result = new Result<>();
        try {
            String value = (String) redisTemplate.opsForValue().
                    get(DataConstants.BORDER_BASE_STATION_FLOW_COUNT);
            if(ObjectUtils.isEmpty(value)){
                BorderBaseStationDataVo borderBaseStationDataVo = systemDataCollectDomainService.selectBorderBaseStationData(dataCollectReq);
                redisTemplate.opsForValue().set(DataConstants.BASE_STATION_FLOW_COUNT,JSONObject.toJSONString(borderBaseStationDataVo),30, TimeUnit.MINUTES);
                result.setSuccess(true);
                result.setResultObj(borderBaseStationDataVo);
                return result;
            }
            BorderBaseStationDataVo borderBaseStationDataVo = JSONObject.parseObject(value, BorderBaseStationDataVo.class);
            result.setResultObj(borderBaseStationDataVo);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("");
            result.setSuccess(false);
            log.info("selectBorderBaseStationData:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<KeyCenterDataVo> selectKeyCenterData(DataCollectReq dataCollectReq) {
        Result<KeyCenterDataVo> result = new Result<>();
        try {
            String value = (String) redisTemplate.opsForValue().
                    get(DataConstants.KEY_CENTER_FLOW_COUNT);
            if(ObjectUtils.isEmpty(value)){
                KeyCenterDataVo keyCenterDataVo = systemDataCollectDomainService.selectKeyCenterData(dataCollectReq);
                redisTemplate.opsForValue().set(DataConstants.BASE_STATION_FLOW_COUNT,JSONObject.toJSONString(keyCenterDataVo),30, TimeUnit.MINUTES);
                result.setSuccess(true);
                result.setResultObj(keyCenterDataVo);
                return result;
            }
            KeyCenterDataVo keyCenterDataVo = JSONObject.parseObject(value, KeyCenterDataVo.class);
            result.setResultObj(keyCenterDataVo);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("");
            result.setSuccess(false);
            log.info("selectKeyCenterData:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> insertSystemData(DataCollectReq dataCollectReq) {
        Result<Integer> result = new Result<>();
        try {
            result.setResultObj(systemDataCollectDomainService.insertSystemData(dataCollectReq));
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("");
            result.setSuccess(false);
            log.info("insertSystemData:{}",e.getMessage());
        }
        return result;
    }
}
