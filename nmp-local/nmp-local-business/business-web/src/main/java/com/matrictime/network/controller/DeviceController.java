package com.matrictime.network.controller;


import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.BaseStationCountRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.response.CountBaseStationResponse;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DeviceService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 分发机管理模块
 * @author wangqiang
 */
@RestController
@RequestMapping(value = "/device",method = RequestMethod.POST)
@Slf4j
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    /**
     * 分发机信息插入
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:dispenser:save")
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

    /**
     * 删除分发机信息
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:dispenser:delete")
    @SystemLog(opermodul = "分发机管理模块",operDesc = "删除分发机信息",operType = "删除分发机信息",operLevl = "2")
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

    /**
     * 更新分发机信息
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:dispenser:update")
    @SystemLog(opermodul = "分发机管理模块",operDesc = "更新分发机信息",operType = "更新分发机信息",operLevl = "2")
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

    /**
     * 查询分发机信息
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:dispenser:query")
    @SystemLog(opermodul = "分发机管理模块",operDesc = "查询分发机信息",operType = "查询分发机信息")
    @RequestMapping(value = "/selectDispenser",method = RequestMethod.POST)
    @ApiOperation(value = "查询分发机接口",notes = "查询分发机信息")
    public Result<PageInfo> selectDispenser(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<PageInfo> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(DeviceTypeEnum.DISPENSER.getCode());
            result = deviceService.selectDeviceALl(deviceInfoRequest);
        }catch (Exception e){
            log.info("查询分发机信息异常:selectDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("查询分发机信息异常");
        }
        return result;
    }

    /**
     * 生成机信息插入
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:generator:save")
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

    /**
     * 删除生成机信息
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:generator:delete")
    @SystemLog(opermodul = "生成机管理模块",operDesc = "删除生成机信息",operType = "删除生成机信息",operLevl = "2")
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

    /**
     * 更新生成机信息
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:generator:update")
    @SystemLog(opermodul = "生成机管理模块",operDesc = "更新生成机信息",operType = "更新生成机信息",operLevl = "2")
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

    /**
     * 查询生成机信息
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:generator:query")
    @SystemLog(opermodul = "生成机管理模块",operDesc = "查询生成机信息",operType = "查询生成机信息")
    @RequestMapping(value = "/selectGenerator",method = RequestMethod.POST)
    @ApiOperation(value = "查询生成机接口",notes = "查询生成机信息")
    public Result<PageInfo> selectGenerator(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<PageInfo> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(DeviceTypeEnum.GENERATOR.getCode());
            result = deviceService.selectDeviceALl(deviceInfoRequest);
        }catch (Exception e){
            log.info("查询生成机信息异常:updateDispenser{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("查询生成机信息异常");
        }
        return result;
    }

    /**
     * 缓存机信息插入
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:cache:save")
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

    /**
     * 删除缓存机信息
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:cache:delete")
    @SystemLog(opermodul = "缓存机管理模块",operDesc = "删除缓存机信息",operType = "删除缓存机信息",operLevl = "2")
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

    /**
     * 更新缓存机信息
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:cache:update")
    @SystemLog(opermodul = "缓存机管理模块",operDesc = "更新缓存机信息",operType = "更新缓存机信息",operLevl = "2")
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

    /**
     * 查询缓存机信息
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:cache:query")
    @SystemLog(opermodul = "缓存机管理模块",operDesc = "查询缓存机信息",operType = "查询缓存机信息")
    @RequestMapping(value = "/selectCache",method = RequestMethod.POST)
    @ApiOperation(value = "查询缓存机接口",notes = "查询缓存机信息")
    public Result<PageInfo> selectCache(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<PageInfo> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(DeviceTypeEnum.CACHE.getCode());
            result = deviceService.selectDeviceALl(deviceInfoRequest);
        }catch (Exception e){
            log.info("查询缓存机信息异常:selectCache{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("查询缓存机信息异常");
        }
        return result;
    }

    /**
     * 数据采集插入
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:dataCollect:insert")
    @SystemLog(opermodul = "数据采集管理模块",operDesc = "数据采集插入",operType = "数据采集插入")
    @RequestMapping(value = "/insertDataBase",method = RequestMethod.POST)
    @ApiOperation(value = "数据采集接口",notes = "数据采集插入")
    public Result<Integer> insertDataBase(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(com.matrictime.network.enums.DeviceTypeEnum.DATA_BASE.getCode());
            result = deviceService.insertDataBase(deviceInfoRequest);
        }catch (Exception e){
            log.info("数据采集插入异常:insertDataBase{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("数据采集插入异常");
        }
        return result;
    }

    /**
     * 删除采集删除
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:datacollect:delete")
    @SystemLog(opermodul = "数据采集管理模块",operDesc = "数据采集删除",operType = "数据采集删除")
    @RequestMapping(value = "/deleteDataBase",method = RequestMethod.POST)
    @ApiOperation(value = "数据采集接口",notes = "数据采集删除")
    public Result<Integer> deleteDataBase(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceService.deleteDataBase(deviceInfoRequest);
        }catch (Exception e){
            log.info("数据采集删除异常:deleteDataBase{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("数据采集删除异常");
        }
        return result;
    }

    /**
     * 删除采集更新
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:dataCollect:modify")
    @SystemLog(opermodul = "数据采集管理模块",operDesc = "数据采集更新",operType = "数据采集更新")
    @RequestMapping(value = "/updateDataBase",method = RequestMethod.POST)
    @ApiOperation(value = "数据采集接口",notes = "数据采集更新")
    public Result<Integer> updateDataBase(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(com.matrictime.network.enums.DeviceTypeEnum.DATA_BASE.getCode());
            result = deviceService.updateDataBase(deviceInfoRequest);
        }catch (Exception e){
            log.info("数据采集更新异常:updateDataBase{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("数据采集更新异常");
        }
        return result;
    }

    /**
     * 查询采集设备信息
     * @param deviceInfoRequest
     * @return
     */
    @RequiresPermissions("sys:datacollect:query")
    @SystemLog(opermodul = "缓存机管理模块",operDesc = "查询采集设备信息",operType = "查询采集设备信息")
    @RequestMapping(value = "/selectDataBase",method = RequestMethod.POST)
    @ApiOperation(value = "查询采集设备信息接口",notes = "查询采集设备信息")
    public Result<PageInfo> selectDataBase(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<PageInfo> result = new Result<>();
        try {
            deviceInfoRequest.setDeviceType(com.matrictime.network.enums.DeviceTypeEnum.DATA_BASE.getCode());
            result = deviceService.selectDeviceALl(deviceInfoRequest);
        }catch (Exception e){
            log.info("查询采集设备信息异常:selectDataBase{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("查询采集设备信息异常");
        }
        return result;
    }

    /**
     * 查询非基站激活设备
     * @param deviceInfoRequest
     * @return
     */
    @SystemLog(opermodul = "非基站管理模块",operDesc = "查询非基站激活设备",operType = "查询")
    @RequestMapping(value = "/selectActiveDevice",method = RequestMethod.POST)
    public Result<DeviceResponse> selectDevice(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<DeviceResponse> result = new Result<>();
        try {
            result = deviceService.selectActiveDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("查询非基站激活设备异常:selectDevice{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("系统异常，请稍后再试");
        }
        return result;
    }

    /**
     * 查询密钥生成机总数
     * @param deviceInfoRequest
     * @return
     */
    @SystemLog(opermodul = "非基站管理模块",operDesc = "查询基站总数",operType = "查询")
    @RequestMapping(value = "/countBaseStation",method = RequestMethod.POST)
    public Result<CountBaseStationResponse> countBaseStation(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<CountBaseStationResponse> result = new Result<>();
        try {
            if(StringUtils.isEmpty(deviceInfoRequest.getRelationOperatorId()) ||
                    StringUtils.isEmpty(deviceInfoRequest.getDeviceType())){
                CountBaseStationResponse countBaseStationResponse = new CountBaseStationResponse();
                Result<CountBaseStationResponse> responseResult = new Result<>();
                responseResult.setResultObj(countBaseStationResponse);
                responseResult.setSuccess(true);
                return responseResult;
            }
            result = deviceService.countBaseStation(deviceInfoRequest);
        }catch (Exception e){
            log.info("countBaseStation:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("系统异常，请稍后再试");
        }
        return result;
    }

    /**
     * 更新基站下的用户数
     * @param baseStationCountRequest
     * @return
     */
    @SystemLog(opermodul = "更新基站下的用户数",operDesc = "更新基站下的用户数",operType = "更新")
    @RequestMapping(value = "/updateConnectCount",method = RequestMethod.POST)
    public Result<Integer> updateConnectCount(@RequestBody BaseStationCountRequest baseStationCountRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceService.updateConnectCount(baseStationCountRequest);
        }catch (Exception e){
            log.info("updateConnectCount:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("系统异常，请稍后再试");
        }
        return result;
    }























}