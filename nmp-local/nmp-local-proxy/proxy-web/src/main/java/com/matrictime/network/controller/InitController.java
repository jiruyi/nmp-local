package com.matrictime.network.controller;


import com.matrictime.network.request.InitInfoReq;
import com.matrictime.network.service.InitInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/init",method = RequestMethod.POST)
@Slf4j
public class InitController {
    @Resource
    private InitInfoService initInfoService;

    @Value("${local.ip}")
    private String localIp;


    /**
     * 代理初始化
     */
    @RequestMapping(value = "/initInfo",method = RequestMethod.POST)
    public void initInfo(){
        InitInfoReq req = new InitInfoReq();
        req.setLocalIp(localIp);
        initInfoService.initInfo(req);
    }

}
