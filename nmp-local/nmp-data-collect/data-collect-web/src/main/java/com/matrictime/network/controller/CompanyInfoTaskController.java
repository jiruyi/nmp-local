package com.matrictime.network.controller;

import com.matrictime.network.base.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.ScheduleCronReq;
import com.matrictime.network.schedule.CompanyInfoTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/8/23.
 */
@RequestMapping(value = "/scheduleCompanyInfo")
@Api(value = "小区上报", tags = "小区上报相关接口")
@RestController
@Slf4j
public class CompanyInfoTaskController {

    @Resource
    private CompanyInfoTaskService taskService;

    @ApiOperation(value = "数据采集时间间隔修改", notes = "数据采集时间间隔修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateScheduleCron(@RequestBody ScheduleCronReq scheduleCronReq) {
        try {
            //参数校验
            checkParam(scheduleCronReq);
            log.info("updateCompanyInfoScheduleCron timer:{}",scheduleCronReq.getConfigValue());
            //更新定时器的间隔时间毫秒值
            taskService.updateTimer(Long.valueOf(scheduleCronReq.getConfigValue())*1000);
            return new Result();
        }catch (Exception e){
            log.error("CompanyInfoTaskController updateScheduleCron exception:{}", e);
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
