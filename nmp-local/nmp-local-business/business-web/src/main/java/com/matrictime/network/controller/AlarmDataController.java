package com.matrictime.network.controller;

import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.AlarmDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 14:56
 * @desc
 */
@RequestMapping(value = "/alarm")
@Api(value = "网关日志和用户登录明细",tags = "网关日志和用户登录明细")
@RestController
@Slf4j
public class AlarmDataController {

    @Autowired
    private AlarmDataService alarmDataService;

    /**
      * @title acceptAlarmData
      * @param []
      * @return void
      * @description  接受代理数据推送
      * @author jiruyi
      * @create 2023/4/19 0019 14:57
      */
    @ApiOperation(value = "查询网关日志记录",notes = "查询网关日志记录")
    @RequestMapping(value = "/accept",method = RequestMethod.POST)
    public Result acceptAlarmData(List<AlarmInfo> alarmInfoList){
        return  alarmDataService.acceptAlarmData(alarmInfoList);
    }
}
