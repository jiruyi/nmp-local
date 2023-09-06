package com.matrictime.network.schedule;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.enums.BusinessDataEnum;
import com.matrictime.network.base.enums.BusinessTypeEnum;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.util.TcpTransportUtil;
import com.matrictime.network.dao.domain.AlarmDomainService;
import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.dao.model.NmplAlarmInfo;
import com.matrictime.network.netty.client.NettyClient;
import com.matrictime.network.service.BusinessDataService;
import com.matrictime.network.strategy.annotation.BusinessType;
import io.netty.channel.ChannelFuture;
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
import java.util.ArrayList;
import java.util.Comparator;
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
@BusinessType(businessType = BusinessTypeEnum.ALARM_DATA)
public class AlarmInfoTaskService implements BusinessDataService, SchedulingConfigurer {

    //默认毫秒值
    private long timer = 60000;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private AlarmDomainService alarmDomainService;

    @Autowired
    private DeviceDomainService deviceDomainService;

    @Autowired
    private NettyClient nettyClient;

    /**
     * @param [scheduledTaskRegistrar]
     * @return void
     * @title configureTasks
     * @description 注册定时任务
     * @author jiruyi
     * @create 2023/7/20 0020 11:19
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                businessData();
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
     * @param []
     * @return void
     * @title businessData
     * @description
     * @author jiruyi
     * @create 2023/8/28 0028 17:44
     */
    @Override
    public void businessData() {
        List<NmplAlarmInfo> alarmInfoList = new ArrayList<>();
        try {
            //告警日志业务逻辑 查询数据
            alarmInfoList = alarmDomainService.queryAlarmList();
            if (CollectionUtils.isEmpty(alarmInfoList)) {
                return;
            }
            //查询数据采集和指控中心的入网码
            String dataNetworkId = deviceDomainService.getNetworkIdByType(DeviceTypeEnum.DAT_COLLECT.getCode());
            String comNetworkId = deviceDomainService.getNetworkIdByType(DeviceTypeEnum.COMMAND_CENTER.getCode());
//            if(StringUtils.isEmpty(dataNetworkId) || StringUtils.isEmpty(comNetworkId)){
//                return;
//            }
            String reqDataStr = JSONObject.toJSONString(alarmInfoList);
            //todo 与边界基站通信 netty ip port 需要查询链路关系 并做出变更
            ChannelFuture channelFuture =
                    nettyClient.sendMsg(TcpTransportUtil.getTcpDataPushVo(BusinessDataEnum.AlarmInfo,
                            reqDataStr, "8600-0001-0001-0001-00000008", "8600-0001-0001-0001-00000008"));
            channelFuture.get();
            if(channelFuture.isDone()){
                if (!channelFuture.isSuccess()){
                    log.info("alarmPush  nettyClient.sendMsg error :{}", channelFuture.cause());
                    return;
                }
                if(channelFuture.isSuccess()){
                    //修改nmpl_data_push_record 数据推送记录表
                    Long maxAlarmId = alarmInfoList.stream().max(Comparator.comparingLong(NmplAlarmInfo::getAlarmId))
                            .get().getAlarmId();
                    log.info("此次推送的最大 alarm_id is :{}", maxAlarmId);
                    alarmDomainService.insertDataPushRecord(maxAlarmId);
                }
            }
            log.info("alarmPush this time query data count：{}", alarmInfoList.size());
        } catch (Exception e) {
            log.error("AlarmInfoTaskService configureTasks exception:{}", e);
            return;
        }
    }

    /**
     * @param [cron]
     * @return void
     * @title updateCron
     * @description 修改定时任务
     * @author jiruyi
     * @create 2023/7/20 0020 11:19
     */
    @Override
    public void updateTimer(long timer) {
        this.timer = timer;
    }

}
