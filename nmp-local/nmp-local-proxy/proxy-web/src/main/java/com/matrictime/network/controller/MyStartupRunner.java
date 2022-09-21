package com.matrictime.network.controller;

import com.matrictime.network.request.InitInfoReq;
import com.matrictime.network.service.InitInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;


@Component
@Slf4j
public class MyStartupRunner implements CommandLineRunner {

    @Value("${local.ip}")
    private String localIp;

    @Autowired
    private InitInfoService initInfoService;


    @Override
    public void run(String... args){
        log.info("SpringBoot run localIp:{}", localIp);
        InitInfoReq req = new InitInfoReq();
        req.setLocalIp(localIp);
        initInfoService.initInfo(req);
    }

    @PreDestroy
    public void destory() throws Exception{
        log.info("在程序关闭后执行");
    }
}
