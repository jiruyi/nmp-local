package com.matrictime.network.task;

import com.matrictime.network.service.DataPushService;
import com.matrictime.network.service.TaskService;
import com.matrictime.network.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.matrictime.network.base.constant.DataConstants.*;

@Component
@EnableAsync //开启多线程
@Slf4j
public class ScheduledTask {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private TaskService taskService;

    @Autowired
    private   DataPushService dataPushService;

    @Value("${netmanage.ip}")
    private String ip;

    @Value("${netmanage.port}")
    private String port;

    @Value("${local.ip}")
    private String localIp;


    @Scheduled(cron = "*/30 * * * * ?")
    @Async
    public void heartReport(){
        log.info(Thread.currentThread().getName()+"======================heartReport begin=============================");
        taskService.heartReport(ip + KEY_SPLIT + port + HEART_REPORT_URL);
        log.info(Thread.currentThread().getName()+"======================heartReport end=============================");
    }


    /**
      * @title alarmPush
      * @param []
      * @return void
      * @description  告警信息定时任务
      * @author jiruyi
      * @create 2023/4/19 0019 17:25
      */
    @Scheduled(cron = "0/10 * * * * ? ")
    public void alarmPush(){
        log.info(Thread.currentThread().getName()+ new Date()+"======================alarmPush begin=============================");
        dataPushService.alarmPush();
        log.info(Thread.currentThread().getName()+new Date()+"======================alarmPush end=============================");
    }

//    @Scheduled(cron = "*/30 * * * * ?")
//    @Async
////    public void pcData(){
////        log.info(Thread.currentThread().getName()+"======================pcData begin=============================");
////        taskService.pcData(ip + KEY_SPLIT + port + PC_DATA_URL);
////        log.info(Thread.currentThread().getName()+"======================pcData end=============================");
////    }


    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void dataCollectPush(){
        log.info(Thread.currentThread().getName()+"======================dataCollect begin=============================");
        taskService.dataCollectPush(ip + KEY_SPLIT + port + DATA_COLLECT_URL,localIp);
        log.info(Thread.currentThread().getName()+"======================dataCollect end=============================");
    }

//    @Scheduled(cron = "*/30 * * * * ?")
//    @Async
//    public void billPush(){
//        log.info(Thread.currentThread().getName()+"======================bill begin=============================");
//        taskService.billPush(ip + KEY_SPLIT + port + BILL_URL);
//        log.info(Thread.currentThread().getName()+"======================bill end=============================");
//    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void physicalDeviceHeartbeat(){
        log.info(Thread.currentThread().getName()+"======================physicalDeviceHeartbeat begin=============================");
        taskService.physicalDeviceHeartbeat(DateUtils.changeDate(new Date()),ip + KEY_SPLIT + port + PHYSICAL_DEVICE_HEARTBEAT_URL);
        log.info(Thread.currentThread().getName()+"======================physicalDeviceHeartbeat end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void physicalDeviceResource(){
        log.info(Thread.currentThread().getName()+"======================physicalDeviceResource begin=============================");
        taskService.physicalDeviceResource(DateUtils.changeDate(new Date()),ip + KEY_SPLIT + port + PHYSICAL_DEVICE_RESOURCE_URL);
        log.info(Thread.currentThread().getName()+"======================physicalDeviceResource end=============================");
        }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void systemResource(){
        log.info(Thread.currentThread().getName()+"======================systemResource begin=============================");
        taskService.systemResource(DateUtils.changeDate(new Date()),ip + KEY_SPLIT + port + SYSTEM_RESOURCE_URL);
        log.info(Thread.currentThread().getName()+"======================systemResource end=============================");
    }



//    @Scheduled(fixedDelay = 5000)
//    @Async·
//    public void fixedDelay() throws InterruptedException {
//        System.out.println(Thread.currentThread().getName() + "-fixedDelay:" + LocalDateTime.now().format(FORMATTER));
//        TimeUnit.SECONDS.sleep(6);
//    }
//
//    @Scheduled(fixedRate = 5000)
//    @Async
//    public void fixedRate() throws InterruptedException {
//        System.out.println(Thread.currentThread().getName() + "-fixedRate:" + LocalDateTime.now().format(FORMATTER));
//        TimeUnit.SECONDS.sleep(6);
//    }
}

