package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDeviceLog;
import com.matrictime.network.dao.model.NmplDeviceLogExample;
import com.matrictime.network.model.DeviceLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplDeviceLogMapper {
    long countByExample(NmplDeviceLogExample example);

    int deleteByExample(NmplDeviceLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDeviceLog record);

    int insertSelective(NmplDeviceLog record);

    List<NmplDeviceLog> selectByExample(NmplDeviceLogExample example);

    NmplDeviceLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDeviceLog record, @Param("example") NmplDeviceLogExample example);

    int updateByExample(@Param("record") NmplDeviceLog record, @Param("example") NmplDeviceLogExample example);

    int updateByPrimaryKeySelective(NmplDeviceLog record);

    int updateByPrimaryKey(NmplDeviceLog record);

    List<NmplDeviceLog> queryDeviceLogList(DeviceLog deviceLog);
}