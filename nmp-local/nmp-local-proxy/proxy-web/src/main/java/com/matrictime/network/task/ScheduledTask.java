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


    /**
     * @title heartReport
     * @return void
     * @description  站点状态上报定时任务
     * @author hx
     * @create 2023/4/19 0019 17:25
     */
    @Scheduled(cron = "*/30 * * * * ?")
    @Async
    public void heartReport(){
        log.info(Thread.currentThread().getName()+"======================heartReport begin=============================");
        taskService.heartReport(new Date());
        log.info(Thread.currentThread().getName()+"======================heartReport end=============================");
    }


    /**
      * @title alarmPush
      * @return void
      * @description  告警信息定时任务
      * @author jiruyi
      * @create 2023/4/19 0019 17:25
      */
    @Scheduled(cron = "0 0/30 * * * ? ")
    public void alarmPush(){
        log.info(Thread.currentThread().getName()+ new Date()+"======================alarmPush begin=============================");
        dataPushService.alarmPush();
        log.info(Thread.currentThread().getName()+new Date()+"======================alarmPush end=============================");
    }

    /**
     * @title dataCollectPush
     * @return void
     * @description 统计数据定时任务
     * @author zyj
     * @create 2023/5/31 14:25
     */
    //@Scheduled(cron = "0 */1 * * * ?")
    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void dataCollectPush(){
        log.info(Thread.currentThread().getName()+"======================dataCollect begin=============================");
        taskService.dataCollectPush(ip + KEY_SPLIT + port + DATA_COLLECT_URL,localIp);
        log.info(Thread.currentThread().getName()+"======================dataCollect end=============================");
    }

    /**
     * @title physicalDeviceHeartbeat
     * @return void
     * @description  物理设备心跳上报定时任务
     * @author hx
     * @create 2023/4/19 0019 17:25
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void physicalDeviceHeartbeat(){
        log.info(Thread.currentThread().getName()+"======================physicalDeviceHeartbeat begin=============================");
        taskService.physicalDeviceHeartbeat(DateUtils.changeDate(new Date()));
        log.info(Thread.currentThread().getName()+"======================physicalDeviceHeartbeat end=============================");
    }

    /**
     * @title physicalDeviceResource
     * @return void
     * @description  物理设备资源上报定时任务
     * @author hx
     * @create 2023/4/19 0019 17:25
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void physicalDeviceResource(){
        log.info(Thread.currentThread().getName()+"======================physicalDeviceResource begin=============================");
        taskService.physicalDeviceResource(DateUtils.changeDate(new Date()));
        log.info(Thread.currentThread().getName()+"======================physicalDeviceResource end=============================");
        }

    /**
     * @title systemResource
     * @return void
     * @description  系统资源上报定时任务
     * @author hx
     * @create 2023/4/19 0019 17:25
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void systemResource(){
        log.info(Thread.currentThread().getName()+"======================systemResource begin=============================");
        taskService.systemResource(DateUtils.changeDate(new Date()));
        log.info(Thread.currentThread().getName()+"======================systemResource end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void systemHeartbeat(){
        log.info(Thread.currentThread().getName()+"======================SystemHeartbeat begin=============================");
        taskService.systemHeartbeat(ip + KEY_SPLIT + port + SYSTEM_HEARTBEAT_URL);
        log.info(Thread.currentThread().getName()+"======================SystemHeartbeat end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void terminalUser(){
        log.info(Thread.currentThread().getName()+"======================TerminalUser begin=============================");
        taskService.terminalUser(ip + KEY_SPLIT + port + TERMINAL_USER_URL);
        log.info(Thread.currentThread().getName()+"======================TerminalUser end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void terminalData(){
        log.info(Thread.currentThread().getName()+"======================terminalData begin=============================");
        taskService.collectTerminalData(ip + KEY_SPLIT + port + TERMINAL_DATA_URL);
        log.info(Thread.currentThread().getName()+"======================terminalData end=============================");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Async
    public void updateCurrentConnectCount(){
        log.info(Thread.currentThread().getName()+"======================updateCurrentConnectCount begin=============================");
        taskService.updateCurrentConnectCount(ip + KEY_SPLIT + port + UPDATE_CURRENT_URL);
        log.info(Thread.currentThread().getName()+"======================updateCurrentConnectCount end=============================");
    }

}

