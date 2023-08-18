package com.matrictime.network.controller;

import com.matrictime.network.base.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.ScheduleCronReq;
import com.matrictime.network.schedule.AlarmInfoTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/7/20 0020 10:22
 * @desc
 */
@RequestMapping(value = "/schedule")
@Api(value = "告警信息相关接口", tags = "告警信息相关接口")
@RestController
@Slf4j
public class ScheduleController {

    @Autowired
    private AlarmInfoTaskService alarmInfoTaskService;


    /**
      * @title updateScheduleCron
      * @param [scheduleCronReq]
      * @return com.matrictime.network.model.Result
      * @description
      * @author jiruyi
      * @create 2023/8/15 0015 9:40
      */
    @ApiOperation(value = "数据采集时间间隔修改", notes = "数据采集时间间隔修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateScheduleCron(@RequestBody  ScheduleCronReq scheduleCronReq) {
        try {
            //参数校验
            checkParam(scheduleCronReq);
            log.info("updateScheduleCron timer:{}",scheduleCronReq.getConfigValue());
            //更新定时器的间隔时间毫秒值
            alarmInfoTaskService.updateTimer(Long.valueOf(scheduleCronReq.getConfigValue())*1000);
            return new Result();
        }catch (Exception e){
            log.error("ScheduleController updateScheduleCron exception:{}", e);
            return new Result(false, e.getMessage());
        }
    }

    public void checkParam(ScheduleCronReq scheduleCronReq) {
        if (StringUtils.isEmpty(scheduleCronReq.getConfigValue())) {
            throw new SystemException("配置时间不能为空");
        }
        if (StringUtils.isEmpty(scheduleCronReq.getUnit())) {
            throw new SystemException("配置单位不能为空");
        }
    }
}
