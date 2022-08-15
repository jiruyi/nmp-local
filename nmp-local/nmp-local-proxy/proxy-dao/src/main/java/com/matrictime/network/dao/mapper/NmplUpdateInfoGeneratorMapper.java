package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplUpdateInfoGenerator;
import com.matrictime.network.dao.model.NmplUpdateInfoGeneratorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplUpdateInfoGeneratorMapper {
    long countByExample(NmplUpdateInfoGeneratorExample example);

    int deleteByExample(NmplUpdateInfoGeneratorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplUpdateInfoGenerator record);

    int insertSelective(NmplUpdateInfoGenerator record);

    List<NmplUpdateInfoGenerator> selectByExample(NmplUpdateInfoGeneratorExample example);

    NmplUpdateInfoGenerator selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplUpdateInfoGenerator record, @Param("example") NmplUpdateInfoGeneratorExample example);

    int updateByExample(@Param("record") NmplUpdateInfoGenerator record, @Param("example") NmplUpdateInfoGeneratorExample example);

    int updateByPrimaryKeySelective(NmplUpdateInfoGenerator record);

    int updateByPrimaryKey(NmplUpdateInfoGenerator record);
}