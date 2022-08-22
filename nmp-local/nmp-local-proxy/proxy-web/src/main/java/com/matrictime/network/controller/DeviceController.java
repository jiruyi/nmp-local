package com.matrictime.network.controller;


import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.service.DeviceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 其他设备管理模块
 * @author hexu
 */
@RestController
@RequestMapping(value = "/device",method = RequestMethod.POST)
@Slf4j
public class DeviceController extends SystemBaseService {

    @Resource
    private DeviceInfoService deviceInfoService;

    /**
     * 秘钥中心信息插入
     * @param deviceInfoVo
     * @return
     */
    @RequestMapping(value = "/insertKeyCenter",method = RequestMethod.POST)
    public Result<Integer> insertKeyCenter(@RequestBody DeviceInfoVo deviceInfoVo){
        Result<Integer> result;
        try {
            deviceInfoVo.setDeviceType(DeviceTypeEnum.DISPENSER.getCode());
            result = deviceInfoService.addDeviceInfo(deviceInfoVo);
        }catch (Exception e){
            log.info("新增秘钥中心异常:insertKeyCenter{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    /**
     * 更新秘钥中心信息
     * @param deviceInfoVo
     * @return
     */
    @RequestMapping(value = "/updateKeyCenter",method = RequestMethod.POST)
    public Result<Integer> updateKeyCenter(@RequestBody DeviceInfoVo deviceInfoVo){
        Result<Integer> result;
        try {
            deviceInfoVo.setDeviceType(DeviceTypeEnum.DISPENSER.getCode());
            result = deviceInfoService.updateDeviceInfo(deviceInfoVo);
        }catch (Exception e){
            log.info("更新秘钥中心信息异常:updateKeyCenter{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 生成机信息插入
     * @param deviceInfoVo
     * @return
     */
    @RequestMapping(value = "/insertGenerator",method = RequestMethod.POST)
    public Result<Integer> insertGenerator(@RequestBody DeviceInfoVo deviceInfoVo){
        Result<Integer> result;
        try {
            deviceInfoVo.setDeviceType(DeviceTypeEnum.GENERATOR.getCode());
            result = deviceInfoService.addDeviceInfo(deviceInfoVo);
        }catch (Exception e){
            log.info("新增生成机异常:insertGenerator{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    /**
     * 更新生成机信息
     * @param deviceInfoVo
     * @return
     */
    @RequestMapping(value = "/updateGenerator",method = RequestMethod.POST)
    public Result<Integer> updateGenerator(@RequestBody DeviceInfoVo deviceInfoVo){
        Result<Integer> result;
        try {
            deviceInfoVo.setDeviceType(DeviceTypeEnum.GENERATOR.getCode());
            result = deviceInfoService.updateDeviceInfo(deviceInfoVo);
        }catch (Exception e){
            log.info("更新生成机信息:updateGenerator{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    /**
     * 缓存机信息插入
     * @param deviceInfoVo
     * @return
     */
    @RequestMapping(value = "/insertCache",method = RequestMethod.POST)
    public Result<Integer> insertCache(@RequestBody DeviceInfoVo deviceInfoVo){
        Result<Integer> result;
        try {
            deviceInfoVo.setDeviceType(DeviceTypeEnum.CACHE.getCode());
            result = deviceInfoService.addDeviceInfo(deviceInfoVo);
        }catch (Exception e){
            log.info("新增缓存机异常:insertCache{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    /**
     * 更新缓存机信息
     * @param deviceInfoVo
     * @return
     */
    @RequestMapping(value = "/updateCache",method = RequestMethod.POST)
    public Result<Integer> updateCache(@RequestBody DeviceInfoVo deviceInfoVo){
        Result<Integer> result;
        try {
            deviceInfoVo.setDeviceType(DeviceTypeEnum.CACHE.getCode());
            result = deviceInfoService.updateDeviceInfo(deviceInfoVo);
        }catch (Exception e){
            log.info("更新缓存机信息:updateCache{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

}