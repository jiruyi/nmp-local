package com.matrictime.network.service;

import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.model.Result;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 15:04
 * @desc  警告数据处理服务
 */
public interface AlarmDataService {

    Result acceptAlarmData(List<AlarmInfo> alarmInfoList);
}
