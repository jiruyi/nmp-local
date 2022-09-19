package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.ProxyReq;
import com.matrictime.network.request.SignalIoReq;
import com.matrictime.network.response.SignalIoResp;
import com.matrictime.network.service.ProxyInitService;
import com.matrictime.network.service.SignalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/proxy")
@RestController
@Slf4j
public class ProxyInitController {

    @Autowired
    ProxyInitService proxyInitService;

    /**
     * 代理初始化
     * @author zyj
     * @param req
     * @return
     */
    @RequestMapping (value = "/init",method = RequestMethod.POST)
    public Result init(@RequestBody ProxyReq req){
        return proxyInitService.init(req);
    }

}
