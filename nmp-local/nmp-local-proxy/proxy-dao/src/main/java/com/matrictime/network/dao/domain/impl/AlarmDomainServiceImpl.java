package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.AlarmDomainService;
import com.matrictime.network.dao.mapper.NmplAlarmInfoMapper;
import com.matrictime.network.dao.model.NmplAlarmInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 11:18
 * @desc 告警信息数据库表
 */
@Service
public class AlarmDomainServiceImpl  implements AlarmDomainService {

    @Autowired
    private NmplAlarmInfoMapper alarmInfoMapper;

    /**
      * @title queryAlarmList
      * @param []
      * @return java.util.List<com.matrictime.network.dao.model.NmplAlarmInfo>
      * @description  查询告警表信息
      * @author jiruyi
      * @create 2023/4/19 0019 11:19
      */
    @Override
    public List<NmplAlarmInfo> queryAlarmList() {
        List<NmplAlarmInfo> infoList =  alarmInfoMapper.selectWithOutIdByExample(null);
        return infoList;
    }
}
