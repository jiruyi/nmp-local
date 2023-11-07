package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsServerConfig;
import com.matrictime.network.dao.model.NmpsServerConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsServerConfigMapper {
    long countByExample(NmpsServerConfigExample example);

    int deleteByExample(NmpsServerConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsServerConfig record);

    int insertSelective(NmpsServerConfig record);

    List<NmpsServerConfig> selectByExample(NmpsServerConfigExample example);

    NmpsServerConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsServerConfig record, @Param("example") NmpsServerConfigExample example);

    int updateByExample(@Param("record") NmpsServerConfig record, @Param("example") NmpsServerConfigExample example);

    int updateByPrimaryKeySelective(NmpsServerConfig record);

    int updateByPrimaryKey(NmpsServerConfig record);
}