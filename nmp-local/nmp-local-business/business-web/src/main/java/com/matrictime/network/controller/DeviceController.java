package com.matrictime.network.controller;


import com.matrictime.network.model.Result;
import com.matrictime.network.request.DeviceInfoRequest;
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

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ApiOperation(value = "设备接口",notes = "设备信息插入")
    public Result<Integer> insertDevice(@RequestBody DeviceInfoRequest deviceInfoRequest){
        Result<Integer> result = new Result<>();
        try {
            result = deviceService.insertDevice(deviceInfoRequest);
        }catch (Exception e){
            log.info("新增设备异常:insertDevice{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("新增设备异常");
        }
        return result;
    }
}