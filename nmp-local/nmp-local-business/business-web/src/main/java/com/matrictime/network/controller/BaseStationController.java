package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.service.BaseStationInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/baseStation",method = RequestMethod.POST)
@Slf4j
public class BaseStationController {

    @Autowired
    private BaseStationInfoService baseStationInfoService;

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ApiOperation(value = "基站接口",notes = "基站信息插入")
    public Result<Integer> insertBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = baseStationInfoService.insertBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("新增基站异常:insertBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("新增基站异常");
        }
        return result;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "基站接口",notes = "基站信息更新")
    public Result<Integer> updateBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = baseStationInfoService.updateBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("更新基站异常:updateBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("更新基站异常");
        }
        return result;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "基站接口",notes = "基站信息删除")
    public Result<Integer> deleteBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = baseStationInfoService.deleteBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("更新基站异常:deleteBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("删除基站异常");
        }
        return result;
    }

    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ApiOperation(value = "基站接口",notes = "根据条件基站信息查询")
    public Result<BaseStationInfoResponse> selectBaseStationInfo(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<BaseStationInfoResponse> result = new Result<>();
        try {
            result = baseStationInfoService.selectBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("根据条件查询基站异常:selectBaseStationInfo{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("根据条件查询基站异常");
        }
        return result;
    }
}