package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplUpdateInfoBase;
import com.matrictime.network.dao.model.NmplUpdateInfoBaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplUpdateInfoBaseMapper {
    long countByExample(NmplUpdateInfoBaseExample example);

    int deleteByExample(NmplUpdateInfoBaseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplUpdateInfoBase record);

    int insertSelective(NmplUpdateInfoBase record);

    List<NmplUpdateInfoBase> selectByExample(NmplUpdateInfoBaseExample example);

    NmplUpdateInfoBase selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplUpdateInfoBase record, @Param("example") NmplUpdateInfoBaseExample example);

    int updateByExample(@Param("record") NmplUpdateInfoBase record, @Param("example") NmplUpdateInfoBaseExample example);

    int updateByPrimaryKeySelective(NmplUpdateInfoBase record);

    int updateByPrimaryKey(NmplUpdateInfoBase record);
}