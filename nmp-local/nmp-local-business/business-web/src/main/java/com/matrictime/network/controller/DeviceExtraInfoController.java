package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceExtraInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.DeviceInfoExtResponse;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.service.DeviceExtraInfoService;
import com.matrictime.network.service.DeviceService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 主备管理
 * @author wangqiang
 */
@RestController
@RequestMapping(value = "/deviceExtraInfo",method = RequestMethod.POST)
@Slf4j
public class DeviceExtraInfoController {

    @Resource
    private DeviceExtraInfoService deviceExtraInfoService;

    @Resource
    private BaseStationInfoService baseStationInfoService;

    @Resource
    private DeviceService deviceService;

    /**
     * 备用设备插入
     * @param deviceExtraInfoRequest
     * @return
     */
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

    /**
     * 备用设备更新
     * @param deviceExtraInfoRequest
     * @return
     */
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

    /**
     * 备用设备删除
     * @param deviceExtraInfoRequest
     * @return
     */
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

    /**
     * 备用设备查询
     * @param deviceExtraInfoRequest
     * @return
     */
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

    @SystemLog(opermodul = "主备管理",operDesc = "主设备查询",operType = "主设备查询")
    @ApiOperation(value = "主设备查询",notes = "主设备查询")
    @RequestMapping(value = "/selectRelDecive",method = RequestMethod.POST)
    public Result<DeviceInfoExtResponse> selectDevice(@RequestBody DeviceExtraInfoRequest deviceExtraInfoRequest){
        Result<DeviceInfoExtResponse> result = new Result<>();
        try {
            result = deviceExtraInfoService.selectDevices(deviceExtraInfoRequest);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.info("主设备查询:selectRelDecive{}",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/selectBaseStation",method = RequestMethod.POST)
    public Result<BaseStationInfoResponse> selectBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        Result<BaseStationInfoResponse> result = new Result<>();
        try {
            result = baseStationInfoService.selectForRoute(baseStationInfoRequest);
        }catch (Exception e){
            log.info("链路查询基站设备异常:selectBaseStation{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("基站设备异常");
        }
        return result;
    }


    /**
     * 链路查询设备
     * @param deviceInfoRequest
     * @return
     */
    @RequestMapping(value = "/selectDevice",method = RequestMethod.POST)
    public Result<DeviceResponse> selectDevice(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<DeviceResponse> result = new Result<>();
        try {
            result = deviceService.selectDeviceForLinkRelation(deviceInfoRequest);
        }catch (Exception e){
            log.info("链路查询设备异常:selectDevice{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("查询设备异常");
        }
        return result;
    }
}































