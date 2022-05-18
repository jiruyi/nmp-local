package com.matrictime.network.config;

import com.jzsg.bussiness.JServiceImpl;
import com.matrictime.network.base.ComOptApiImpl;
import com.matrictime.network.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Date;


@Component
@Slf4j
public class MyStartupRunner implements CommandLineRunner {

    @Value("${app.name}")
    private String appName;

    @Value("${app.id}")
    private String appId;

    @Value("${app.port}")
    private Integer appPort;

    private ComOptApiImpl comOptApi = new ComOptApiImpl();

    @Value("${app.flowtype}")
    private Integer FlowType;

    @Value("${app.handleType}")
    private Integer handleType;

    @Override
    public void run(String... args) throws Exception {
        log.info("SpringBoot run appName:{},appId:{},appPort:{},FlowType:{},handleType:{}", appName, appId, appPort, FlowType, handleType);
        JServiceImpl.FlowType = FlowType;
        JServiceImpl.handleType = handleType;
        JServiceImpl.start(appName, appId, appPort, comOptApi);
//        if (FlowType == 0){
//            int i = -1;
//            Date date = DateUtils.addMinuteForDate(new Date(), 1);
//            while (i != 0 && new Date().before(date)){
//                i = JServiceImpl.setBSAuth("192.168.72.213", 20055);
//                log.info("接入基站认证结果："+i);
//                Thread.currentThread().sleep(3000);
//            }
//        }
    }

    @PreDestroy
    public void destory() throws Exception{
        JServiceImpl.stop();
        log.info("在程序关闭后执行");
    }
}
