package com.matrictime.network.service;

import com.matrictime.network.dao.model.AlarmAndServerInfo;
import com.matrictime.network.dao.model.NmpsAlarmInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.req.AlarmDataListReq;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 15:04
 * @desc  警告数据处理服务
 */
public interface AlarmDataService {
    //数据推送
    Result acceptAlarmData(List<NmpsAlarmInfo> alarmInfoList, String redisKey);


    Result<PageInfo<AlarmAndServerInfo>> queryAlarmDataList(AlarmDataListReq alarmDataListReq);
}
