package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.ConfigService;
import com.matrictime.network.service.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.matrictime.network.constant.DataConstants.EDIT_RANGE_ALL;
import static com.matrictime.network.constant.DataConstants.EDIT_TYPE_UPD;


@RequestMapping(value = "/config")
@RestController
@Slf4j
/**
 * 配置相关请求接口
 */
public class ConfigController {

    @Autowired
    private ConfigService configService;
    @Autowired
    private ConfigurationService configurationService;

    /**
     * 网元参数配置查询接口（支持分页查询）
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryConfigByPages",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "网元参数配置查询接口（支持分页查询）",operType = "查询")
    @RequiresPermissions("sys:config:query")
    public Result<PageInfo> queryConfigByPages(@RequestBody QueryConfigByPagesReq req){
        try {
            return configService.queryConfigByPages(req);
        }catch (Exception e){
            log.error("ConfigController.queryConfigByPages exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
    * 网元参数配置同步
    * @author hexu
    * @param req
    * @return
    */
    @RequestMapping (value = "/sync",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "网元参数配置同步",operType = "编辑",operLevl = "2")
    @RequiresPermissions("sys:config:sync")
    public Result<SyncConfigResp> sync(@RequestBody SyncConfigReq req){
        try {
            return configService.sync(req);
        }catch (Exception e){
            log.error("ConfigController.sync exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 数据采集参数配置查询
     * @return
     */
    @RequestMapping (value = "/queryDataCollect",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "数据采集参数配置查询",operType = "查询")
    @RequiresPermissions("sys:parmDataCollect:query")
    public Result<QueryDataCollectResp> queryDataCollect(){
        try {
            return configService.queryDataCollect();
        }catch (Exception e){
            log.error("ConfigController.queryDataCollect exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 采集业务配置
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/editDataCollectConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "采集业务配置",operType = "编辑",operLevl = "2")
    @RequiresPermissions("sys:parmDataCollect:modifyConfig")
    public Result editDataCollectConfig(@RequestBody EditDataCollectConfigReq req){
        try {
            return configService.editDataCollectConfig(req);
        }catch (Exception e){
            log.error("ConfigController.editDataBusinessConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 手动上报业务数据
     * @author zhangyunjie
     * @return
     */
    @RequestMapping (value = "/reportBusinessData",method = RequestMethod.POST)
    @SystemLog(opermodul = "配置模块",operDesc = "手动上报业务数据",operType = "上报")
    public Result reportBusinessData(){
        return  configurationService.reportBusinessData();
    }
}
