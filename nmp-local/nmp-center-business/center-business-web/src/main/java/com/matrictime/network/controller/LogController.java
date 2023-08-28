package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LoginDetail;
import com.matrictime.network.request.AlarmInfoRequest;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * 查询网关日志记录
     * @author jiruyi
     * @param logRequest
     * @return
     */
    @ApiOperation(value = "查询网关日志记录",notes = "查询网关日志记录")
    @SystemLog(opermodul = "日志管理模块",operDesc = "查询操作日志",operType = "查询")
    @RequiresPermissions("sys:operateLog:query")
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
     * 查询用户登录明细
     * @title queryLoinDetailList
     * @return com.matrictime.network.model.Result<com.matrictime.network.response.PageInfo<NetworkLoginDetail>>
     * @description
     * @author jiruyi
     * @create 2022/3/3 0003 15:47
     */
    @ApiOperation(value = "查询用户登录明细",notes = "查询用户登录明细")
    @SystemLog(opermodul = "日志管理模块",operDesc = "查询用户登录明细",operType = "查询")
    @RequiresPermissions("sys:loginLog:query")
    @RequestMapping(value = "/login/detail",method = RequestMethod.POST)
    public Result<PageInfo> queryLoginDetailList(@RequestBody LoginDetail loginDetail){
        try {
            return logService.queryLoginDetailList(loginDetail);
        }catch (Exception e){
            log.error("queryLogList发生异常：{}", e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }


    /**
      * @title queryAlarmInfoList
      * @param [alarmInfoRequest]
      * @return com.matrictime.network.model.Result<com.matrictime.network.response.PageInfo>
      * @description
      * @author jiruyi
      * @create 2023/8/17 0017 19:44
      */
    @ApiOperation(value = "查询用户登录明细",notes = "查询用户登录明细")
    @SystemLog(opermodul = "日志管理模块",operDesc = "查询告警日志",operType = "查询")
    @RequiresPermissions("sys:accusation:query")
    @RequestMapping(value = "/alarm/query",method = RequestMethod.POST)
    public Result<PageInfo> queryAlarmInfoList(@RequestBody AlarmInfoRequest alarmInfoRequest){
        try {
            return logService.queryAlarmInfoList(alarmInfoRequest);
        }catch (Exception e){
            log.error("queryAlarmInfoList发生异常：{}", e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }
}
