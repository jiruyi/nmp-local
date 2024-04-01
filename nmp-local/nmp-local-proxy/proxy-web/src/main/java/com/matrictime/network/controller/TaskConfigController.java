package com.matrictime.network.controller;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditTaskConfigReq;
import com.matrictime.network.service.TaskConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务配置模块
 * @author hexu
 */
@RestController
@RequestMapping(value = "/task",method = RequestMethod.POST)
@Slf4j
public class TaskConfigController extends SystemBaseService {

    @Autowired
    private TaskConfigService taskConfigService;

    @RequestMapping (value = "/sync",method = RequestMethod.POST)
    public Result syncConfig(@RequestBody EditTaskConfigReq req){
        try {
            return taskConfigService.update(req.getTaskConfigVos());
        }catch (Exception e){
            log.error("TaskConfigController.sync exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
