package com.matrictime.network.controller;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.service.VersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 版本管理模块
 * @author hexu
 */
@RestController
@RequestMapping(value = "/version",method = RequestMethod.POST)
@Slf4j
public class VersionController extends SystemBaseService {

    @Autowired
    private VersionService versionService;

    /**
     * 加载
     * @param request
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    public Result load(VersionLoadReq request){
        Result result;
        try {
            result = versionService.load(request);
        }catch (Exception e){
            log.warn("VersionController.load{}",e);
            result = failResult("");
        }
        return result;
    }

    /**
     * 加载运行
     * @param request
     * @return
     */
    @RequestMapping(value = "/start",method = RequestMethod.POST)
    public Result<Integer> start(@RequestBody VersionStartReq request){
        Result<Integer> result;
        try {
            result = versionService.start(request);
        }catch (Exception e){
            log.info("VersionController.start{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 启动
     * @param request
     * @return
     */
    @RequestMapping(value = "/run",method = RequestMethod.POST)
    public Result<Integer> run(@RequestBody VersionRunReq request){
        Result<Integer> result;
        try {
            result = versionService.run(request);
        }catch (Exception e){
            log.info("VersionController.run{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 停止
     * @param request
     * @return
     */
    @RequestMapping(value = "/stop",method = RequestMethod.POST)
    public Result<Integer> stop(@RequestBody VersionStopReq request){
        Result<Integer> result;
        try {
            result = versionService.stop(request);
        }catch (Exception e){
            log.info("VersionController.stop{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 卸载
     * @param request
     * @return
     */
    @RequestMapping(value = "/uninstall",method = RequestMethod.POST)
    public Result<Integer> uninstall(@RequestBody VersionUninstallReq request){
        Result<Integer> result;
        try {
            result = versionService.uninstall(request);
        }catch (Exception e){
            log.info("VersionController.uninstall{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }
}
