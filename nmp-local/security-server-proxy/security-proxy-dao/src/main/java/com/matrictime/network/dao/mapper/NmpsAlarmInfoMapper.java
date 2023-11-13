package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsAlarmInfo;
import com.matrictime.network.dao.model.NmpsAlarmInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsAlarmInfoMapper {
    long countByExample(NmpsAlarmInfoExample example);

    int deleteByExample(NmpsAlarmInfoExample example);

    int deleteByPrimaryKey(Long alarmId);

    int insert(NmpsAlarmInfo record);

    int insertSelective(NmpsAlarmInfo record);

    List<NmpsAlarmInfo> selectByExample(NmpsAlarmInfoExample example);

    NmpsAlarmInfo selectByPrimaryKey(Long alarmId);

    int updateByExampleSelective(@Param("record") NmpsAlarmInfo record, @Param("example") NmpsAlarmInfoExample example);

    int updateByExample(@Param("record") NmpsAlarmInfo record, @Param("example") NmpsAlarmInfoExample example);

    int updateByPrimaryKeySelective(NmpsAlarmInfo record);

    int updateByPrimaryKey(NmpsAlarmInfo record);
}