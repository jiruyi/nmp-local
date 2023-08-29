package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.ScheduleCronReq;
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


}
