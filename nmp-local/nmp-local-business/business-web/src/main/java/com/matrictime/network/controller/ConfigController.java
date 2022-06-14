package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
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
     * 编辑配置
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/editConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "配置模块",operDesc = "编辑配置记录",operType = "编辑")
    @RequiresPermissions("sys:parm:update")
    public Result<EditConfigResp> editConfig(@RequestBody EditConfigReq req){
        try {
            return  configService.editConfig(req);
        }catch (Exception e){
            log.error("ConfigController.editConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


    /**
     * 查询接口（支持分页查询）
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryConfigByPages",method = RequestMethod.POST)
    @SystemLog(opermodul = "配置模块",operDesc = "查询接口（支持分页查询）记录",operType = "查询")
    @RequiresPermissions("sys:parm:query")
    public Result<PageInfo> queryConfigByPages(@RequestBody QueryConfigByPagesReq req){
        try {
            return  configService.queryConfigByPages(req);
        }catch (Exception e){
            log.error("ConfigController.queryConfigByPages exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


    /**
     * 恢复默认接口（支持全量恢复,同时需要同步数据）
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/resetDefaultConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "配置模块",operDesc = "恢复默认接口记录",operType = "恢复默认")
    @RequiresPermissions("sys:parm:reset")
    public Result<ResetDefaultConfigResp> resetDefaultConfig(@RequestBody ResetDefaultConfigReq req){
        try {
            return  configService.resetDefaultConfig(req);
        }catch (Exception e){
            log.error("ConfigController.resetDefaultConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


    /**
     * 同步配置(支持全量同步)
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/syncConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "配置模块",operDesc = "同步配置接口（支持全量同步）记录",operType = "同步配置")
    @RequiresPermissions("sys:parm:synchro")
    public Result<SyncConfigResp> syncConfig(@RequestBody SyncConfigReq req){
        try {
            return  configService.syncConfig(req);
        }catch (Exception e){
            log.error("ConfigController.syncConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 更新设备配置信息
     * @author zhangyunjie
     * @param configurationReq
     * @return
     */
    @RequestMapping (value = "/insertOrUpdate",method = RequestMethod.POST)
    @SystemLog(opermodul = "配置模块",operDesc = "更新设备配置信息",operType = "更新")
    public Result insertOrUpdate(@RequestBody ConfigurationReq configurationReq){
        return  configurationService.insertOrUpdate(configurationReq);
    }



}
