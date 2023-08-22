package com.matrictime.network.schedule;

import com.matrictime.network.dao.domain.AlarmDomainService;
import com.matrictime.network.dao.model.NmplAlarmInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/7/20 0020 10:20
 * @desc
 */
@Slf4j
@Component
public class AlarmInfoTaskService implements SchedulingConfigurer {

    //默认毫秒值
    private long timer = 3000;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private  AlarmDomainService alarmDomainService;
    /**
      * @title configureTasks
      * @param [scheduledTaskRegistrar]
      * @return void
      * @description  注册定时任务
      * @author jiruyi
      * @create 2023/7/20 0020 11:19
      */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                //业务逻辑 查询数据
                List<NmplAlarmInfo> alarmInfoList =  alarmDomainService.queryAlarmList();
                if(CollectionUtils.isEmpty(alarmInfoList)){
                    return;
                }
                log.info("alarmPush this time query data count：{}",alarmInfoList.size());
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //此处使用不同的触发器，为设置循环时间的关键，区别于CronTrigger触发器，
                //该触发器可随意设置循环间隔时间，单位为毫秒
                PeriodicTrigger periodicTrigger = new PeriodicTrigger(timer);
                Date nextExecutionTime = periodicTrigger.nextExecutionTime(triggerContext);
                return nextExecutionTime;
            }
        });
    }

    /**
      * @title updateCron
      * @param [cron]
      * @return void
      * @description  修改定时任务
      * @author jiruyi
      * @create 2023/7/20 0020 11:19
      */
    public void updateTimer(long timer){
        this.timer = timer;
    }

}
