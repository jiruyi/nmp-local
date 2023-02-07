package com.matrictime.network.task;

import com.matrictime.network.service.TaskService;
import com.matrictime.network.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
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

//    @Value("${task.saveDays}")
//    private Integer saveDays;


//    @Scheduled(cron = "* * * */1 * ?")
//    @Async
//    public void delKeyInfoData(){
//        Date date = new Date();
//        log.info(Thread.currentThread().getName()+"======================delKeyInfoData begin=============================param:Date["+ DateUtils.dateToStringIfEmpty(date)+"];+saveDays["+saveDays+"]");
//        int i = taskService.delKeyInfoData(date,saveDays);
//        log.info(Thread.currentThread().getName()+"======================delKeyInfoData end=============================result:"+i);
//    }

}

