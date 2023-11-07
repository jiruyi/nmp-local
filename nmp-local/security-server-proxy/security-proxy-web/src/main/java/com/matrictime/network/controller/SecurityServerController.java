package com.matrictime.network.controller;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.req.EditServerProxyReq;
import com.matrictime.network.service.SecurityServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 安全服务器模块
 * @author hexu
 */
@RestController
@RequestMapping(value = "/server",method = RequestMethod.POST)
@Slf4j
public class SecurityServerController extends SystemBaseService {

    @Autowired
    private SecurityServerService serverService;

    /**
     * 安全服务器更新
     * @param
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result<Integer> updateServer(@RequestBody EditServerProxyReq req){
        Result<Integer> result;
        try {
            result = serverService.updateServer(req);
        }catch (Exception e){
            log.info("SecurityServerController.update{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


}