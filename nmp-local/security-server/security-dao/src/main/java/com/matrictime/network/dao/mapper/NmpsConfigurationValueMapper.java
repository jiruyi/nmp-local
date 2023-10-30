package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsConfigurationValue;
import com.matrictime.network.dao.model.NmpsConfigurationValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsConfigurationValueMapper {
    long countByExample(NmpsConfigurationValueExample example);

    int deleteByExample(NmpsConfigurationValueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsConfigurationValue record);

    int insertSelective(NmpsConfigurationValue record);

    List<NmpsConfigurationValue> selectByExample(NmpsConfigurationValueExample example);

    NmpsConfigurationValue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsConfigurationValue record, @Param("example") NmpsConfigurationValueExample example);

    int updateByExample(@Param("record") NmpsConfigurationValue record, @Param("example") NmpsConfigurationValueExample example);

    int updateByPrimaryKeySelective(NmpsConfigurationValue record);

    int updateByPrimaryKey(NmpsConfigurationValue record);
}