package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.model.AlarmInfo;
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
    int batchInsert(@Param("alarmInfoList") List<AlarmInfo> alarmInfoList);
}
