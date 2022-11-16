package com.matrictime.network.controller;

import com.matrictime.network.service.BaseStationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

    @Resource
    private BaseStationInfoService baseStationInfoService;

    @Override
    public void run(String... args) throws Exception {
        log.info("在程序启动后执行");
        baseStationInfoService.initBaseStation();
    }


    @PreDestroy
    public void destory() throws Exception{
        log.info("在程序关闭后执行");
    }
}
