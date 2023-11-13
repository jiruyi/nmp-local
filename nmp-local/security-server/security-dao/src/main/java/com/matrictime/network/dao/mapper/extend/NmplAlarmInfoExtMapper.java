package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.AlarmAndServerInfo;
import com.matrictime.network.dao.model.NmpsAlarmInfo;
import com.matrictime.network.req.AlarmDataListReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 15:58
 * @desc
 */
public interface NmplAlarmInfoExtMapper {

    int batchInsert(@Param("alarmInfoList") List<NmpsAlarmInfo> alarmInfoList);

    List<AlarmAndServerInfo> queryAlarmDataList(AlarmDataListReq alarmDataListReq);


}
