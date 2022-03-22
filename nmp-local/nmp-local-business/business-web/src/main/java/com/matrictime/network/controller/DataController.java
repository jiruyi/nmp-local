package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.request.BillRequest;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.MonitorReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DataCollectService;
import com.matrictime.network.util.ListSplitUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 统计管理
 */
@RequestMapping(value = "/data")
@RestController
@Slf4j
public class DataController {
    @Autowired
    DataCollectService dataCollectService;

    /**
     * 基站数据多条件查询接口
     * @param dataCollectReq
     * @return
     */
    @ApiOperation(value = "基站数据多条件查询接口",notes = "话单多条件查询接口")
    @RequestMapping(value = "/queryStationDataByConditon",method = RequestMethod.POST)
    @RequiresPermissions("sys:stationData:query")
    public Result<PageInfo> queryStationDataByConditon(@RequestBody DataCollectReq dataCollectReq){
        dataCollectReq.setDeviceType("01");
        return dataCollectService.queryByConditon(dataCollectReq);
    }

    /**
     * 分发机数据多条件查询接口
     * @param dataCollectReq
     * @return
     */
    @ApiOperation(value = "分发机数据多条件查询接口",notes = "话单多条件查询接口")
    @RequestMapping(value = "/queryDispenserDataByConditon",method = RequestMethod.POST)
    @RequiresPermissions("sys:dispenserData:query")
    public Result<PageInfo> queryDispenserDataByConditon(@RequestBody DataCollectReq dataCollectReq){
        dataCollectReq.setDeviceType("02");
        return dataCollectService.queryByConditon(dataCollectReq);
    }

    /**
     * 生成机数据多条件查询接口
     * @param dataCollectReq
     * @return
     */
    @ApiOperation(value = "生成机数据多条件查询接口",notes = "话单多条件查询接口")
    @RequestMapping(value = "/queryGeneratorDataByConditon",method = RequestMethod.POST)
    @RequiresPermissions("sys:generatorData:query")
    public Result<PageInfo> queryGeneratorDataByConditon(@RequestBody DataCollectReq dataCollectReq){
        dataCollectReq.setDeviceType("03");
        return dataCollectService.queryByConditon(dataCollectReq);
    }

    /**
     * 缓存机数据多条件查询接口
     * @param dataCollectReq
     * @return
     */
    @ApiOperation(value = "缓存机数据多条件查询接口",notes = "话单多条件查询接口")
    @RequestMapping(value = "/queryCacheDataByConditon",method = RequestMethod.POST)
    @RequiresPermissions("sys:cacheData:query")
    public Result<PageInfo> queryCacheDataByConditon(@RequestBody DataCollectReq dataCollectReq){
        dataCollectReq.setDeviceType("04");
        return dataCollectService.queryByConditon(dataCollectReq);
    }

    /**
     * 统计数据插入
     * @param dataCollectReq
     * @return
     */
    @ApiOperation(value = "统计数据创建接口",notes = "话单多条件查询接口")
    @RequestMapping(value = "/saveData",method = RequestMethod.POST)
    public Result saveData(@RequestBody DataCollectReq dataCollectReq){
        Result result = null;
        try {
            if (dataCollectReq.getDataCollectVoList()!=null&&dataCollectReq.getDataCollectVoList().size()>10){
                List<Result>resultList = new ArrayList<>();
                List<List<DataCollectVo>> data = ListSplitUtil.split(dataCollectReq.getDataCollectVoList(),10);
                for (List<DataCollectVo> datum : data) {
                    DataCollectReq req = new DataCollectReq();
                    req.setDataCollectVoList(datum);
                    resultList.add(dataCollectService.save(req).get());
                }
                result = new Result<>();
                result.setSuccess(true);
                result.setResultObj(resultList);
            }else {
                result = dataCollectService.save(dataCollectReq).get();
            }
        } catch (InterruptedException e) {
            log.info(e.getMessage());
        } catch (ExecutionException e) {
            log.info(e.getMessage());
        }finally {
            return result;
        }
    }


    /**
     * 状态监控统计数据
     */
    @RequestMapping(value = "/monitorData",method = RequestMethod.POST)
    public Result monitorData(@RequestBody MonitorReq monitorReq){
        return dataCollectService.monitorData(monitorReq);
    }


    /**
     * 状态监控top10
     */
    @RequestMapping(value = "/monitorDataTopTen",method = RequestMethod.POST)
    public Result monitorDataTopTen(@RequestBody MonitorReq monitorReq){
        return dataCollectService.monitorDataTopTen(monitorReq);
    }

}