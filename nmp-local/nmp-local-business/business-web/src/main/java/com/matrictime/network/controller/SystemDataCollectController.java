package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationDataVo;
import com.matrictime.network.modelVo.BorderBaseStationDataVo;
import com.matrictime.network.modelVo.KeyCenterDataVo;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.service.SystemDataCollectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
@RestController
@RequestMapping(value = "/systemDataCollect",method = RequestMethod.POST)
@Slf4j
public class SystemDataCollectController {

    @Resource
    private SystemDataCollectService systemDataCollectService;

    /**
     * 查询接入基站流量数据
     * @return
     */
    @SystemLog(opermodul = "业务数据收集管理",operDesc = "查询接入基站流量数据",operType = "查询")
    @RequestMapping(value = "/selectBaseStationData",method = RequestMethod.POST)
    public Result<BaseStationDataVo> selectBaseStationData(@RequestBody DataCollectReq dataCollectReq){
        Result<BaseStationDataVo> result = new Result<>();
        try {
            if(StringUtils.isEmpty(dataCollectReq.getRelationOperatorId())){
                //获取小区id为空时基站流量的数据
                BaseStationDataVo emptyBaseStationDataVo = getEmptyBaseStationDataVo();
                Result<BaseStationDataVo> dataVoResult = new Result<>();
                dataVoResult.setSuccess(true);
                dataVoResult.setResultObj(emptyBaseStationDataVo);
                return dataVoResult;
            }
            result = systemDataCollectService.selectBaseStationData(dataCollectReq);
        }catch (Exception e){
            log.info("selectBaseStationData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

    /**
     * 获取小区id为空时基站流量的数据
     * @return
     */
    private BaseStationDataVo getEmptyBaseStationDataVo(){
        BaseStationDataVo baseStationDataVo = new BaseStationDataVo();
        baseStationDataVo.setKeyRelayPayloadDown(DataConstants.EMPTY_FLOW);
        baseStationDataVo.setForwardingPayloadUp(DataConstants.EMPTY_FLOW);
        baseStationDataVo.setKeyRelayPayloadUp(DataConstants.EMPTY_FLOW);
        baseStationDataVo.setCommunicationsLoadDown(DataConstants.EMPTY_FLOW);
        baseStationDataVo.setCommunicationsLoadUp(DataConstants.EMPTY_FLOW);
        baseStationDataVo.setForwardingPayloadDown(DataConstants.EMPTY_FLOW);
        return baseStationDataVo;
    }

    /**
     * 查询边界基站流量数据
     * @return
     */
    @SystemLog(opermodul = "业务数据收集管理",operDesc = "查询边界基站流量数据",operType = "查询")
    @RequestMapping(value = "/selectBorderBaseStationData",method = RequestMethod.POST)
    public Result<BorderBaseStationDataVo> selectBorderBaseStationData(@RequestBody DataCollectReq dataCollectReq){
        Result<BorderBaseStationDataVo> result = new Result<>();
        try {
            if(StringUtils.isEmpty(dataCollectReq.getRelationOperatorId())){
                //查询小区id为空时边界基站流量数据
                BorderBaseStationDataVo emptyBorderBaseStationDataVo = getEmptyBorderBaseStationDataVo();
                Result<BorderBaseStationDataVo> dataVoResult = new Result<>();
                dataVoResult.setResultObj(emptyBorderBaseStationDataVo);
                dataVoResult.setSuccess(true);
                return dataVoResult;
            }
            result = systemDataCollectService.selectBorderBaseStationData(dataCollectReq);
        }catch (Exception e){
            log.info("selectBorderBaseStationData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

    /**
     * 查询小区id为空时边界基站流量数据
     * @return
     */
    private BorderBaseStationDataVo getEmptyBorderBaseStationDataVo(){
        BorderBaseStationDataVo borderBaseStationDataVo = new BorderBaseStationDataVo();
        borderBaseStationDataVo.setCommunicationsLoadUp(DataConstants.EMPTY_FLOW);
        borderBaseStationDataVo.setCommunicationsLoadDown(DataConstants.EMPTY_FLOW);
        borderBaseStationDataVo.setForwardingPayloadDown(DataConstants.EMPTY_FLOW);
        borderBaseStationDataVo.setForwardingPayloadUp(DataConstants.EMPTY_FLOW);
        borderBaseStationDataVo.setKeyRelayPayloadDown(DataConstants.EMPTY_FLOW);
        borderBaseStationDataVo.setKeyRelayPayloadUp(DataConstants.EMPTY_FLOW);
        return borderBaseStationDataVo;
    }

    /**
     * 查询密钥中心流量数据
     * @return
     */
    @SystemLog(opermodul = "业务数据收集管理",operDesc = "查询密钥中心流量数据",operType = "查询")
    @RequestMapping(value = "/selectKeyCenterData",method = RequestMethod.POST)
    public Result<KeyCenterDataVo> selectKeyCenterData(@RequestBody DataCollectReq dataCollectReq){
        Result<KeyCenterDataVo> result = new Result<>();
        try {
            if(StringUtils.isEmpty(dataCollectReq.getRelationOperatorId())){
                //获取小区id为空的密钥中心流量数据
                KeyCenterDataVo emptyKeyCenterDataVo = getEmptyKeyCenterDataVo();
                Result<KeyCenterDataVo> dataVoResult = new Result<>();
                dataVoResult.setResultObj(emptyKeyCenterDataVo);
                dataVoResult.setSuccess(true);
                return dataVoResult;
            }
            result = systemDataCollectService.selectKeyCenterData(dataCollectReq);
        }catch (Exception e){
            log.info("selectKeyCenterData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

    /**
     * 获取小区id为空的密钥中心流量数据
     * @return
     */
    private KeyCenterDataVo getEmptyKeyCenterDataVo(){
        KeyCenterDataVo keyCenterDataVo = new KeyCenterDataVo();
        keyCenterDataVo.setKeyDistributionPayloadDown(DataConstants.EMPTY_FLOW);
        keyCenterDataVo.setKeyDistributionPayloadUp(DataConstants.EMPTY_FLOW);
        keyCenterDataVo.setCommunicationsLoadDown(DataConstants.EMPTY_FLOW);
        keyCenterDataVo.setCommunicationsLoadUp(DataConstants.EMPTY_FLOW);
        keyCenterDataVo.setForwardingPayloadDown(DataConstants.EMPTY_FLOW);
        keyCenterDataVo.setForwardingPayloadUp(DataConstants.EMPTY_FLOW);
        return keyCenterDataVo;
    }

    /**
     * 业务数据收集
     * @param dataCollectReq
     * @return
     */
    //@SystemLog(opermodul = "业务数据收集管理",operDesc = "业务数据收集",operType = "插入")
    @RequestMapping(value = "/insertSystemData",method = RequestMethod.POST)
    public Result<Integer> insertSystemData(@RequestBody DataCollectReq dataCollectReq){
        Result<Integer> result = new Result<>();
        try {
            if(ObjectUtils.isEmpty(dataCollectReq)){
                return new Result<>(false,"上报数据为空");
            }
            result = systemDataCollectService.insertSystemData(dataCollectReq);
        }catch (Exception e){
            log.info("insertSystemData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

}
