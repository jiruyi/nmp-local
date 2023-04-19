package com.matrictime.network.service.impl;

import com.matrictime.network.convert.AlarmInfoConvert;
import com.matrictime.network.dao.domain.AlarmDomainService;
import com.matrictime.network.dao.model.NmplAlarmInfo;
import com.matrictime.network.facade.AlarmDataFacade;
import com.matrictime.network.service.DataPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 11:13
 * @desc 数据推送服务
 */
@Slf4j
@Service
public class DataPushServiceImpl implements DataPushService {

    @Autowired
    private  AlarmDomainService alarmDomainService;

    @Autowired
    private AlarmDataFacade alarmDataFacade;

    @Autowired
    private AlarmInfoConvert alarmInfoConvert;
    /**
     * @title alarmPush
     * @param
     * @return void
     * @description  查询代理库信息告警表  组装数据 推送到中心  删除数据
     * @author jiruyi
     * @create 2023/4/19 0019 11:13
     */
    @Override
    public void alarmPush() {
        try {
            List<NmplAlarmInfo> alarmInfoList =  alarmDomainService.queryAlarmList();
            if(CollectionUtils.isEmpty(alarmInfoList)){
                return;
            }
            alarmDataFacade.acceptAlarmData(alarmInfoConvert.to(alarmInfoList));
        }catch (Exception e){
            log.error("DataPushService alarmPush exception:{}",e.getMessage());
        }


    }
}
