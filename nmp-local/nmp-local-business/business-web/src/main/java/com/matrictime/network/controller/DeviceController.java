package com.matrictime.network.controller;


import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DeviceService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/device",method = RequestMethod.POST)
@Slf4j
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    @SystemLog(opermodul = "分发机管理模块",operDesc = "分发机信息插入",operType = "分发机信息插入")
    @RequestMapping(value = "/insertDispenser",method = RequestMethod.POST)
    @ApiOperation(value = "分发机接口",notes = "分发机信息插入")
    public Result<Integer> insertDispenser(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(DeviceTypeEnum.DISPENSER.getCode());
            result = deviceService.insertDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("新增分发机异常:insertDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("新增分发机异常");
        }
        return result;
    }

    @SystemLog(opermodul = "分发机管理模块",operDesc = "删除分发机信息",operType = "删除分发机信息")
    @RequestMapping(value = "/deleteDispenser",method = RequestMethod.POST)
    @ApiOperation(value = "分发机接口",notes = "删除分发机信息")
    public Result<Integer> deleteDispenser(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceService.deleteDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("删除设备信息异常:deleteDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("删除分发机信息异常");
        }
        return result;
    }

    @SystemLog(opermodul = "分发机管理模块",operDesc = "更新分发机信息",operType = "更新分发机信息")
    @RequestMapping(value = "/updateDispenser",method = RequestMethod.POST)
    @ApiOperation(value = "更新分发机接口",notes = "更新分发机信息")
    public Result<Integer> updateDispenser(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceService.updateDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("更新分发机信息异常:updateDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("更新分发机信息异常");
        }
        return result;
    }

    @SystemLog(opermodul = "分发机管理模块",operDesc = "查询分发机信息",operType = "查询分发机信息")
    @RequestMapping(value = "/selectDispenser",method = RequestMethod.POST)
    @ApiOperation(value = "查询分发机接口",notes = "查询分发机信息")
    public Result<PageInfo> selectDispenser(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<PageInfo> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(DeviceTypeEnum.DISPENSER.getCode());
            result = deviceService.selectDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("查询分发机信息异常:selectDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("查询分发机信息异常");
        }
        return result;
    }

    @SystemLog(opermodul = "生成机管理模块",operDesc = "生成机信息插入",operType = "生成机信息插入")
    @RequestMapping(value = "/insertGenerator",method = RequestMethod.POST)
    @ApiOperation(value = "生成机接口",notes = "生成机信息插入")
    public Result<Integer> insertGenerator(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(DeviceTypeEnum.GENERATOR.getCode());
            result = deviceService.insertDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("新增生成机异常:insertDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("新增生成机异常");
        }
        return result;
    }

    @SystemLog(opermodul = "生成机管理模块",operDesc = "删除生成机信息",operType = "删除生成机信息")
    @RequestMapping(value = "/deleteGenerator",method = RequestMethod.POST)
    @ApiOperation(value = "生成机接口",notes = "删除生成机信息")
    public Result<Integer> deleteGenerator(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceService.deleteDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("删除生成机信息:deleteGenerator{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("删除生成机信息异常");
        }
        return result;
    }

    @SystemLog(opermodul = "生成机管理模块",operDesc = "更新生成机信息",operType = "更新生成机信息")
    @RequestMapping(value = "/updateGenerator",method = RequestMethod.POST)
    @ApiOperation(value = "更新生成机接口",notes = "更新生成机信息")
    public Result<Integer> updateGenerator(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceService.updateDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("更新生成机信息:updateDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("更新生成机信息异常");
        }
        return result;
    }

    @SystemLog(opermodul = "生成机管理模块",operDesc = "查询生成机信息",operType = "查询生成机信息")
    @RequestMapping(value = "/selectGenerator",method = RequestMethod.POST)
    @ApiOperation(value = "查询生成机接口",notes = "查询生成机信息")
    public Result<PageInfo> selectGenerator(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<PageInfo> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(DeviceTypeEnum.GENERATOR.getCode());
            result = deviceService.selectDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("查询生成机信息异常:updateDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("查询生成机信息异常");
        }
        return result;
    }

    @SystemLog(opermodul = "缓存机管理模块",operDesc = "缓存机信息插入",operType = "缓存机信息插入")
    @RequestMapping(value = "/insertCache",method = RequestMethod.POST)
    @ApiOperation(value = "缓存机接口",notes = "缓存机信息插入")
    public Result<Integer> insertCache(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(DeviceTypeEnum.CACHE.getCode());
            result = deviceService.insertDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("新增缓存机异常:insertDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("新增缓存机异常");
        }
        return result;
    }

    @SystemLog(opermodul = "缓存机管理模块",operDesc = "删除缓存机信息",operType = "删除缓存机信息")
    @RequestMapping(value = "/deleteCache",method = RequestMethod.POST)
    @ApiOperation(value = "缓存机接口",notes = "删除缓存机信息")
    public Result<Integer> deleteCache(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceService.deleteDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("删除缓存机信息:deleteCache{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("删除缓存机信息异常");
        }
        return result;
    }

    @SystemLog(opermodul = "缓存机管理模块",operDesc = "更新缓存机信息",operType = "更新缓存机信息")
    @RequestMapping(value = "/updateCache",method = RequestMethod.POST)
    @ApiOperation(value = "更新缓存机接口",notes = "更新缓存机信息")
    public Result<Integer> updateCache(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceService.updateDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("更新缓存机信息:updateDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("更新缓存机信息异常");
        }
        return result;
    }

    @SystemLog(opermodul = "缓存机管理模块",operDesc = "查询缓存机信息",operType = "查询缓存机信息")
    @RequestMapping(value = "/selectCache",method = RequestMethod.POST)
    @ApiOperation(value = "查询缓存机接口",notes = "查询缓存机信息")
    public Result<PageInfo> selectCache(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<PageInfo> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(DeviceTypeEnum.CACHE.getCode());
            result = deviceService.selectDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("查询缓存机信息异常:selectCache{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("查询缓存机信息异常");
        }
        return result;
    }


























}