package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.request.DeviceTaskConfigReq;
import com.matrictime.network.service.NmplDeviceTaskService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/deviceTask",method = RequestMethod.POST)
@Slf4j
public class DeviceTaskController {

    @Resource
    NmplDeviceTaskService nmplDeviceTaskService;

    //@RequiresPermissions("sys:deviceTask:stationInsideQuery")
    @RequestMapping(value = "/stationInsideQuery",method = RequestMethod.POST)
    public Result stationInsidequery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:stationBoundaryQuery")
    @RequestMapping(value = "/stationBoundaryQuery",method = RequestMethod.POST)
    public Result stationBoundaryquery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:dispenserQuery")
    @RequestMapping(value = "/dispenserQuery",method = RequestMethod.POST)
    public Result dispenserQuery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:cacheQuery")
    @RequestMapping(value = "/cacheQuery",method = RequestMethod.POST)
    public Result cacheQuery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:dataBaseQuery")
    @RequestMapping(value = "/dataBaseQuery",method = RequestMethod.POST)
    public Result dataBaseQuery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);
    }

    //@RequiresPermissions("sys:deviceTask:accuseCenterQuery")ACCUSE_CENTER
    @RequestMapping(value = "/accuseCenterQuery",method = RequestMethod.POST)
    public Result accuseCenterQuery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);
    }



    //@RequiresPermissions("sys:deviceTask:stationInsideSave")
    @RequestMapping(value = "/stationInsideSave",method = RequestMethod.POST)
    public Result stationInsideSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:stationBoundarySave")
    @RequestMapping(value = "/stationBoundarySave",method = RequestMethod.POST)
    public Result stationBoundarySave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:dispenserSave")
    @RequestMapping(value = "/dispenserSave",method = RequestMethod.POST)
    public Result dispenserSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:cacheSave")
    @RequestMapping(value = "/cacheSave",method = RequestMethod.POST)
    public Result cacheSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:dataBaseSave")
    @RequestMapping(value = "/dataBaseSave",method = RequestMethod.POST)
    public Result dataBaseSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);
    }

    //@RequiresPermissions("sys:deviceTask:accuseCenterSave")ACCUSE_CENTER
    @RequestMapping(value = "/accuseCenterSave",method = RequestMethod.POST)
    public Result accuseCenterSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);
    }


}
