package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.model.DeviceLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.model.LoginDetail;
import com.matrictime.network.request.LoginRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/2 0002 15:39
 * @desc
 */

@RequestMapping(value = "/log")
@Api(value = "网关日志和用户登录明细",tags = "网关日志和用户登录明细")
@RestController
@Slf4j
public class LogController {

    @Autowired
    private LogService logService;

    @ApiOperation(value = "查询网关日志记录",notes = "查询网关日志记录")
    @SystemLog(opermodul = "日志管理模块",operDesc = "查询网关日志记录",operType = "查询")
    @RequestMapping(value = "/oper/query",method = RequestMethod.POST)
    public Result<PageInfo> queryLogList(@RequestBody LogRequest logRequest){
        try {
            return  logService.queryNetworkLogList(logRequest);
        }catch (Exception e){
            log.error("queryLogList发生异常：{}", e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }

    /**
     * @title queryLoinDetailList
     * @param [logRequest]
     * @return com.matrictime.network.model.Result<com.matrictime.network.response.PageInfo<NetworkLoginDetail>>
     * @description
     * @author jiruyi
     * @create 2022/3/3 0003 15:47
     */
    @ApiOperation(value = "查询用户登录明细",notes = "查询用户登录明细")
    @SystemLog(opermodul = "日志管理模块",operDesc = "查询用户登录明细",operType = "查询")
    @RequestMapping(value = "/login/detail",method = RequestMethod.POST)
    public Result<PageInfo> queryLoginDetailList(@RequestBody LoginDetail loginDetail){
        try {
           return logService.queryLoginDetailList(loginDetail);
        }catch (Exception e){
            log.error("queryLogList发生异常：{}", e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }

    @ApiOperation(value = "设备日志保存",notes = "设备日志保存")
    @SystemLog(opermodul = "日志管理模块",operDesc = "设备日志保存",operType = "插入")
    @RequestMapping(value = "/device/save",method = RequestMethod.POST)
    public Result saveDeviceLog(@RequestBody DeviceLog deviceLog){
        try {
            if(ObjectUtils.isEmpty(deviceLog.getDeviceId())){
                throw  new SystemException(ErrorMessageContants.DEVICE_ID_IS_NULL_MSG);
            }
            if(ObjectUtils.isEmpty(deviceLog.getOperDesc())){
                throw  new SystemException(ErrorMessageContants.OPER_DESC_IS_NULL_MSG);
            }
            if(ObjectUtils.isEmpty(deviceLog.getDeviceType())){
                throw  new SystemException(ErrorMessageContants.OPER_TYPE_IS_NULL_MSG);
            }
            //设备日志保存
            logService.saveDeviceLog(deviceLog);
        }catch (Exception e){
            log.error("saveDeviceLog发生异常：{}", e.getMessage());
            return new Result(false, e.getMessage());
        }
        return new Result(true, DataConstants.SUCCESS_MSG);
    }


    /**
     * @title ApiOperation
     * @param []
     * @return
     * @description 设备日志查询
     * @author jiruyi
     * @create 2022/3/7 0007 11:38
     */
    @ApiOperation(value = "设备日志查询",notes = "设备日志查询")
    @SystemLog(opermodul = "日志管理模块",operDesc = "设备日志查询",operType = "插入")
    @RequestMapping(value = "/device/query",method = RequestMethod.POST)
    public Result queryDeviceLog(@RequestBody DeviceLog deviceLog){
        try {
            return logService.queryDeviceLogList(deviceLog);
        }catch (Exception e){
            log.error("queryDeviceLog发生异常：{}", e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }
}
