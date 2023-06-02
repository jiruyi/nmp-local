package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplAlarmInfo;
import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.request.AlarmDataBaseRequest;
import com.matrictime.network.request.AlarmDataListReq;
import com.matrictime.network.response.AlarmPhyTypeCount;
import com.matrictime.network.response.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 15:48
 * @desc
 */
public interface AlarmDataDomainService {

     int  acceptAlarmData(List<AlarmInfo> alarmInfoList,String cpuId);

     void alarmSysCountDataToRedis(List<AlarmInfo> alarmInfoList);

     void alarmPhyCountDataToRedis(List<AlarmInfo> alarmInfoList);

     Map<String, Map<String,Long>> querySysAlarmDataCount(AlarmDataBaseRequest alarmDataBaseRequest) throws Exception;

     List<AlarmPhyTypeCount> queryPhyAlarmDataCount(AlarmDataBaseRequest alarmDataBaseRequest);

     PageInfo<NmplAlarmInfo> queryAlarmDataList(AlarmDataListReq alarmDataListReq);


}
