package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.AlarmDataDomainService;
import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.AlarmDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 16:21
 * @desc 告警信息 服务
 */
@Service
@Slf4j
public class AlarmDataServiceImpl  extends SystemBaseService implements AlarmDataService {


    @Autowired
    private AlarmDataDomainService alarmDataDomainService;
    /**
      * @title acceptAlarmData
      * @param [alarmInfoList]
      * @return com.matrictime.network.model.Result
      * @description  告警信息入库
      * @author jiruyi
      * @create 2023/4/19 0019 16:22
      */
    @Override
    public Result acceptAlarmData(List<AlarmInfo> alarmInfoList) {
        //参数校验
        if (CollectionUtils.isEmpty(alarmInfoList)){
            return  failResult("acceptAlarmData alarmInfoList is null");
        }
        try {
            //插入数据库
            log.info("alarmInfoList param size is: {}",alarmInfoList.size());
            int count = alarmDataDomainService.acceptAlarmData(alarmInfoList);
            log.info("alarmDataDomainService save mysql  count is: {}",count);
            return buildResult(count);
        }catch (Exception e){
            log.error("AlarmDataService acceptAlarmData exception : {} ",e.getMessage());
            return failResult(e);
        }

    }
}
