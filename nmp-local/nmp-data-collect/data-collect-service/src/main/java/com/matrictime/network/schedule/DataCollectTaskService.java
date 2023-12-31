package com.matrictime.network.schedule;


import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.BusinessDataEnum;
import com.matrictime.network.base.enums.BusinessTypeEnum;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.util.TcpTransportUtil;
import com.matrictime.network.dao.domain.*;
import com.matrictime.network.dao.model.NmplBusinessRoute;
import com.matrictime.network.modelVo.CompanyInfoVo;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.netty.client.NettyClient;
import com.matrictime.network.response.DataCollectResponse;
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
@BusinessType(businessType = BusinessTypeEnum.DATA_TRAFFIC)
public class DataCollectTaskService implements SchedulingConfigurer, BusinessDataService {
    //默认毫秒值
    private long timer = 300000;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private DeviceDomainService deviceDomainService;

    @Autowired
    private NettyClient nettyClient;

    @Resource
    private DataCollectDomainService collectDomainService;

    @Resource
    private ConfigDomainService configDomainService;

    @Resource
    private CompanyHeartbeatDomainService heartbeatDomainService;

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
        Boolean report = configDomainService.isReport(BusinessTypeEnum.DATA_TRAFFIC.getCode());
        log.info("DataCollectTaskService isReport:{}",report);
        if(!report){
            log.info("DataCollectTaskService isReport:{},不做上报处理",report);
            return;
        }
        try {
            //业务逻辑 查询数据
            List<DataCollectVo> dataCollectVos = collectDomainService.selectDataCollect();
            if(CollectionUtils.isEmpty(dataCollectVos)){
                return;
            }
            //查询本机数据采集和本运营商的指控中心的入网码
            String dataNetworkId = deviceDomainService.getNetworkIdByType(DeviceTypeEnum.DAT_COLLECT.getCode());
            NmplBusinessRoute route = deviceDomainService.getBusinessRoute();
            if(StringUtils.isEmpty(dataNetworkId) || ObjectUtils.isEmpty(route)){
                log.info("查询dataNetworkId 或 commandNetworkId为空,作返回处理");
                return;
            }
            String commandNetworkId = route.getNetworkId();
            log.info("DataCollectTaskService  businessData dataNetworkId:{} commandNetworkId:{}",dataNetworkId,commandNetworkId);
            //业务数据转jsonString
            String reqDataStr = JSONObject.toJSONString(dataCollectVos);
            //发送TCP数据包
            ChannelFuture channelFuture =
                    nettyClient.sendMsg(TcpTransportUtil.getTcpDataPushVo(BusinessDataEnum.DataCollect,
                            reqDataStr, commandNetworkId, dataNetworkId));
            //阻塞等待结果
            channelFuture.get();
            if(channelFuture.isDone()){
                if (!channelFuture.isSuccess()){
                    log.info("dataCollectTaskService  nettyClient.sendMsg error :{}", channelFuture.cause());
                    return;
                }
                if(channelFuture.isSuccess()){
                    Long maxId = dataCollectVos.get(dataCollectVos.size()-1).getId();
                    log.info("dataCollect 此次推送的最大 maxId is :{}", maxId);
                    heartbeatDomainService.insertDataPushRecord(maxId, DataConstants.NMPL_DATA_COLLECT);
                }
            }
        } catch (Exception e) {
            log.error("DataCollectTaskService configureTasks exception:{}", e);
            return;
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
