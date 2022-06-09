package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplConfiguration;
import com.matrictime.network.dao.model.NmplConfigurationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplConfigurationMapper {
    long countByExample(NmplConfigurationExample example);

    int deleteByExample(NmplConfigurationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplConfiguration record);

    int insertSelective(NmplConfiguration record);

    List<NmplConfiguration> selectByExample(NmplConfigurationExample example);

    NmplConfiguration selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplConfiguration record, @Param("example") NmplConfigurationExample example);

    int updateByExample(@Param("record") NmplConfiguration record, @Param("example") NmplConfigurationExample example);

    int updateByPrimaryKeySelective(NmplConfiguration record);

    int updateByPrimaryKey(NmplConfiguration record);
}