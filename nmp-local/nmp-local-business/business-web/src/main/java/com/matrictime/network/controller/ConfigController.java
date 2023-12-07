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
     * 接入基站参数配置查询接口（支持分页查询）
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryInStationConfigByPages",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "接入基站参数配置查询接口（支持分页查询）",operType = "查询")
    @RequiresPermissions("sys:parmInStation:query")
    public Result<PageInfo> queryInStationConfigByPages(@RequestBody QueryConfigByPagesReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.STATION_INSIDE.getCode());
            return configService.queryConfigByPages(req);
        }catch (Exception e){
            log.error("ConfigController.queryInStationConfigByPages exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 边界基站参数配置查询接口（支持分页查询）
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryBoundaryStationConfigByPages",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "边界基站参数配置查询接口（支持分页查询）",operType = "查询")
    @RequiresPermissions("sys:parmBoundaryStation:query")
    public Result<PageInfo> queryBoundaryStationConfigByPages(@RequestBody QueryConfigByPagesReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.STATION_BOUNDARY.getCode());
            return configService.queryConfigByPages(req);
        }catch (Exception e){
            log.error("ConfigController.queryBoundaryStationConfigByPages exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 密钥中心参数配置查询接口（支持分页查询）
     * @param req
     * @return
     */
    @RequestMapping (value = "/querySecretConfigByPages",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "密钥中心参数配置查询接口（支持分页查询）",operType = "查询")
    @RequiresPermissions("sys:parmSecret:query")
    public Result<PageInfo> querySecretConfigByPages(@RequestBody QueryConfigByPagesReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.DEVICE_DISPENSER.getCode());
            return configService.queryConfigByPages(req);
        }catch (Exception e){
            log.error("ConfigController.querySecretConfigByPages exception:{}",e.getMessage());
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
     * 接入基站编辑配置
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/editInStationConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "接入基站编辑配置",operType = "编辑",operLevl = "2")
    @RequiresPermissions("sys:parmInStation:modify")
    public Result<EditConfigResp> editInStationConfig(@RequestBody EditConfigReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            return configService.editConfig(req);
        }catch (Exception e){
            log.error("ConfigController.editInStationConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 边界基站编辑配置
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/editBoundaryStationConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "边界基站编辑配置",operType = "编辑",operLevl = "2")
    @RequiresPermissions("sys:parmBoundaryStation:modify")
    public Result<EditConfigResp> editBoundaryStationConfig(@RequestBody EditConfigReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            return configService.editConfig(req);
        }catch (Exception e){
            log.error("ConfigController.editBoundaryStationConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 密钥中心编辑配置
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/editSecretConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "密钥中心编辑配置",operType = "编辑",operLevl = "2")
    @RequiresPermissions("sys:parmSecret:modify")
    public Result<EditConfigResp> editSecretConfig(@RequestBody EditConfigReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            return configService.editConfig(req);
        }catch (Exception e){
            log.error("ConfigController.editSecretConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 数据采集启用/禁用
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/dataCollectEnable",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "数据采集启用/禁用",operType = "编辑",operLevl = "2")
    @RequiresPermissions("sys:parmDataCollect:enable")
    public Result<EditConfigResp> dataCollectEnable(@RequestBody EditConfigReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            return configService.editConfig(req);
        }catch (Exception e){
            log.error("ConfigController.dataCollectEnable exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 数据采集修改基础配置
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/editDataCollectConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "数据采集修改基础配置",operType = "编辑",operLevl = "2")
    @RequiresPermissions("sys:parmDataCollect:modify")
    public Result<EditConfigResp> editDataCollectConfig(@RequestBody EditConfigReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            return configService.editConfig(req);
        }catch (Exception e){
            log.error("ConfigController.editDataCollectConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 编辑上报业务配置
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/editDataBusinessConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "编辑上报业务配置记录",operType = "编辑",operLevl = "2")
    @RequiresPermissions("sys:parmDataCollect:modifyConfig")
    public Result editDataBusinessConfig(@RequestBody EditDataBusinessConfigReq req){
        try {
            return configService.editDataBusinessConfig(req);
        }catch (Exception e){
            log.error("ConfigController.editDataBusinessConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 同步接入基站配置(支持全量同步)
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/syncInStationConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "同步接入基站配置(支持全量同步)记录",operType = "同步配置",operLevl = "2")
    @RequiresPermissions("sys:parmInStation:synchronous")
    public Result<SyncConfigResp> syncInStationConfig(@RequestBody SyncConfigReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.STATION_INSIDE.getCode());
            return configService.syncConfig(req);
        }catch (Exception e){
            log.error("ConfigController.syncInStationConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 同步边界基站配置(支持全量同步)
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/syncBoundaryStationConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "同步边界基站配置(支持全量同步)记录",operType = "同步配置",operLevl = "2")
    @RequiresPermissions("sys:parmBoundaryStation:synchronous")
    public Result<SyncConfigResp> syncBoundaryStationConfig(@RequestBody SyncConfigReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.STATION_BOUNDARY.getCode());
            return configService.syncConfig(req);
        }catch (Exception e){
            log.error("ConfigController.syncBoundaryStationConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 同步密钥中心配置(支持全量同步)
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/syncSecretConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "同步密钥中心配置(支持全量同步)记录",operType = "同步配置",operLevl = "2")
    @RequiresPermissions("sys:parmSecret:synchronous")
    public Result<SyncConfigResp> syncSecretConfig(@RequestBody SyncConfigReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.DEVICE_DISPENSER.getCode());
            return configService.syncConfig(req);
        }catch (Exception e){
            log.error("ConfigController.syncSecretConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


    /**
     * 接入基站配置恢复默认（支持全量恢复）
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/resetInStationConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "接入基站配置恢复默认（支持全量恢复）",operType = "恢复默认",operLevl = "2")
    @RequiresPermissions("sys:parmInStation:default")
    public Result<ResetDefaultConfigResp> resetInStationConfig(@RequestBody ResetDefaultConfigReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.STATION_INSIDE.getCode());
            return  configService.resetDefaultConfig(req);
        }catch (Exception e){
            log.error("ConfigController.resetInStationConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 边界基站配置恢复默认（支持全量恢复）
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/resetBoundaryStationConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "边界基站配置恢复默认（支持全量恢复）",operType = "恢复默认",operLevl = "2")
    @RequiresPermissions("sys:parmBoundaryStation:default")
    public Result<ResetDefaultConfigResp> resetBoundaryStationConfig(@RequestBody ResetDefaultConfigReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.STATION_BOUNDARY.getCode());
            return  configService.resetDefaultConfig(req);
        }catch (Exception e){
            log.error("ConfigController.resetBoundaryStationConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 密钥中心配置恢复默认（支持全量恢复）
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/resetSecretConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "密钥中心配置恢复默认（支持全量恢复）",operType = "恢复默认",operLevl = "2")
    @RequiresPermissions("sys:parmSecret:default")
    public Result<ResetDefaultConfigResp> resetSecretConfig(@RequestBody ResetDefaultConfigReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.DEVICE_DISPENSER.getCode());
            return  configService.resetDefaultConfig(req);
        }catch (Exception e){
            log.error("ConfigController.resetSecretConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 数据采集基础配置恢复默认（支持全量恢复）
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/resetDataCollectConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "数据采集基础配置恢复默认（支持全量恢复）",operType = "恢复默认",operLevl = "2")
    @RequiresPermissions("sys:parmDataCollect:default")
    public Result<ResetDefaultConfigResp> resetDataCollectConfig(@RequestBody ResetDefaultConfigReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.DATA_BASE.getCode());
            return  configService.resetDefaultConfig(req);
        }catch (Exception e){
            log.error("ConfigController.resetDataCollectConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


    /**
     * 数据采集基础配置同步（支持全量同步）
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/syncDataCollectConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "数据采集基础配置同步（支持全量同步）",operType = "同步",operLevl = "2")
    @RequiresPermissions("sys:parmDataCollect:syncBase")
    public Result<SyncConfigResp> syncDataCollectConfig(@RequestBody SyncConfigReq req){
        try {
            req.setDeviceType(DeviceTypeEnum.DATA_BASE.getCode());
            return configService.syncDataCollectConfig(req);
        }catch (Exception e){
            log.error("ConfigController.syncDataCollectConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


    /**
     * 恢复数据采集上报业务配置接口（支持全量恢复）
     * @author hexu
     * @param req
     * @return
     */
    @RequestMapping (value = "/resetDataBusinessConfig",method = RequestMethod.POST)
    @SystemLog(opermodul = "系统设置",operDesc = "恢复数据采集上报业务配置接口",operType = "恢复默认",operLevl = "2")
    @RequiresPermissions("sys:parmDataCollect:defaultConfig")
    public Result<ResetDataBusinessConfigResp> resetDataBusinessConfig(@RequestBody ResetDataBusinessConfigReq req){
        try {
            return  configService.resetDataBusinessConfig(req);
        }catch (Exception e){
            log.error("ConfigController.resetDataBusinessConfig exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }



    /**
     * 更新设备配置信息
     * @author zhangyunjie
     * @param configurationReq
     * @return
     */
//    @RequestMapping (value = "/insertOrUpdate",method = RequestMethod.POST)
//    @SystemLog(opermodul = "配置模块",operDesc = "更新设备配置信息",operType = "更新")
//    public Result insertOrUpdate(@RequestBody ConfigurationReq configurationReq){
//        return  configurationService.insertOrUpdate(configurationReq);
//    }


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
