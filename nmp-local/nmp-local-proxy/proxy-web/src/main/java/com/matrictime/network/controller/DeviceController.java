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
     * 设备信息插入
     * @param deviceInfoVo
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result<Integer> insert(@RequestBody DeviceInfoVo deviceInfoVo){
        Result<Integer> result;
        try {
            result = deviceInfoService.addDeviceInfo(deviceInfoVo);
        }catch (Exception e){
            log.info("新增设备信息异常:insert{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    /**
     * 更新设备信息
     * @param deviceInfoVo
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result<Integer> update(@RequestBody DeviceInfoVo deviceInfoVo){
        Result<Integer> result;
        try {
            result = deviceInfoService.updateDeviceInfo(deviceInfoVo);
        }catch (Exception e){
            log.info("更新设备信息异常:update{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


}