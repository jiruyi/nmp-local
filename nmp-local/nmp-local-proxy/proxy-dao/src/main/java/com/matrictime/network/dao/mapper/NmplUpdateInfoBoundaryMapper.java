package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplUpdateInfoBoundary;
import com.matrictime.network.dao.model.NmplUpdateInfoBoundaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplUpdateInfoBoundaryMapper {
    long countByExample(NmplUpdateInfoBoundaryExample example);

    int deleteByExample(NmplUpdateInfoBoundaryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplUpdateInfoBoundary record);

    int insertSelective(NmplUpdateInfoBoundary record);

    List<NmplUpdateInfoBoundary> selectByExample(NmplUpdateInfoBoundaryExample example);

    NmplUpdateInfoBoundary selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplUpdateInfoBoundary record, @Param("example") NmplUpdateInfoBoundaryExample example);

    int updateByExample(@Param("record") NmplUpdateInfoBoundary record, @Param("example") NmplUpdateInfoBoundaryExample example);

    int updateByPrimaryKeySelective(NmplUpdateInfoBoundary record);

    int updateByPrimaryKey(NmplUpdateInfoBoundary record);
}