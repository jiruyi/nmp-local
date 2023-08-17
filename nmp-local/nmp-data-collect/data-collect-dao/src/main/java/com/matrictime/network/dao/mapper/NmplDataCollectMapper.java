package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.dao.model.NmplDataCollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplDataCollectMapper {
    long countByExample(NmplDataCollectExample example);

    int deleteByExample(NmplDataCollectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDataCollect record);

    int insertSelective(NmplDataCollect record);

    List<NmplDataCollect> selectByExample(NmplDataCollectExample example);

    NmplDataCollect selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDataCollect record, @Param("example") NmplDataCollectExample example);

    int updateByExample(@Param("record") NmplDataCollect record, @Param("example") NmplDataCollectExample example);

    int updateByPrimaryKeySelective(NmplDataCollect record);

    int updateByPrimaryKey(NmplDataCollect record);
}