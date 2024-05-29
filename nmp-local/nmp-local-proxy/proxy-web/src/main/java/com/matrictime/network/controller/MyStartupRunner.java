package com.matrictime.network.controller;

import com.matrictime.network.service.InitInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;


@Component
@Slf4j
public class MyStartupRunner implements CommandLineRunner {

    @Autowired
    private InitInfoService initInfoService;


    @Override
    public void run(String... args){
        log.info("SpringBoot run");
        initInfoService.initInfo();
    }

    @PreDestroy
    public void destory() throws Exception{
        log.info("在程序关闭后执行");
    }
}
