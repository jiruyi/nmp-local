package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplAlarmInfo;
import com.matrictime.network.dao.model.NmplAlarmInfoExample;
import com.matrictime.network.dao.model.extend.NmplAlarmInfoExt;
import com.matrictime.network.modelVo.AlarmInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplAlarmInfoMapper {
    long countByExample(NmplAlarmInfoExample example);

    int deleteByExample(NmplAlarmInfoExample example);

    int deleteByPrimaryKey(Long alarmId);

    int insert(NmplAlarmInfo record);

    int insertSelective(NmplAlarmInfo record);

    List<NmplAlarmInfo> selectByExample(NmplAlarmInfoExample example);

    NmplAlarmInfo selectByPrimaryKey(Long alarmId);

    int updateByExampleSelective(@Param("record") NmplAlarmInfo record, @Param("example") NmplAlarmInfoExample example);

    int updateByExample(@Param("record") NmplAlarmInfo record, @Param("example") NmplAlarmInfoExample example);

    int updateByPrimaryKeySelective(NmplAlarmInfo record);

    int updateByPrimaryKey(NmplAlarmInfo record);

    List<NmplAlarmInfoExt> selectListFromAlarmAndCompany(NmplAlarmInfoExample example);

    int batchInsert(@Param("alarmInfoList") List<AlarmInfo> alarmInfoList);
}