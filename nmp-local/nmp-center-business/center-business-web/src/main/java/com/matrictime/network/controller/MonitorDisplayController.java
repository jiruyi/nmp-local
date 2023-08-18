package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.QueryCompanyUserResp;
import com.matrictime.network.response.QueryDeviceResp;
import com.matrictime.network.response.queryUserResp;
import com.matrictime.network.service.MonitorDisplayService;
import com.matrictime.network.service.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "/display")
@RestController
@Slf4j
public class MonitorDisplayController {

    @Autowired
    MonitorDisplayService monitorDisplayService;

    /**
     * 查询小区用户数
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryCompanyUser",method = RequestMethod.POST)
//    @SystemLog(opermodul = "大屏展示",operDesc = "查询小区用户数",operType = "查询")
//    @RequiresPermissions("sys:basic:query")
    public Result<QueryCompanyUserResp> queryCompanyUser(@RequestBody QueryCompanyUserReq req){
        try {
            return  monitorDisplayService.queryCompanyUser(req);
        }catch (Exception e){
            log.error("MonitorDisplayController.queryCompanyUser exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 查询用户数
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryUser",method = RequestMethod.POST)
//    @SystemLog(opermodul = "大屏展示",operDesc = "查询用户数",operType = "编辑")
//    @RequiresPermissions("sys:basic:update")
    public Result<queryUserResp> updateBasicConfig(@RequestBody QueryUserReq req){
        try {
            return  monitorDisplayService.queryUser(req);
        }catch (Exception e){
            log.error("MonitorDisplayController.queryUser exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 查询设备数
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryDevice",method = RequestMethod.POST)
//    @SystemLog(opermodul = "大屏展示",operDesc = "查询设备数",operType = "查询")
//    @RequiresPermissions("sys:dict:query")
    public Result<QueryDeviceResp> queryDevice(@RequestBody QueryDeviceReq req){
        try {
            return  monitorDisplayService.queryDevice(req);
        }catch (Exception e){
            log.error("MonitorDisplayController.queryDevice exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


}
