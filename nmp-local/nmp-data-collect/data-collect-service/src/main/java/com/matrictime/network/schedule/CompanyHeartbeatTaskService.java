package com.matrictime.network.schedule;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.enums.BusinessDataEnum;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.util.TcpTransportUtil;
import com.matrictime.network.dao.domain.*;
import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.netty.client.NettyClient;
import com.matrictime.network.service.BusinessDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/22.
 */
@Slf4j
@Component
public class CompanyHeartbeatTaskService implements SchedulingConfigurer, BusinessDataService {

    //默认毫秒值
    private long timer = 300000;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private CompanyHeartbeatDomainService heartbeatDomainService;

    @Autowired
    private DeviceDomainService deviceDomainService;

    @Autowired
    private NettyClient nettyClient;

    @Resource
    private StationSummaryDomainService summaryDomainService;

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
        //业务逻辑 查询数据
        List<CompanyHeartbeatVo> list = heartbeatDomainService.selectCompanyHeartbeat();
        if(CollectionUtils.isEmpty(list)){
            return;
        }

        //查询数据采集和指控中心的入网码
        String dataNetworkId = deviceDomainService.getNetworkIdByType(DeviceTypeEnum.DAT_COLLECT.getCode());
        String comNetworkId = deviceDomainService.getNetworkIdByType(DeviceTypeEnum.COMMAND_CENTER.getCode());
        if(StringUtils.isEmpty(dataNetworkId) || StringUtils.isEmpty(comNetworkId)){
            return;
        }
        String reqDataStr = JSONObject.toJSONString(list);
        //todo 与边界基站通信 netty ip port 需要查询链路关系 并做出变更
//        nettyClient.sendMsg(TcpTransportUtil.getTcpDataPushVo(BusinessDataEnum.CompanyHeartbeat,
//                reqDataStr,comNetworkId,dataNetworkId));
        log.info("companyHeartbeatPush this time query data count：{}",list.size());
        //修改nmpl_data_push_record 数据推送记录表
        Long maxCompanyHeartId = list.stream().max(Comparator.comparingLong(CompanyHeartbeatVo::getId))
                .get().getId();
        log.info("此次推送的最大 company_heart_id is :{}",maxCompanyHeartId);
        summaryDomainService.insertDataPushRecord(maxCompanyHeartId,BusinessDataEnum.CompanyHeartbeat.getTableName());

        log.info("CompanyHeartbeatTaskService this time query data count：{}",list.size());
    }

    /**
     * 修改定时任务
     */
    @Override
    public void updateTimer(long timer){
        this.timer = timer;
    }
}
