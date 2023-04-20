package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplAlarmInfo;

import java.util.Date;
import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 11:16
 * @desc 告警内容 数据库操作
 */
public interface AlarmDomainService {

    List<NmplAlarmInfo> queryAlarmList();
    int deleteThisTimePushData(Date uploadTime);
}
