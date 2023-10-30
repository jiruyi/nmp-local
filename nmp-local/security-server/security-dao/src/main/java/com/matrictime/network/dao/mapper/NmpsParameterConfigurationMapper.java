package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsParameterConfiguration;
import com.matrictime.network.dao.model.NmpsParameterConfigurationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsParameterConfigurationMapper {
    long countByExample(NmpsParameterConfigurationExample example);

    int deleteByExample(NmpsParameterConfigurationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsParameterConfiguration record);

    int insertSelective(NmpsParameterConfiguration record);

    List<NmpsParameterConfiguration> selectByExample(NmpsParameterConfigurationExample example);

    NmpsParameterConfiguration selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsParameterConfiguration record, @Param("example") NmpsParameterConfigurationExample example);

    int updateByExample(@Param("record") NmpsParameterConfiguration record, @Param("example") NmpsParameterConfigurationExample example);

    int updateByPrimaryKeySelective(NmpsParameterConfiguration record);

    int updateByPrimaryKey(NmpsParameterConfiguration record);
}