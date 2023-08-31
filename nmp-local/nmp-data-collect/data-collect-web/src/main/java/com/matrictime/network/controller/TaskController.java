package com.matrictime.network.controller;

import com.matrictime.network.enums.ConfigEnum;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplConfigVo;
import com.matrictime.network.modelVo.NmplReportBusinessVo;
import com.matrictime.network.request.ScheduleCronReq;
import com.matrictime.network.request.SyncConfigReq;
import com.matrictime.network.request.TaskReq;
import com.matrictime.network.strategy.BusinessConifg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/task")
@Api(value = "终端用户", tags = "终端用户相关接口")
@RestController
@Slf4j
public class TaskController extends BusinessConifg {

    @ApiOperation(value = "手动上报", notes = "手动上报")
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public Result report(@RequestBody TaskReq taskReq) {
        try {
            if(!CollectionUtils.isEmpty(taskReq.getCodeList())){
                for (String s : taskReq.getCodeList()) {
                    configServiceMap.get(s).businessData();
                }
            }
            return new Result();
        }catch (Exception e){
            log.error("TaskController report exception:{}", e);
            return new Result(false, e.getMessage());
        }
    }

    @ApiOperation(value = "同步数据采集参数配置", notes = "同步数据采集参数配置")
    @RequestMapping(value = "/syncConfig", method = RequestMethod.POST)
    public Result updateTime(@RequestBody SyncConfigReq req) {
        try {
            if (CollectionUtils.isEmpty(req.getConfigVos())){
                throw new Exception("configVos"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            for (NmplConfigVo configVo: req.getConfigVos()){
                if (ConfigEnum.REPORT_PERIOD.getCode().equals(configVo.getConfigCode())){// 目前只处理上报周期配置
                    if (CollectionUtils.isEmpty(req.getReportBusiness())){
                        throw new Exception("reportBusiness"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                    }
                    for (NmplReportBusinessVo reportBusinessVo: req.getReportBusiness()){
                        configServiceMap.get(reportBusinessVo.getBusinessCode()).updateTimer(Long.valueOf(configVo.getConfigValue())*1000);
                    }
                }
            }
            return new Result();
        }catch (Exception e){
            log.error("TaskController syncConfig exception:{}", e);
            return new Result(false, e.getMessage());
        }
    }


}
