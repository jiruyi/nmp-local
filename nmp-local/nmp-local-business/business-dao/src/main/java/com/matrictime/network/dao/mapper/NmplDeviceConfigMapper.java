package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDeviceConfig;
import com.matrictime.network.dao.model.NmplDeviceConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplDeviceConfigMapper {
    long countByExample(NmplDeviceConfigExample example);

    int deleteByExample(NmplDeviceConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDeviceConfig record);

    int insertSelective(NmplDeviceConfig record);

    List<NmplDeviceConfig> selectByExample(NmplDeviceConfigExample example);

    NmplDeviceConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDeviceConfig record, @Param("example") NmplDeviceConfigExample example);

    int updateByExample(@Param("record") NmplDeviceConfig record, @Param("example") NmplDeviceConfigExample example);

    int updateByPrimaryKeySelective(NmplDeviceConfig record);

    int updateByPrimaryKey(NmplDeviceConfig record);
}