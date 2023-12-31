package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DataCollectService;
import com.matrictime.network.service.TerminalDataService;
import com.matrictime.network.util.ListSplitUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 统计管理
 * @author zhangyunjie
 */
@RequestMapping(value = "/data")
@RestController
@Slf4j
public class DataController {
    @Autowired
    DataCollectService dataCollectService;
    @Value("${thread.batchMaxSize}")
    Integer maxSize;
    @Autowired
    TerminalDataService terminalDataService;
    /**
     * 基站数据多条件查询接口
     * @param dataCollectReq
     * @return
     */
    @ApiOperation(value = "基站数据多条件查询接口",notes = "基站数据多条件查询接口")
    @RequestMapping(value = "/queryStationDataByConditon",method = RequestMethod.POST)
    @RequiresPermissions("sys:stationData:query")
    @SystemLog(opermodul = "统计管理模块",operDesc = "查询基站数据",operType = "查询")
    public Result<PageInfo> queryStationDataByConditon(@RequestBody DataCollectReq dataCollectReq){
        dataCollectReq.setDeviceType(StationTypeEnum.BASE.getCode());
        return dataCollectService.queryByConditon(dataCollectReq);
    }

    /**
     * 分发机数据多条件查询接口
     * @param dataCollectReq
     * @return
     */
    @ApiOperation(value = "分发机数据多条件查询接口",notes = "分发机数据多条件查询接口")
    @RequestMapping(value = "/queryDispenserDataByConditon",method = RequestMethod.POST)
    @RequiresPermissions("sys:dispenserData:query")
    @SystemLog(opermodul = "统计管理模块",operDesc = "查询分发机数据",operType = "查询")
    public Result<PageInfo> queryDispenserDataByConditon(@RequestBody DataCollectReq dataCollectReq){
        dataCollectReq.setDeviceType(DeviceTypeEnum.DISPENSER.getCode());
        return dataCollectService.queryByConditon(dataCollectReq);
    }

    /**
     * 生成机数据多条件查询接口
     * @param dataCollectReq
     * @return
     */
    @ApiOperation(value = "生成机数据多条件查询接口",notes = "生成机数据多条件查询接口")
    @RequestMapping(value = "/queryGeneratorDataByConditon",method = RequestMethod.POST)
    @RequiresPermissions("sys:generatorData:query")
    @SystemLog(opermodul = "统计管理模块",operDesc = "查询生成机数据",operType = "查询")
    public Result<PageInfo> queryGeneratorDataByConditon(@RequestBody DataCollectReq dataCollectReq){
        dataCollectReq.setDeviceType(DeviceTypeEnum.GENERATOR.getCode());
        return dataCollectService.queryByConditon(dataCollectReq);
    }

    /**
     * 缓存机数据多条件查询接口
     * @param dataCollectReq
     * @return
     */
    @ApiOperation(value = "缓存机数据多条件查询接口",notes = "缓存机数据多条件查询接口")
    @RequestMapping(value = "/queryCacheDataByConditon",method = RequestMethod.POST)
    @RequiresPermissions("sys:cacheData:query")
    @SystemLog(opermodul = "统计管理模块",operDesc = "查询缓存机数据",operType = "查询")
    public Result<PageInfo> queryCacheDataByConditon(@RequestBody DataCollectReq dataCollectReq){
        dataCollectReq.setDeviceType(DeviceTypeEnum.CACHE.getCode());
        return dataCollectService.queryByConditon(dataCollectReq);
    }




    /**
     * 获取统计数据的设备
     * @param dataCollectReq
     * @return
     */
    @RequestMapping(value = "/selectAllDevice",method = RequestMethod.POST)
    public Result selectAllDevice(@RequestBody DataCollectReq dataCollectReq){
        return dataCollectService.selectAllDevice(dataCollectReq);
    }

    /**
     * 流量变化
     * @param dataCollectReq
     * @return
     */
    @RequestMapping(value = "/flowTransformation",method = RequestMethod.POST)
    public Result flowTransformation(@RequestBody DataCollectReq dataCollectReq){
        return dataCollectService.flowTransformation(dataCollectReq);
    }

    /**
     * 当前流量
     * @param dataCollectReq
     * @return
     */
    @RequestMapping(value = "/currentIpFlow",method = RequestMethod.POST)
    public Result currentIpFlow(@RequestBody DataCollectReq dataCollectReq){
        return dataCollectService.currentIpFlow(dataCollectReq);
    }

    /**
     * 终端流量变化
     * @param terminalDataReq
     * @return
     */
    @RequestMapping(value = "/terminalflowTransfor",method = RequestMethod.POST)
    public Result TerminalflowTransfor(@RequestBody TerminalDataReq terminalDataReq){
        return terminalDataService.flowTransformation(terminalDataReq);
    }

}
