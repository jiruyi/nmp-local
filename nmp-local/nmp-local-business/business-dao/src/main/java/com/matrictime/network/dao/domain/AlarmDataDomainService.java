package com.matrictime.network.dao.domain;

import com.matrictime.network.model.AlarmInfo;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 15:48
 * @desc
 */
public interface AlarmDataDomainService {

     int  acceptAlarmData(List<AlarmInfo> alarmInfoList);
}
