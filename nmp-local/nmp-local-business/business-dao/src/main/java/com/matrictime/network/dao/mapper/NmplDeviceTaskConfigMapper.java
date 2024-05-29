package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDeviceTaskConfig;
import com.matrictime.network.dao.model.NmplDeviceTaskConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplDeviceTaskConfigMapper {
    long countByExample(NmplDeviceTaskConfigExample example);

    int deleteByExample(NmplDeviceTaskConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDeviceTaskConfig record);

    int insertSelective(NmplDeviceTaskConfig record);

    List<NmplDeviceTaskConfig> selectByExample(NmplDeviceTaskConfigExample example);

    NmplDeviceTaskConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDeviceTaskConfig record, @Param("example") NmplDeviceTaskConfigExample example);

    int updateByExample(@Param("record") NmplDeviceTaskConfig record, @Param("example") NmplDeviceTaskConfigExample example);

    int updateByPrimaryKeySelective(NmplDeviceTaskConfig record);

    int updateByPrimaryKey(NmplDeviceTaskConfig record);
}