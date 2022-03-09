package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplSignal;
import com.matrictime.network.dao.model.NmplSignalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplSignalMapper {
    long countByExample(NmplSignalExample example);

    int deleteByExample(NmplSignalExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplSignal record);

    int insertSelective(NmplSignal record);

    List<NmplSignal> selectByExample(NmplSignalExample example);

    NmplSignal selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplSignal record, @Param("example") NmplSignalExample example);

    int updateByExample(@Param("record") NmplSignal record, @Param("example") NmplSignalExample example);

    int updateByPrimaryKeySelective(NmplSignal record);

    int updateByPrimaryKey(NmplSignal record);
}