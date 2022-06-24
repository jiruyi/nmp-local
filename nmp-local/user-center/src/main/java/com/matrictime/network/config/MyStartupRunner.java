package com.matrictime.network.config;

import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.matrictime.network.base.ComOptApiImpl;
import com.matrictime.network.service.UserService;
import com.matrictime.network.util.DateUtils;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
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

    @Value("${app.stationip}")
    private String stationip;

    @Value("${app.stationport}")
    private String stationport;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        log.info("SpringBoot run appName:{},appId:{},appPort:{},FlowType:{},handleType:{}", appName, appId, appPort, FlowType, handleType);
        JServiceImpl.FlowType = FlowType;
        JServiceImpl.handleType = handleType;
        JServiceImpl.start(appName, appId, appPort, comOptApi);
        if (handleType == 1){
            new Thread(() -> {
                while (true){
                    if (JServiceImpl.getFD()!=-1){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("commonKey",stationip);
                        jsonObject.put("uuid",stationport);
                        try {
                            String post = HttpClientUtil.post("http://127.0.0.1:8006/user/zr", jsonObject.toJSONString());
                            if ("0".equals(post)){
                                break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(1000*30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        userService.updateUserLogStatus();

    }

    @PreDestroy
    public void destory() throws Exception{
        JServiceImpl.stop();
        log.info("在程序关闭后执行");
    }
}
