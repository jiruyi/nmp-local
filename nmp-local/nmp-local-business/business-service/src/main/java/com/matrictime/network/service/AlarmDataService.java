package com.matrictime.network.service;

import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.AlarmDataBaseRequest;
import com.matrictime.network.request.AlarmDataListReq;
import com.matrictime.network.response.AlarmDataPhyResp;
import com.matrictime.network.response.AlarmDataSysResp;
import com.matrictime.network.response.PageInfo;

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
    Result acceptAlarmData(List<AlarmInfo> alarmInfoList,String cpuId);
    //系统告警数据查询
    Result<AlarmDataSysResp> querySysAlarmDataCount(AlarmDataBaseRequest alarmDataBaseRequest);

    //物理设备告警数据查询
    Result<AlarmDataPhyResp> queryPhyAlarmDataCount(AlarmDataBaseRequest alarmDataBaseRequest);

    Result<PageInfo<AlarmInfo>> queryAlarmDataList(AlarmDataListReq alarmDataListReq);
}
