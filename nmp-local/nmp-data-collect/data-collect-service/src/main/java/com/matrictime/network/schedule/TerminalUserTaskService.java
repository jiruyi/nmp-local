package com.matrictime.network.schedule;

import com.matrictime.network.dao.domain.StationSummaryDomainService;
import com.matrictime.network.dao.domain.TerminalUserDomainService;
import com.matrictime.network.modelVo.StationSummaryVo;
import com.matrictime.network.modelVo.TerminalUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/22.
 */
@Slf4j
@Component
public class TerminalUserTaskService implements SchedulingConfigurer {

    //默认毫秒值
    private long timer = 3000;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private TerminalUserDomainService terminalUserDomainService;

    /**
     * 数据流量定时任务
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                //业务逻辑 查询数据
                List<TerminalUserVo> terminalUserVoList = terminalUserDomainService.selectTerminalUser();
                if(ObjectUtils.isEmpty(terminalUserVoList)){
                    return;
                }
                log.info("TerminalUserTaskService this time query data count：{}",terminalUserVoList.size());
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
     * 修改定时任务
     */
    public void updateTimer(long timer){
        this.timer = timer;
    }
}
