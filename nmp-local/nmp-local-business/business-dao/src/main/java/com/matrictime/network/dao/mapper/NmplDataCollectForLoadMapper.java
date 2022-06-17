package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDataCollectForLoad;
import com.matrictime.network.dao.model.NmplDataCollectForLoadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplDataCollectForLoadMapper {
    long countByExample(NmplDataCollectForLoadExample example);

    int deleteByExample(NmplDataCollectForLoadExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDataCollectForLoad record);

    int insertSelective(NmplDataCollectForLoad record);

    List<NmplDataCollectForLoad> selectByExample(NmplDataCollectForLoadExample example);

    NmplDataCollectForLoad selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDataCollectForLoad record, @Param("example") NmplDataCollectForLoadExample example);

    int updateByExample(@Param("record") NmplDataCollectForLoad record, @Param("example") NmplDataCollectForLoadExample example);

    int updateByPrimaryKeySelective(NmplDataCollectForLoad record);

    int updateByPrimaryKey(NmplDataCollectForLoad record);
}