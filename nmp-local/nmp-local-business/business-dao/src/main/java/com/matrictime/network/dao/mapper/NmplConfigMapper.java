package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplConfig;
import com.matrictime.network.dao.model.NmplConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplConfigMapper {
    long countByExample(NmplConfigExample example);

    int deleteByExample(NmplConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplConfig record);

    int insertSelective(NmplConfig record);

    List<NmplConfig> selectByExample(NmplConfigExample example);

    NmplConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplConfig record, @Param("example") NmplConfigExample example);

    int updateByExample(@Param("record") NmplConfig record, @Param("example") NmplConfigExample example);

    int updateByPrimaryKeySelective(NmplConfig record);

    int updateByPrimaryKey(NmplConfig record);
}