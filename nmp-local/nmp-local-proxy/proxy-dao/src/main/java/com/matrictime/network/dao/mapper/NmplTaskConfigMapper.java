package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplTaskConfig;
import com.matrictime.network.dao.model.NmplTaskConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplTaskConfigMapper {
    long countByExample(NmplTaskConfigExample example);

    int deleteByExample(NmplTaskConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplTaskConfig record);

    int insertSelective(NmplTaskConfig record);

    List<NmplTaskConfig> selectByExample(NmplTaskConfigExample example);

    NmplTaskConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplTaskConfig record, @Param("example") NmplTaskConfigExample example);

    int updateByExample(@Param("record") NmplTaskConfig record, @Param("example") NmplTaskConfigExample example);

    int updateByPrimaryKeySelective(NmplTaskConfig record);

    int updateByPrimaryKey(NmplTaskConfig record);
}