package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.AlarmDataDomainService;
import com.matrictime.network.dao.mapper.NmplAlarmInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplAlarmInfoExtMapper;
import com.matrictime.network.model.AlarmInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 15:50
 * @desc  警告信息数据处理
 */
@Service
@Slf4j
public class AlarmDataDomainServiceImpl extends SystemBaseService implements AlarmDataDomainService {

     @Autowired
     private NmplAlarmInfoMapper alarmInfoMapper;

     @Autowired
     private NmplAlarmInfoExtMapper alarmInfoExtMapper;
    /**
      * @title acceptAlarmData
      * @param [alarmInfoList]
      * @return com.matrictime.network.model.Result
      * @description  数据推送入库
      * @author jiruyi
      * @create 2023/4/19 0019 15:51
      */
    @Override
    public int acceptAlarmData(List<AlarmInfo> alarmInfoList) {
        if(CollectionUtils.isEmpty(alarmInfoList)){
            return NumberUtils.INTEGER_ZERO;
        }
       return  alarmInfoExtMapper.batchInsert(alarmInfoList);
    }
}
