package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplSmsDetail;
import com.matrictime.network.dao.model.NmplSmsDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplSmsDetailMapper {
    long countByExample(NmplSmsDetailExample example);

    int deleteByExample(NmplSmsDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplSmsDetail record);

    int insertSelective(NmplSmsDetail record);

    List<NmplSmsDetail> selectByExample(NmplSmsDetailExample example);

    NmplSmsDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplSmsDetail record, @Param("example") NmplSmsDetailExample example);

    int updateByExample(@Param("record") NmplSmsDetail record, @Param("example") NmplSmsDetailExample example);

    int updateByPrimaryKeySelective(NmplSmsDetail record);

    int updateByPrimaryKey(NmplSmsDetail record);
}