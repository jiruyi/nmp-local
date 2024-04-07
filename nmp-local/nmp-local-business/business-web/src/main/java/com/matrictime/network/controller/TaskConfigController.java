package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.SyncTaskReq;
import com.matrictime.network.request.QueryTaskReq;
import com.matrictime.network.response.SyncTaskResp;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.TaskConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.matrictime.network.constant.DataConstants.EDIT_TYPE_UPD;

/**
 * 通用网元定时任务配置
 */
@RequestMapping(value = "/task")
@RestController
@Slf4j
public class TaskConfigController {

    @Autowired
    private TaskConfigService taskConfigService;

    /**
     * 分页查询
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryByPages",method = RequestMethod.POST)
    @SystemLog(opermodul = "通用网元定时任务配置",operDesc = "查询接口（支持分页查询）",operType = "查询")
    @RequiresPermissions("sys:taskConfig:query")
    public Result<PageInfo> queryByPages(@RequestBody QueryTaskReq req){
        try {
            return taskConfigService.queryTaskByPages(req);
        }catch (Exception e){
            log.error("TaskConfigController.queryByPages exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 定时任务配置同步
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/sync",method = RequestMethod.POST)
    @SystemLog(opermodul = "通用网元定时任务配置",operDesc = "同步接口",operType = "同步",operLevl = "2")
    @RequiresPermissions("sys:taskConfig:sync")
    public Result<SyncTaskResp> sync(@RequestBody SyncTaskReq req){
        try {
            return taskConfigService.sync(req);
        }catch (Exception e){
            log.error("ConfigController.sync exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
