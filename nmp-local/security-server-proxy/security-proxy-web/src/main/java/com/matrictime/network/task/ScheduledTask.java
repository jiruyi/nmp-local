package com.matrictime.network.task;

import com.matrictime.network.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@EnableAsync //开启多线程
@Slf4j
public class ScheduledTask {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private TaskService taskService;

    @Value("${security_server.ip}")
    private String ip;

    @Value("${security_server.port}")
    private String port;


    /**
     * @title heartReport
     * @return void
     * @description  安全服务器状态上报定时任务
     * @author hx
     * @create 2023/4/19 0019 17:25
     */
//    @Scheduled(cron = "*/30 * * * * ?")
//    @Async
//    public void heartReport(){
//        log.info(Thread.currentThread().getName()+"======================heartReport begin=============================");
//        taskService.heartReport(new Date());
//        log.info(Thread.currentThread().getName()+"======================heartReport end=============================");
//    }



    @Scheduled(cron = "*/30 * * * * ?")
    @Async
    public void dataPush(){
        log.info(Thread.currentThread().getName()+"======================dataPush begin=============================");
        taskService.dataPush();
        log.info(Thread.currentThread().getName()+"======================dataPush end=============================");
    }
}

