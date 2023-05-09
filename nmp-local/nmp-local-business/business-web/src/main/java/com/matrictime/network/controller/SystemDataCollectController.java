package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationDataVo;
import com.matrictime.network.modelVo.BorderBaseStationDataVo;
import com.matrictime.network.modelVo.KeyCenterDataVo;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.service.SystemDataCollectService;
import lombok.extern.slf4j.Slf4j;
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
            result = systemDataCollectService.selectBaseStationData(dataCollectReq);
        }catch (Exception e){
            log.info("selectBaseStationData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
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
            result = systemDataCollectService.selectBorderBaseStationData(dataCollectReq);
        }catch (Exception e){
            log.info("selectBorderBaseStationData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
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
            result = systemDataCollectService.selectKeyCenterData(dataCollectReq);
        }catch (Exception e){
            log.info("selectKeyCenterData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

    /**
     * 业务数据收集
     * @param dataCollectReq
     * @return
     */
    @SystemLog(opermodul = "业务数据收集管理",operDesc = "业务数据收集",operType = "插入")
    @RequestMapping(value = "/insertSystemData",method = RequestMethod.POST)
    public Result<Integer> insertSystemData(@RequestBody DataCollectReq dataCollectReq){
        Result<Integer> result = new Result<>();
        try {
            result = systemDataCollectService.insertSystemData(dataCollectReq);
        }catch (Exception e){
            log.info("insertSystemData:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

}