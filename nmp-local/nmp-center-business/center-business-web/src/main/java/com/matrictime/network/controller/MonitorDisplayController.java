package com.matrictime.network.controller;

import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.QueryCompanyUserResp;
import com.matrictime.network.response.QueryDeviceResp;
import com.matrictime.network.response.QueryMapInfoResp;
import com.matrictime.network.response.queryUserResp;
import com.matrictime.network.service.MonitorDisplayService;
import com.matrictime.network.service.SystemConfigService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 指控中心数据展示模块
 */
@RequestMapping(value = "/display")
@RestController
@Slf4j
public class MonitorDisplayController {

    @Autowired
    MonitorDisplayService monitorDisplayService;

    /**
     * 查询小区用户数
     * @return
     */
    @RequestMapping(value = "/queryCompanyUser",method = RequestMethod.POST)
//    @SystemLog(opermodul = "大屏展示",operDesc = "查询小区用户数",operType = "查询")
//    @RequiresPermissions("sys:basic:query")
    public Result<QueryCompanyUserResp> queryCompanyUser(){
        try {
            return  monitorDisplayService.queryCompanyUser();
        }catch (Exception e){
            log.error("MonitorDisplayController.queryCompanyUser exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 查询用户数(小区内)
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryUserUnit",method = RequestMethod.POST)
//    @SystemLog(opermodul = "大屏展示",operDesc = "查询用户数",operType = "编辑")
//    @RequiresPermissions("sys:basic:update")
    public Result<queryUserResp> queryUserUnit(@RequestBody QueryUserReq req){
        try {
            if (ParamCheckUtil.checkVoStrBlank(req.getCompanyNetworkId())){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            return  monitorDisplayService.queryUser(req);
        }catch (Exception e){
            log.error("MonitorDisplayController.queryUserUnit exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 查询用户数
     * @return
     */
    @RequestMapping(value = "/queryUser",method = RequestMethod.POST)
//    @SystemLog(opermodul = "大屏展示",operDesc = "查询用户数",operType = "编辑")
//    @RequiresPermissions("sys:basic:update")
    public Result<queryUserResp> queryUser(){
        try {
            QueryUserReq req = new QueryUserReq();
            return  monitorDisplayService.queryUser(req);
        }catch (Exception e){
            log.error("MonitorDisplayController.queryUser exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 查询设备数(小区内)
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryDeviceUnit",method = RequestMethod.POST)
//    @SystemLog(opermodul = "大屏展示",operDesc = "查询设备数",operType = "查询")
//    @RequiresPermissions("sys:dict:query")
    public Result<QueryDeviceResp> queryDeviceUnit(@RequestBody QueryDeviceReq req){
        try {
            if (ParamCheckUtil.checkVoStrBlank(req.getCompanyNetworkId())){
                throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            return  monitorDisplayService.queryDevice(req);
        }catch (Exception e){
            log.error("MonitorDisplayController.queryDeviceUnit exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 查询设备数
     * @return
     */
    @RequestMapping(value = "/queryDevice",method = RequestMethod.POST)
//    @SystemLog(opermodul = "大屏展示",operDesc = "查询设备数",operType = "查询")
//    @RequiresPermissions("sys:dict:query")
    public Result<QueryDeviceResp> queryDevice(){
        try {
            QueryDeviceReq req = new QueryDeviceReq();
            return  monitorDisplayService.queryDevice(req);
        }catch (Exception e){
            log.error("MonitorDisplayController.queryDevice exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


    /**
     * 查询地图信息
     * @return
     */
    @RequestMapping(value = "/queryMapInfo",method = RequestMethod.POST)
//    @SystemLog(opermodul = "大屏展示",operDesc = "查询地图信息",operType = "查询")
//    @RequiresPermissions("sys:dict:query")
    public Result<QueryMapInfoResp> queryMapInfo(){
        try {
            return  monitorDisplayService.queryMapInfo();
        }catch (Exception e){
            log.error("MonitorDisplayController.queryMapInfo exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


}
