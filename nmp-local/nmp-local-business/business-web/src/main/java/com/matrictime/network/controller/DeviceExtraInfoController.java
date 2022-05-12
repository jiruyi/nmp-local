package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.DeviceExtraInfoRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DeviceExtraInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/deviceExtraInfo",method = RequestMethod.POST)
@Slf4j
public class DeviceExtraInfoController {

    @Resource
    private DeviceExtraInfoService deviceExtraInfoService;

    @RequiresPermissions("sys:deviceExtra:save")
    @SystemLog(opermodul = "主备管理",operDesc = "备用设备插入",operType = "备用设备插入")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ApiOperation(value = "主备管理接口",notes = "备用设备插入")
    public Result<Integer> insertDeviceExtra(@RequestBody DeviceExtraInfoRequest deviceExtraInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceExtraInfoService.insert(deviceExtraInfoRequest);
        }catch (Exception e){
            log.info("备用设备插入异常:insertDeviceExtra{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("备用设备插入异常");
        }
        return result;
    }

    @RequiresPermissions("sys:deviceExtra:update")
    @SystemLog(opermodul = "主备管理",operDesc = "备用设备更新",operType = "备用设备更新")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "备用设备更新",notes = "备用设备更新")
    public Result<Integer> updateDeviceExtra(@RequestBody DeviceExtraInfoRequest deviceExtraInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceExtraInfoService.update(deviceExtraInfoRequest);
        }catch (Exception e){
            log.info("备用设备插入异常:updateDeviceExtra{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("备用设备更新异常");
        }
        return result;
    }

    @RequiresPermissions("sys:deviceExtra:delete")
    @SystemLog(opermodul = "主备管理",operDesc = "备用设备删除",operType = "备用设备删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "备用设备删除",notes = "备用设备删除")
    public Result<Integer> deleteDeviceExtra(@RequestBody DeviceExtraInfoRequest deviceExtraInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceExtraInfoService.delete(deviceExtraInfoRequest);
        }catch (Exception e){
            log.info("备用设备删除异常:updateDeviceExtra{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("备用设备删除异常");
        }
        return result;
    }

    @RequiresPermissions("sys:deviceExtra:query")
    @SystemLog(opermodul = "主备管理",operDesc = "备用设备查询",operType = "备用设备查询")
    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ApiOperation(value = "备用设备删除",notes = "备用设备查询")
    public Result<PageInfo> selectDeviceExtra(@RequestBody DeviceExtraInfoRequest deviceExtraInfoRequest){
        Result<PageInfo> result = new Result<>();
        try {
            result = deviceExtraInfoService.select(deviceExtraInfoRequest);
        }catch (Exception e){
            log.info("备用设备删除异常:updateDeviceExtra{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("备用设备删除异常");
        }
        return result;
    }
}































