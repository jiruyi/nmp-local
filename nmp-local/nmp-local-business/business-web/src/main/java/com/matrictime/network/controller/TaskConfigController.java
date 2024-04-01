package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.request.EditTaskReq;
import com.matrictime.network.request.QueryTaskReq;
import com.matrictime.network.response.EditConfigResp;
import com.matrictime.network.response.EditTaskResp;
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
    @RequiresPermissions("sys:task:query")
    public Result<PageInfo> queryByPages(@RequestBody QueryTaskReq req){
        try {
            return taskConfigService.queryTaskByPages(req);
        }catch (Exception e){
            log.error("TaskConfigController.queryByPages exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 接入基站编辑配置
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/update",method = RequestMethod.POST)
    @SystemLog(opermodul = "通用网元定时任务配置",operDesc = "编辑接口",operType = "编辑",operLevl = "2")
    @RequiresPermissions("sys:task:update")
    public Result<EditTaskResp> update(@RequestBody EditTaskReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            return taskConfigService.edit(req);
        }catch (Exception e){
            log.error("ConfigController.editInStationConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
