package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplAlarmInfo;
import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.request.AlarmDataListReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 15:58
 * @desc
 */
public interface NmplAlarmInfoExtMapper {

    int batchInsert(@Param("alarmInfoList") List<AlarmInfo> alarmInfoList);

    List<Map> selectSysAlarmCount(@Param("paramMap") Map<String,String> paramMap);

    List<Map> selectPhyAlarmCount(@Param("paramMap") Map<String,String> paramMap);

    List<Map>  selectPhyAlarmCountByDates(@Param("paramMap") Map paramMap);

    List<NmplAlarmInfo> queryAlarmDataList(AlarmDataListReq alarmDataListReq);

    List<String> selectIpFromDeviceAndStation(String roId);

}
