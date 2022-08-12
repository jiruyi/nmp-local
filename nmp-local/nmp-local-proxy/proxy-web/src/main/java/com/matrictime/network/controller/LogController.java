package com.matrictime.network.controller;

import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.DeviceLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.SaveDeviceLogReq;
import com.matrictime.network.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志管理模块
 */

@RequestMapping(value = "/log")
@Api(value = "网关日志和用户登录明细",tags = "网关日志和用户登录明细")
@RestController
@Slf4j
public class LogController {

    @Autowired
    private LogService logService;

    /**
      * 设备日志保存
      * @title saveDeviceLog
      * @return com.matrictime.network.model.Result
      */
    @ApiOperation(value = "设备日志保存",notes = "设备日志保存")
    @RequestMapping(value = "/device/save",method = RequestMethod.POST)
    public Result saveDeviceLog(@RequestBody SaveDeviceLogReq req){
        try {
            List<DeviceLog> deviceLogs = req.getDeviceLogs();
            if (CollectionUtils.isEmpty(deviceLogs)){
                throw  new SystemException(ErrorMessageContants.DEVICE_LIST_IS_NULL_MSG);
            }
            for (DeviceLog deviceLog : deviceLogs){
                if(ObjectUtils.isEmpty(deviceLog.getDeviceIp())){
                    throw  new SystemException(ErrorMessageContants.DEVICE_IP_IS_NULL_MSG);
                }
                if(ObjectUtils.isEmpty(deviceLog.getOperDesc())){
                    throw  new SystemException(ErrorMessageContants.OPER_DESC_IS_NULL_MSG);
                }
                if(ObjectUtils.isEmpty(deviceLog.getDeviceType())){
                    throw  new SystemException(ErrorMessageContants.OPER_TYPE_IS_NULL_MSG);
                }
                //设备日志保存
                logService.saveDeviceLog(deviceLog);
            }
        }catch (Exception e){
            log.error("saveDeviceLog发生异常：{}", e.getMessage());
            return new Result(false, e.getMessage());
        }
        return new Result(true, DataConstants.SUCCESS_MSG);
    }

}
