package com.matrictime.network.service.impl;

import com.matrictime.network.convert.AlarmInfoConvert;
import com.matrictime.network.dao.domain.AlarmDomainService;
import com.matrictime.network.dao.model.NmplAlarmInfo;
import com.matrictime.network.facade.AlarmDataFacade;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.DataPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
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
    public  void alarmPush() {
        try {
            //查询数据
            List<NmplAlarmInfo> alarmInfoList =  alarmDomainService.queryAlarmList();
            if(CollectionUtils.isEmpty(alarmInfoList)){
                return;
            }
            log.info("alarmPush this time query data count：{}",alarmInfoList.size());
            //推送数据
            Result pushResult = alarmDataFacade.acceptAlarmData(alarmInfoConvert.to(alarmInfoList));
            if(ObjectUtils.isEmpty(pushResult) ||  !pushResult.isSuccess()){
                return;
            }
            log.info("alarmDataFacade.acceptAlarmData push data count :{}",pushResult.getResultObj());
            //删除此次推送之前的数据
            Long maxAlarmId = alarmInfoList.stream().max(Comparator.comparingLong(NmplAlarmInfo::getAlarmId)).get().getAlarmId();
            log.info("alarmPush this time maxAlarmId ：{}",maxAlarmId);
            int deleteCount =  alarmDomainService.deleteThisTimePushData(maxAlarmId);
            log.info("alarmPush this time delete data count：{}",deleteCount);
        }catch (Exception e){
            log.error("DataPushService alarmPush exception:{}",e.getMessage());
        }
    }

}
