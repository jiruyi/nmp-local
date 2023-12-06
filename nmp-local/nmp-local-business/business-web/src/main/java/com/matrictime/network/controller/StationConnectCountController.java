package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.StationConnectCountRequest;
import com.matrictime.network.response.StationConnectCountResponse;
import com.matrictime.network.service.StationConnectCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/8/8.
 */
@RequestMapping(value = "/stationConnectCount")
@RestController
@Slf4j
public class StationConnectCountController {

    @Resource
    private StationConnectCountService stationConnectCountService;

    @SystemLog(opermodul = "设备当前用户",operDesc = "查询各个用户总数",operType = "查询各个用户总数")
    @RequestMapping(value = "/selectStationConnectCount",method = RequestMethod.POST)
    public Result<Integer> selectStationConnectCount(@RequestBody StationConnectCountRequest stationConnectCountRequest){
        Result<Integer> result = new Result<>();
        try {
            result = stationConnectCountService.selectConnectCount(stationConnectCountRequest);
        }catch (Exception e){
            log.info("selectStationConnectCount:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

    //@SystemLog(opermodul = "设备当前用户",operDesc = "上报各个用户总数",operType = "上报各个用户总数")
    @RequestMapping(value = "/insertStationConnectCount",method = RequestMethod.POST)
    public Result<Integer> insertStationConnectCount(@RequestBody StationConnectCountResponse stationConnectCountResponse){
        Result<Integer> result = new Result<>();
        try {
            if(ObjectUtils.isEmpty(stationConnectCountResponse)){
                return new Result<>(false,"上传数据为空");
            }
            result = stationConnectCountService.insertConnectCount(stationConnectCountResponse);
        }catch (Exception e){
            log.info("updateStationConnectCount:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }



}
