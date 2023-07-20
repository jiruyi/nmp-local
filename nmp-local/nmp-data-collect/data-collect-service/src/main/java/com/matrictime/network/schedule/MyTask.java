package com.matrictime.network.schedule;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/7/20 0020 10:20
 * @desc
 */
@Component
public class MyTask implements SchedulingConfigurer {
    private String cron = "*/5 * * * * ?";

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前时间："+new Date(System.currentTimeMillis()));
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExecutionTime = trigger.nextExecutionTime(triggerContext);
                return nextExecutionTime;
            }
        });
    }

    public void updateCron(String cron){
        this.cron = cron;
    }

}
