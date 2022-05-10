package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.service.DeviceService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/baseStation",method = RequestMethod.POST)
@Slf4j
public class BaseStationController {

    @Resource
    private BaseStationInfoService baseStationInfoService;

    @Resource
    private DeviceService deviceService;

    @RequiresPermissions("sys:station:save")
    @SystemLog(opermodul = "基站管理模块",operDesc = "基站插入",operType = "基站插入")
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

    @RequiresPermissions("sys:station:update")
    @SystemLog(opermodul = "基站管理模块",operDesc = "基站更新",operType = "基站更新")
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

    @RequiresPermissions("sys:station:delete")
    @SystemLog(opermodul = "基站管理模块",operDesc = "基站删除",operType = "基站删除")
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

    @RequiresPermissions("sys:station:query")
    @SystemLog(opermodul = "基站管理模块",operDesc = "根据条件基站信息查询",operType = "根据条件基站信息查询")
    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ApiOperation(value = "基站接口",notes = "根据条件基站信息查询")
    public Result<PageInfo> selectBaseStationInfo(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<PageInfo> result = new Result<>();
        try {
            result = baseStationInfoService.selectBaseStationInfo(baseStationInfoRequest);
        }catch (Exception e){
            log.info("根据条件查询基站异常:selectBaseStationInfo{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("根据条件查询基站异常");
        }
        return result;
    }

    @SystemLog(opermodul = "基站管理模块",operDesc = "根据ip获取设备Id",operType = "根据ip获取设备信息")
    @RequestMapping(value = "/selectDevice",method = RequestMethod.POST)
    public Result<StationVo> selectDevice(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<StationVo> result = new Result<>();
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        try {
            if(deviceInfoRequest == null || deviceInfoRequest.getDeviceType() == null ||
                    deviceInfoRequest.getLanIp() == null){
                return new Result<>(false,"请求参数异常");
            }
            if(StationTypeEnum.INSIDE.getCode().equals(deviceInfoRequest.getDeviceType()) ||
                    StationTypeEnum.BOUNDARY.getCode().equals(deviceInfoRequest.getDeviceType())){
                baseStationInfoRequest.setLanIp(deviceInfoRequest.getLanIp());
                result = baseStationInfoService.selectDeviceId(baseStationInfoRequest);
            }else {
                if("04".equals(deviceInfoRequest)){
                    deviceInfoRequest.setDeviceType("01");
                }
                if("05".equals(deviceInfoRequest)){
                    deviceInfoRequest.setDeviceType("02");
                }
                if("06".equals(deviceInfoRequest)){
                    deviceInfoRequest.setDeviceType("03");
                }
                result = deviceService.selectDeviceId(deviceInfoRequest);
            }
            if(result.getResultObj() == null){
                return new Result<>(false,"dataBase中没有该数据");
            }
        }catch (Exception e){
            log.info("根据条件查询基站异常:selectDevice{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("根据条件查询基站异常");
        }
        return result;
    }

}