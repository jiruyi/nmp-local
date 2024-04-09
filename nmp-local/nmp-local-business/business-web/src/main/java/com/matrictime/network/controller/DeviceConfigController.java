package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.DeviceConfigReq;
import com.matrictime.network.request.DeviceReportBusinessReq;
import com.matrictime.network.request.DeviceTaskConfigReq;
import com.matrictime.network.service.DeviceConfigService;
import com.matrictime.network.service.NmplDeviceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/deviceConfig",method = RequestMethod.POST)
@Slf4j
public class DeviceConfigController {

    @Resource
    NmplDeviceTaskService nmplDeviceTaskService;
    @Resource
    DeviceConfigService deviceConfigService;


    //--------------------接入基站------------------------------

    //@RequiresPermissions("sys:deviceTask:stationInsideConfig")
    @RequestMapping(value = "/stationInsideTaskquery",method = RequestMethod.POST)
    public Result stationInsideTaskquery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:stationInsideConfig")
    @RequestMapping(value = "/stationInsideTaskSave",method = RequestMethod.POST)
    public Result stationInsideTaskSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:stationInsideConfig")
    @RequestMapping(value = "/stationInsideConfigQuery",method = RequestMethod.POST)
    public Result stationInsideConfigQuery(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.query(deviceConfigReq);
    }

    //@RequiresPermissions("sys:deviceTask:stationInsideConfig")
    @RequestMapping(value = "/stationInsideConfigSave",method = RequestMethod.POST)
    public Result stationInsideConfigSave(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.save(deviceConfigReq);
    }


    //--------------------边界基站------------------------------

    //@RequiresPermissions("sys:deviceTask:stationBoundaryConfig")
    @RequestMapping(value = "/stationBoundaryTaskquery",method = RequestMethod.POST)
    public Result stationBoundaryTaskquery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:stationBoundaryConfig")
    @RequestMapping(value = "/stationBoundaryTaskSave",method = RequestMethod.POST)
    public Result stationBoundaryTaskSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:stationBoundaryConfig")
    @RequestMapping(value = "/stationBoundaryConfigQuery",method = RequestMethod.POST)
    public Result stationBoundaryConfigQuery(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.query(deviceConfigReq);
    }

    //@RequiresPermissions("sys:deviceTask:stationBoundaryConfig")
    @RequestMapping(value = "/stationBoundaryConfigSave",method = RequestMethod.POST)
    public Result stationBoundaryConfigSave(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.save(deviceConfigReq);
    }


    //--------------------密钥中心------------------------------

    //@RequiresPermissions("sys:deviceTask:dispenserConfig")
    @RequestMapping(value = "/dispenserTaskquery",method = RequestMethod.POST)
    public Result dispenserTaskquery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:dispenserConfig")
    @RequestMapping(value = "/dispenserTaskSave",method = RequestMethod.POST)
    public Result dispenserTaskSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:dispenserConfig")
    @RequestMapping(value = "/dispenserConfigQuery",method = RequestMethod.POST)
    public Result dispenserConfigQuery(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.query(deviceConfigReq);
    }

    //@RequiresPermissions("sys:deviceTask:dispenserConfig")
    @RequestMapping(value = "/dispenserConfigSave",method = RequestMethod.POST)
    public Result dispenserConfigSave(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.save(deviceConfigReq);
    }

    //--------------------生成机------------------------------

    //@RequiresPermissions("sys:deviceTask:generatorConfig")
    @RequestMapping(value = "/generatorTaskquery",method = RequestMethod.POST)
    public Result generatorTaskquery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:generatorConfig")
    @RequestMapping(value = "/generatorTaskSave",method = RequestMethod.POST)
    public Result generatorTaskSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:generatorConfig")
    @RequestMapping(value = "/generatorConfigQuery",method = RequestMethod.POST)
    public Result generatorConfigQuery(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.query(deviceConfigReq);
    }

    //@RequiresPermissions("sys:deviceTask:generatorConfig")
    @RequestMapping(value = "/generatorConfigSave",method = RequestMethod.POST)
    public Result generatorConfigSave(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.save(deviceConfigReq);
    }



    //--------------------缓存机------------------------------

    //@RequiresPermissions("sys:deviceTask:cacheConfig")
    @RequestMapping(value = "/cacheTaskquery",method = RequestMethod.POST)
    public Result cacheTaskquery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:cacheConfig")
    @RequestMapping(value = "/cacheTaskSave",method = RequestMethod.POST)
    public Result cacheTaskSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:cacheConfig")
    @RequestMapping(value = "/cacheConfigQuery",method = RequestMethod.POST)
    public Result cacheConfigQuery(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.query(deviceConfigReq);
    }

    //@RequiresPermissions("sys:deviceTask:cacheConfig")
    @RequestMapping(value = "/cacheConfigSave",method = RequestMethod.POST)
    public Result cacheConfigSave(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.save(deviceConfigReq);
    }



    //--------------------数据采集------------------------------

    //@RequiresPermissions("sys:deviceTask:dataBaseConfig")
    @RequestMapping(value = "/dataBaseTaskquery",method = RequestMethod.POST)
    public Result dataBaseTaskquery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:dataBaseConfig")
    @RequestMapping(value = "/dataBaseTaskSave",method = RequestMethod.POST)
    public Result dataBaseTaskSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:dataBaseConfig")
    @RequestMapping(value = "/dataBaseConfigQuery",method = RequestMethod.POST)
    public Result dataBaseConfigQuery(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.query(deviceConfigReq);
    }

    //@RequiresPermissions("sys:deviceTask:dataBaseConfig")
    @RequestMapping(value = "/dataBaseConfigSave",method = RequestMethod.POST)
    public Result dataBaseConfigSave(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.save(deviceConfigReq);
    }

    //@RequiresPermissions("sys:deviceTask:dataBaseConfig")
    @RequestMapping(value = "/dataBaseReportBusinessQuery",method = RequestMethod.POST)
    public Result dataBaseReportBusinessQuery(@RequestBody DeviceReportBusinessReq deviceReportBusinessReq){
        return deviceConfigService.queryReportBusiness(deviceReportBusinessReq);
    }

    //@RequiresPermissions("sys:deviceTask:dataBaseConfig")
    @RequestMapping(value = "/dataBaseReportBusinessSave",method = RequestMethod.POST)
    public Result dataBaseReportBusinessSave(@RequestBody DeviceReportBusinessReq deviceReportBusinessReq){
        return deviceConfigService.saveReportBusiness(deviceReportBusinessReq);
    }



    //--------------------指控中心------------------------------

    //@RequiresPermissions("sys:deviceTask:accuseCenterConfig")
    @RequestMapping(value = "/accuseCenterTaskquery",method = RequestMethod.POST)
    public Result accuseCenterTaskquery(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.query(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:accuseCenterConfig")
    @RequestMapping(value = "/accuseCenterTaskSave",method = RequestMethod.POST)
    public Result accuseCenterTaskSave(@RequestBody DeviceTaskConfigReq deviceTaskConfigReq){
        return nmplDeviceTaskService.save(deviceTaskConfigReq);

    }

    //@RequiresPermissions("sys:deviceTask:accuseCenterConfig")
    @RequestMapping(value = "/accuseCenterConfigQuery",method = RequestMethod.POST)
    public Result accuseCenterConfigQuery(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.query(deviceConfigReq);
    }

    //@RequiresPermissions("sys:deviceTask:accuseCenterConfig")
    @RequestMapping(value = "/accuseCenterConfigSave",method = RequestMethod.POST)
    public Result accuseCenterConfigSave(@RequestBody DeviceConfigReq deviceConfigReq){
        return deviceConfigService.save(deviceConfigReq);
    }



}
