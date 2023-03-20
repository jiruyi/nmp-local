package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplVersionInfo;
import com.matrictime.network.dao.model.NmplVersionInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplVersionInfoMapper {
    long countByExample(NmplVersionInfoExample example);

    int deleteByExample(NmplVersionInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplVersionInfo record);

    int insertSelective(NmplVersionInfo record);

    List<NmplVersionInfo> selectByExample(NmplVersionInfoExample example);

    NmplVersionInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplVersionInfo record, @Param("example") NmplVersionInfoExample example);

    int updateByExample(@Param("record") NmplVersionInfo record, @Param("example") NmplVersionInfoExample example);

    int updateByPrimaryKeySelective(NmplVersionInfo record);

    int updateByPrimaryKey(NmplVersionInfo record);
}