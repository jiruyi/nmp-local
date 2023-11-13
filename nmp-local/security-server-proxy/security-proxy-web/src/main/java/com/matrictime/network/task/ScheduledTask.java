package com.matrictime.network.task;

import com.matrictime.network.service.TaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableAsync //开启多线程
@Slf4j
public class ScheduledTask {

    @Autowired
    private TaskService taskService;


    /**
     * @title initData
     * @return void
     * @description  安全服务器配置同步定时任务
     * @author hx
     * @create 2023/4/19 0019 17:25
     */
    @XxlJob("initData")
    public void initData(){
        log.info(Thread.currentThread().getName()+"======================initData begin=============================");
        taskService.initData();
        log.info(Thread.currentThread().getName()+"======================initData end=============================");
    }



    /**
     * @title heartReport
     * @return void
     * @description  安全服务器状态上报定时任务
     * @author hx
     * @create 2023/4/19 0019 17:25
     */
    @XxlJob("heartReport")
    public void heartReport(){
        log.info(Thread.currentThread().getName()+"======================heartReport begin=============================");
        taskService.heartReport(new Date());
        log.info(Thread.currentThread().getName()+"======================heartReport end=============================");
    }



    @XxlJob("dataInfo")
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void dataPush(){
        log.info(Thread.currentThread().getName()+"======================dataPush begin=============================");
        taskService.dataPush();
        log.info(Thread.currentThread().getName()+"======================dataPush end=============================");
    }
}

