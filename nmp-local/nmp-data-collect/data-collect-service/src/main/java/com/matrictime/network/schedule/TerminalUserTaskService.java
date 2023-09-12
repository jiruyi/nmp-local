package com.matrictime.network.schedule;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.enums.BusinessDataEnum;
import com.matrictime.network.base.enums.BusinessTypeEnum;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.util.TcpTransportUtil;
import com.matrictime.network.dao.domain.*;
import com.matrictime.network.modelVo.TerminalUserVo;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author by wangqiang
 * @date 2023/8/22.
 */
@Slf4j
@Component
@BusinessType(businessType = BusinessTypeEnum.TERMINAL_USER)
public class TerminalUserTaskService implements SchedulingConfigurer, BusinessDataService {

    //默认毫秒值
    private long timer = 300000;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private StationSummaryDomainService summaryDomainService;

    @Resource
    private TerminalUserDomainService terminalUserDomainService;

    @Autowired
    private DeviceDomainService deviceDomainService;

    @Autowired
    private NettyClient nettyClient;

    @Resource
    private ConfigDomainService configDomainService;

    /**
     * 数据流量定时任务
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

    @Override
    public void businessData() {

        Boolean report = configDomainService.isReport(BusinessTypeEnum.TERMINAL_USER.getCode());
        if(!report){
            return;
        }

        //业务逻辑 查询数据
        List<TerminalUserVo> terminalUserVoList = terminalUserDomainService.selectTerminalUser();
        if(ObjectUtils.isEmpty(terminalUserVoList)){
            return;
        }

        //查询数据采集和指控中心的入网码
        String dataNetworkId = deviceDomainService.getNetworkIdByType(DeviceTypeEnum.DAT_COLLECT.getCode());
        String commandNetworkId = deviceDomainService.getNetworkIdByType(DeviceTypeEnum.COMMAND_CENTER.getCode());
        if(StringUtils.isEmpty(dataNetworkId) || StringUtils.isEmpty(commandNetworkId)){
            return;
        }
        String reqDataStr = JSONObject.toJSONString(terminalUserVoList);
        //todo 与边界基站通信 netty ip port 需要查询链路关系 并做出变更
        log.info("terminalUserPush this time query data count：{}",terminalUserVoList.size());
        ChannelFuture channelFuture =
                nettyClient.sendMsg(TcpTransportUtil.getTcpDataPushVo(BusinessDataEnum.CompanyHeartbeat,
                        reqDataStr, commandNetworkId, dataNetworkId));
        try {
            channelFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(channelFuture.isDone()){
            if (!channelFuture.isSuccess()){
                log.info("terminalUserPush nettyClient.sendMsg error :{}", channelFuture.cause());
                return;
            }
            if(channelFuture.isSuccess()){
                //修改nmpl_data_push_record 数据推送记录表
                Long maxTerminalUserId = terminalUserVoList.stream().max(Comparator.comparingLong(TerminalUserVo::getId))
                        .get().getId();
                log.info("此次推送的最大 terminal_user_id is :{}",maxTerminalUserId);
                summaryDomainService.insertDataPushRecord(maxTerminalUserId, BusinessDataEnum.TerminalUser.getTableName());
            }
        }

    }

    /**
     * 修改定时任务
     */
    @Override
    public void updateTimer(long timer){
        this.timer = timer;
    }
}
