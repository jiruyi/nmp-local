package com.matrictime.network.config;

import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.matrictime.network.base.ComOptApiImpl;
import com.matrictime.network.service.UserService;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;


@Component
@Slf4j
public class MyStartupRunner implements CommandLineRunner {

    @Value("${app.name}")
    private String appName;

    @Value("${app.id}")
    private String appId;

    @Value("${app.port}")
    private Integer appPort;
    @Value("${app.flowtype}")
    private Integer FlowType;

    @Value("${app.handleType}")
    private Integer handleType;

    @Value("${app.stationip}")
    private String stationip;

    @Value("${app.stationport}")
    private String stationport;

    @Value("${rz.sleep.time}")
    private Integer rzSleepTime;

    @Value("${rz.sleep.wait.time}")
    private Integer rzSleepWaitTime;

    @Value("${rz.retry.time}")
    private Integer rzRetryTime;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        log.info("SpringBoot run appName:{},appId:{},appPort:{},FlowType:{},handleType:{}", appName, appId, appPort, FlowType, handleType);
        userService.updateUserLogStatus();

    }

    @PreDestroy
    public void destory() throws Exception{
        log.info("在程序关闭后执行");
    }
}
