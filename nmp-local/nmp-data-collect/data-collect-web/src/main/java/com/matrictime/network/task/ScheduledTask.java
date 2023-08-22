package com.matrictime.network.task;

import com.matrictime.network.service.ScheduledTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;

import static com.matrictime.network.base.constant.DataConstants.*;

/**
 * @author by wangqiang
 * @date 2023/8/21.
 */
@Component
@EnableAsync //开启多线程
@Slf4j
public class ScheduledTask {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Resource
    private ScheduledTaskService scheduledTaskService;

    @Value("${netmanage.ip}")
    private String ip;

    @Value("${netmanage.port}")
    private String port;

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void insertDataCollect(){
        log.info(Thread.currentThread().getName()+"======================insertDataCollect begin=============================");
        scheduledTaskService.insertDataCollect(ip + KEY_SPLIT + port + INSERT_DATA_COLLECT);
        log.info(Thread.currentThread().getName()+"======================insertDataCollect end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void receiveTerminalUser(){
        log.info(Thread.currentThread().getName()+"======================receiveTerminalUser begin=============================");
        scheduledTaskService.receiveTerminalUser(ip + KEY_SPLIT + port + RECEIVE_TERMINAL_USER);
        log.info(Thread.currentThread().getName()+"======================receiveTerminalUser end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void selectCompanyHeartbeat(){
        log.info(Thread.currentThread().getName()+"======================selectCompanyHeartbeat begin=============================");
        scheduledTaskService.selectCompanyHeartbeat(ip + KEY_SPLIT + port + INSERT_COMPANY_HEARTBEAT);
        log.info(Thread.currentThread().getName()+"======================selectCompanyHeartbeat end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void selectDevice(){
        log.info(Thread.currentThread().getName()+"======================selectDevice begin=============================");
        scheduledTaskService.selectDevice(ip + KEY_SPLIT + port + RECEIVE_STATION_SUMMARY);
        log.info(Thread.currentThread().getName()+"======================selectDevice end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void selectStation(){
        log.info(Thread.currentThread().getName()+"======================selectStation begin=============================");
        scheduledTaskService.selectStation(ip + KEY_SPLIT + port + RECEIVE_STATION_SUMMARY);
        log.info(Thread.currentThread().getName()+"======================selectStation end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void selectBorderStation(){
        log.info(Thread.currentThread().getName()+"======================selectBorderStation begin=============================");
        scheduledTaskService.selectBorderStation(ip + KEY_SPLIT + port + RECEIVE_STATION_SUMMARY);
        log.info(Thread.currentThread().getName()+"======================selectBorderStation end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void selectSystemHeart(){
        log.info(Thread.currentThread().getName()+"======================selectSystemHeart begin=============================");
        scheduledTaskService.selectSystemHeart(ip + KEY_SPLIT + port + RECEIVE_STATION_SUMMARY);
        log.info(Thread.currentThread().getName()+"======================selectSystemHeart end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void receiveCompany(){
        log.info(Thread.currentThread().getName()+"======================receiveCompany begin=============================");
        scheduledTaskService.receiveCompany(ip + KEY_SPLIT + port + RECEIVE_COMPANY);
        log.info(Thread.currentThread().getName()+"======================receiveCompany end=============================");
    }

}
