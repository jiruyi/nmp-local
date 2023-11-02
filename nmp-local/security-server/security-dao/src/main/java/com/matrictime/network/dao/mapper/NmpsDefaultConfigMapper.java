package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsDefaultConfig;
import com.matrictime.network.dao.model.NmpsDefaultConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsDefaultConfigMapper {
    long countByExample(NmpsDefaultConfigExample example);

    int deleteByExample(NmpsDefaultConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsDefaultConfig record);

    int insertSelective(NmpsDefaultConfig record);

    List<NmpsDefaultConfig> selectByExample(NmpsDefaultConfigExample example);

    NmpsDefaultConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsDefaultConfig record, @Param("example") NmpsDefaultConfigExample example);

    int updateByExample(@Param("record") NmpsDefaultConfig record, @Param("example") NmpsDefaultConfigExample example);

    int updateByPrimaryKeySelective(NmpsDefaultConfig record);

    int updateByPrimaryKey(NmpsDefaultConfig record);
}