package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.AlarmAndServerInfo;
import com.matrictime.network.dao.model.NmpsAlarmInfo;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.req.AlarmDataListReq;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 11:16
 * @desc 告警内容 数据库操作
 */
public interface AlarmDomainService {

    PageInfo<AlarmAndServerInfo> queryAlarmDataList(AlarmDataListReq alarmDataListReq);


    int  acceptAlarmData(List<NmpsAlarmInfo> alarmInfoList,String redisKey);
}
